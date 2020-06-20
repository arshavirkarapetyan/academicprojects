package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;


import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static java.lang.Character.toUpperCase;

public class Engine implements java.io.Serializable {
    private static TERenderer ter = new TERenderer();
    private static boolean[][] visited;
    private static List<Room> rooms;
    private static Avatar person;
    private static Avatar prize;
    private static String load = "";
    private static final int WIDTH = 60;
    private static final int HEIGHT = 40;
    private static TETile[][] world = new TETile[WIDTH][HEIGHT];
    private static Random RANDOM;


    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        StdDraw.clear();
        displayMenu();
        readInput();
    }

    public static void nextKey() {
        while (!StdDraw.hasNextKeyTyped()) {
            continue;
        }
    }

    public static void initWorld() {
        ter.initialize(WIDTH, HEIGHT, 0, 0);
        ter.renderFrame(interactWithInputString(load));
    }

    public static void placeBear(TETile a) {
        int x = rooms.get(0).center.x;
        int y = rooms.get(0).center.y;
        person = new Avatar(x, y, a);
        world[x][y] = a;
        person.p = new Position(x, y);
    }

    public static void placeHoney() {
        int x2 = rooms.get(rooms.size() - 1).center.x;
        int y2 = rooms.get(rooms.size() - 1).center.y;
        prize = new Avatar(x2, y2, Tileset.HONEY);
        world[x2][y2] = Tileset.HONEY;
        prize.p = new Position(x2, y2);
    }

    public static void placeWater() {
        for (int i = 1; i < rooms.size() - 1; i++) {
            int x = rooms.get(i).center.x;
            int y = rooms.get(i).center.y;
            world[x][y] = Tileset.WATER;
        }
    }



    public static void readInput() {
        int seed = 0;
        nextKey();
        while (StdDraw.hasNextKeyTyped()) {
            char key = StdDraw.nextKeyTyped();
            key = Character.toUpperCase(key);
            load += key;
            if (key == 'N') {
                StdDraw.clear();
                displayMenu2();
                nextKey();
                if (StdDraw.hasNextKeyTyped()) {
                    char key2 = StdDraw.nextKeyTyped();
                    load += key2;
                    while (key2 > 47 && key2 < 58) {
                        seed = seed * 10 + (key2 - 48);
                        nextKey();
                        key2 = StdDraw.nextKeyTyped();
                        load += key2;
                    }
                    if (key2 == 'S' || key2 == 's') {
                        //start game with that seed!
                        ter.initialize(WIDTH, HEIGHT, 0, 0);
                        RANDOM = new Random(seed);
                        fillRooms(world);
                        placeHoney();
                        placeBear(Tileset.BEAR);
                        placeWater();
                        StdDraw.clear();
                        ter.renderFrame(world);
                    }
                }
            }
            if (key == 'W') {
                person.up();
                ter.renderFrame(world);
            } else if (key == 'A') {
                person.left();
                ter.renderFrame(world);
            } else if (key == 'S') {
                person.down();
                ter.renderFrame(world);
            } else if (key == 'D') {
                person.right();
                ter.renderFrame(world);
            } else if (key == 'R' || key == 'r') {
                try {
                    FileInputStream fileIn = new FileInputStream("./byow/tile.txt");
                    ObjectInputStream in = new ObjectInputStream(fileIn);
                    load = (String) in.readObject();
                    in.close();
                    fileIn.close();
                } catch (IOException i) {
                    i.printStackTrace();

                } catch (ClassNotFoundException cc) {
                    System.exit(0);
                }
                replay(load);
            } else if (key == 'C' || key == 'c') {
                StdDraw.clear();
                displayMenu1();
                nextKey();
                key = StdDraw.nextKeyTyped();
                key = Character.toUpperCase(key);
                load += key;
//                if (StdDraw.hasNextKeyTyped()) {
//                    key = StdDraw.nextKeyTyped();
//                    load += key;
//                }
                StdDraw.clear();
                displayMenu2();
                nextKey();
                if (StdDraw.hasNextKeyTyped()) {
                    char key2 = StdDraw.nextKeyTyped();
                    load += key2;
                    while (key2 > 47 && key2 < 58) {
                        seed = seed * 10 + (key2 - 48);
                        nextKey();
                        key2 = StdDraw.nextKeyTyped();
                        load += key2;
                    }
                    if (key2 == 'S' || key2 == 's') {
                        //start game with that seed!
                        ter.initialize(WIDTH, HEIGHT, 0, 0);
                        RANDOM = new Random(seed);
                        fillRooms(world);
                        placeHoney();
                        if (key == '1') {
                            placeBear(Tileset.BEAR);
                        } else if (key == '2') {
                            placeBear(Tileset.BEAR2);
                        } else if (key == '3') {
                            placeBear(Tileset.BEAR3);
                        }
                        placeWater();
                        StdDraw.clear();
                        ter.renderFrame(world);
                    }

                }
            } else if (key == 'L' || key == 'l') {
                try {
                    FileInputStream fileIn = new FileInputStream("./byow/tile.txt");
                    ObjectInputStream in = new ObjectInputStream(fileIn);
                    load = (String) in.readObject();
                    in.close();
                    fileIn.close();
                } catch (IOException i) {
                    i.printStackTrace();
                    return;
                } catch (ClassNotFoundException c) {
                    System.exit(0);
                }
                initWorld();
            } else if (key == ':') {
                nextKey();
                key = StdDraw.nextKeyTyped();
                if (key == 'Q' || key == 'q') {
                    load = load.substring(0, load.length() - 1);
                    try {
                        FileOutputStream fileOut =
                                new FileOutputStream("./byow/tile.txt");
                        ObjectOutputStream out = new ObjectOutputStream(fileOut);
                        out.writeObject(load);
                        out.close();
                        fileOut.close();
                    } catch (IOException i) {
                        i.printStackTrace();
                    }
                    System.exit(0);
                    break;
                }
            } else if (key == 'Q' || key == 'q') {
                System.exit(0);
            }
            nextKey();
        }
    }

    public static void displayMenu() {
        StdDraw.disableDoubleBuffering();
        StdDraw.setCanvasSize(WIDTH * 10, HEIGHT * 10);
        StdDraw.setXscale(0, WIDTH * 10);
        StdDraw.setYscale(0, HEIGHT * 10);
        StdDraw.clear(Color.WHITE);
        StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
        Font font = new Font("DialogInput", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.picture(WIDTH * 10 / 2, HEIGHT * 10 / 2 + 120, "./byow/Core/babyBear.png");
        StdDraw.text(WIDTH * 10 / 2, HEIGHT * 10 / 2 + 40, "WELCOME TO BEAR GAMES!");
        StdDraw.text(WIDTH * 10 / 2, HEIGHT * 10 / 2 + 10, "NEW BEAR GAME (N)");
        StdDraw.text(WIDTH * 10 / 2, HEIGHT * 10 / 2 - 20, "LOAD BEAR GAME (L)");
        StdDraw.text(WIDTH * 10 / 2, HEIGHT * 10 / 2 - 50, "REPLAY BEAR GAME (R)");
        StdDraw.text(WIDTH * 10 / 2, HEIGHT * 10 / 2 - 80, "CHOOSE YOUR AVATAR (C)");
        StdDraw.text(WIDTH * 10 / 2, HEIGHT * 10 / 2 - 110, "QUIT BEAR GAME (Q)");
        StdDraw.show();
        StdDraw.enableDoubleBuffering();
    }

    public static void displayMenu1() {
        StdDraw.disableDoubleBuffering();
        StdDraw.setXscale(0, WIDTH * 10);
        StdDraw.setYscale(0, HEIGHT * 10 + 5);
        StdDraw.clear(Color.WHITE);
        StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
        Font font = new Font("DialogInput", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.text(WIDTH * 10 / 2, HEIGHT * 10 / 2 + 40, "CHOOSE YOUR AVATAR!");
        StdDraw.text(WIDTH * 10 / 2, HEIGHT * 10 / 2 + 10, "BEAR 1 (1)");
        StdDraw.text(WIDTH * 10 / 2, HEIGHT * 10 / 2 - 20, "BEAR 2 (2)");
        StdDraw.text(WIDTH * 10 / 2, HEIGHT * 10 / 2 - 50, "BEAR 3 (3)");
        StdDraw.show();
        StdDraw.enableDoubleBuffering();
    }

    public static void displayMenu2() {
        StdDraw.disableDoubleBuffering();
        StdDraw.setXscale(0, WIDTH * 10);
        StdDraw.setYscale(0, HEIGHT * 10 + 5);
        StdDraw.clear(Color.WHITE);
        StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
        Font font = new Font("DialogInput", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.text(WIDTH * 10 / 2, HEIGHT * 10 / 2 + 20, "PLEASE ENTER A SEED!");
        StdDraw.text(WIDTH * 10 / 2, HEIGHT * 10 / 2 - 10, "Press 'S' when ready to start game");
        StdDraw.show();
        StdDraw.enableDoubleBuffering();
    }

    public static void win() {
        StdDraw.disableDoubleBuffering();
        StdDraw.setXscale(0, WIDTH * 10);
        StdDraw.setYscale(0, HEIGHT * 10 + 5);
        StdDraw.clear(Color.WHITE);
        StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
        Font font = new Font("DialogInput", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.text(WIDTH * 10 / 2, HEIGHT * 10 / 2 - 10, "YOU WON!");
        StdDraw.text(WIDTH * 10 / 2, HEIGHT * 10 / 2 - 40, "ENJOY YOUR HONEY");
        StdDraw.picture(WIDTH * 10 / 2, HEIGHT * 10 / 2 + 80, "./byow/Core/Bear.png");
        StdDraw.show();
        StdDraw.enableDoubleBuffering();
        nextKey();
        if (StdDraw.hasNextKeyTyped()) {
            System.exit(0);
        }
    }

    public static void lose() {
        StdDraw.disableDoubleBuffering();
        StdDraw.setXscale(0, WIDTH * 10);
        StdDraw.setYscale(0, HEIGHT * 10 + 5);
        StdDraw.clear(Color.WHITE);
        StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
        Font font = new Font("DialogInput", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.text(WIDTH * 10 / 2, HEIGHT * 10 / 2 - 10, "OUCH...");
        StdDraw.text(WIDTH * 10 / 2, HEIGHT * 10 / 2 - 40, "LOSER!!!");
        StdDraw.picture(WIDTH * 10 / 2, HEIGHT * 10 / 2 + 100, "./byow/Core/polarbear.png");
        StdDraw.show();
        StdDraw.enableDoubleBuffering();
        nextKey();
        if (StdDraw.hasNextKeyTyped()) {
            System.exit(0);
        }
    }


    private static class Position {
        int x;
        int y;

        private Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static class Room {
        Position center;
        int w, h;

        public Room(int x, int y, int w, int h) {
            this.w = w;
            this.h = h;
            center = new Position(x, y);
        }
    }

    public static class Avatar implements java.io.Serializable {
        Position p;
        TETile img;
        public Avatar(int x, int y, TETile img) {
            p = new Position(x, y);
            this.img = img;
        }
        public void right() {
            if (world[p.x + 1][p.y] == Tileset.FLOOR) {
                world[p.x][p.y] = Tileset.FLOOR;
                world[p.x + 1][p.y] = img;
                p.x = p.x + 1;
            } else if (world[p.x + 1][p.y] == Tileset.HONEY) {
                win();
            } else if (world[p.x + 1][p.y] == Tileset.WATER) {
                lose();
            }
        }
        public void left() {
            if (world[p.x - 1][p.y] == Tileset.FLOOR) {
                world[p.x][p.y] = Tileset.FLOOR;
                world[p.x - 1][p.y] = img;
                p.x = p.x - 1;
            } else if (world[p.x - 1][p.y] == Tileset.HONEY) {
                win();
            } else if (world[p.x - 1][p.y] == Tileset.WATER) {
                lose();
            }
        }
        public void down() {
            if (world[p.x][p.y - 1] == Tileset.FLOOR) {
                world[p.x][p.y] = Tileset.FLOOR;
                world[p.x][p.y - 1] = img;
                p.y = p.y - 1;
            } else if (world[p.x][p.y - 1] == Tileset.HONEY) {
                win();
            } else if (world[p.x][p.y - 1] == Tileset.WATER) {
                lose();
            }
        }
        public void up() {
            if (world[p.x][p.y + 1] == Tileset.FLOOR) {
                world[p.x][p.y] = Tileset.FLOOR;
                world[p.x][p.y + 1] = img;
                p.y = p.y + 1;
            } else if (world[p.x][p.y + 1] == Tileset.HONEY) {
                win();
            } else if (world[p.x][p.y + 1] == Tileset.WATER) {
                lose();
            }
        }
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     * <p>
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     * <p>
     * In other words, both of these calls:
     * - interactWithInputString("n123sss:q")
     * - interactWithInputString("lww")
     * <p>
     * should yield the exact same world state as:
     * - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public static TETile[][] interactWithInputString(String input) {
        //  Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        InputReader inputReader = new StringInputReader(input);
        int seed = 0;
        while (inputReader.possibleNextInput()) {
            char c = inputReader.getNextKey();
            c = toUpperCase(c);
            if (c == 'N') {
                c = inputReader.getNextKey();
                while (c > 47 && c < 58) {
                    seed = seed * 10 + (c - 48);
                    c = inputReader.getNextKey();
                }
                if (c == 'S' || c == 's') {
                    RANDOM = new Random(seed);
                    fillRooms(world);
                    placeHoney();
                    placeBear(Tileset.BEAR);
                    placeWater();
                }
            } else if (c == 'C' || c == 'c') {
                c = inputReader.getNextKey();
                if (c == '1') {
                    c = inputReader.getNextKey();
                    while (c > 47 && c < 58) {
                        seed = seed * 10 + (c - 48);
                        c = inputReader.getNextKey();
                    }
                    if (c == 'S' || c == 's') {
                        RANDOM = new Random(seed);
                        fillRooms(world);
                        placeHoney();
                        placeBear(Tileset.BEAR);
                        placeWater();
                    }
                } else if (c == '2') {
                    c = inputReader.getNextKey();
                    while (c > 47 && c < 58) {
                        seed = seed * 10 + (c - 48);
                        c = inputReader.getNextKey();
                    }
                    if (c == 'S' || c == 's') {
                        RANDOM = new Random(seed);
                        fillRooms(world);
                        placeHoney();
                        placeBear(Tileset.BEAR2);
                        placeWater();
                    }
                } else if (c == '3') {
                    c = inputReader.getNextKey();
                    while (c > 47 && c < 58) {
                        seed = seed * 10 + (c - 48);
                        c = inputReader.getNextKey();
                    }
                    if (c == 'S' || c == 's') {
                        RANDOM = new Random(seed);
                        fillRooms(world);
                        placeHoney();
                        placeBear(Tileset.BEAR3);
                        placeWater();
                    }
                }
            } else if (c == 'W' || c == 'w') {
                person.up();
            } else if (c == 'A' || c == 'a') {
                person.left();

            } else if (c == 'S' || c == 's') {
                person.down();
            } else if (c == 'D' || c == 'd') {
                person.right();
            } else if (c == 'R' || c == 'r') {
                try {
                    FileInputStream fileIn = new FileInputStream("./byow/tile.txt");
                    ObjectInputStream in = new ObjectInputStream(fileIn);
                    load = (String) in.readObject();
                    in.close();
                    fileIn.close();
                } catch (IOException i) {
                    i.printStackTrace();

                } catch (ClassNotFoundException cc) {
                    System.exit(0);
                }
            } else if (c == 'L' || c == 'l') {
                try {
                    FileInputStream fileIn = new FileInputStream("./byow/tile.txt");
                    ObjectInputStream in = new ObjectInputStream(fileIn);
                    load = (String) in.readObject();
                    in.close();
                    fileIn.close();
                } catch (IOException i) {
                    i.printStackTrace();

                } catch (ClassNotFoundException cc) {
                    System.exit(0);
                }

            } else if (c == ':') {
                c = inputReader.getNextKey();
                if (c == 'Q' || c == 'q') {
                    try {
                        FileOutputStream fileOut =
                                new FileOutputStream("./byow/tile.txt");
                        ObjectOutputStream out = new ObjectOutputStream(fileOut);
                        out.writeObject(load);
                        out.close();
                        fileOut.close();
                    } catch (IOException i) {
                        i.printStackTrace();
                    }
                }
            } else if (c == 'Q' || c == 'q') {
                System.exit(0);
            }
        }
        return world;
    }

    public static TETile[][] replay(String input) {
        //  Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        InputReader inputReader = new StringInputReader(input);
        int seed = 0;
        while (inputReader.possibleNextInput()) {
            char c = inputReader.getNextKey();
            c = toUpperCase(c);
            if (c == 'N') {
                c = inputReader.getNextKey();
                while (c > 47 && c < 58) {
                    seed = seed * 10 + (c - 48);
                    c = inputReader.getNextKey();
                }
                if (c == 'S' || c == 's') {
                    ter.initialize(WIDTH, HEIGHT, 0, 0);
                    RANDOM = new Random(seed);
                    fillRooms(world);
                    placeHoney();
                    placeBear(Tileset.BEAR);
                    placeWater();
                    StdDraw.clear();
                    ter.renderFrame(world);
                }
            } else if (c == 'C' || c == 'c') {
                c = inputReader.getNextKey();
                if (c == '1') {
                    c = inputReader.getNextKey();
                    while (c > 47 && c < 58) {
                        seed = seed * 10 + (c - 48);
                        c = inputReader.getNextKey();
                    }
                    if (c == 'S' || c == 's') {
                        ter.initialize(WIDTH, HEIGHT, 0, 0);
                        RANDOM = new Random(seed);
                        fillRooms(world);
                        placeHoney();
                        placeBear(Tileset.BEAR);
                        placeWater();
                        StdDraw.clear();
                        ter.renderFrame(world);
                    }
                } else if (c == '2') {
                    c = inputReader.getNextKey();
                    while (c > 47 && c < 58) {
                        seed = seed * 10 + (c - 48);
                        c = inputReader.getNextKey();
                    }
                    if (c == 'S' || c == 's') {
                        ter.initialize(WIDTH, HEIGHT, 0, 0);
                        RANDOM = new Random(seed);
                        fillRooms(world);
                        placeHoney();
                        placeBear(Tileset.BEAR2);
                        placeWater();
                        StdDraw.clear();
                        ter.renderFrame(world);
                    }
                } else if (c == '3') {
                    c = inputReader.getNextKey();
                    while (c > 47 && c < 58) {
                        seed = seed * 10 + (c - 48);
                        c = inputReader.getNextKey();
                    }
                    if (c == 'S' || c == 's') {
                        ter.initialize(WIDTH, HEIGHT, 0, 0);
                        RANDOM = new Random(seed);
                        fillRooms(world);
                        placeHoney();
                        placeBear(Tileset.BEAR3);
                        placeWater();
                        StdDraw.clear();
                        ter.renderFrame(world);
                    }
                }

            } else if (c == 'W' || c == 'w') {
                person.up();
                ter.renderFrame(world);
            } else if (c == 'A' || c == 'a') {
                person.left();
                ter.renderFrame(world);
            } else if (c == 'S' || c == 's') {
                person.down();
                ter.renderFrame(world);
            } else if (c == 'D' || c == 'd') {
                person.right();
                ter.renderFrame(world);
            } else if (c == 'L' || c == 'l') {
                try {
                    FileInputStream fileIn = new FileInputStream("./byow/tile.txt");
                    ObjectInputStream in = new ObjectInputStream(fileIn);
                    load = (String) in.readObject();
                    in.close();
                    fileIn.close();
                } catch (IOException i) {
                    i.printStackTrace();

                } catch (ClassNotFoundException cc) {
                    System.exit(0);
                }

            } else if (c == ':') {
                c = inputReader.getNextKey();
                if (c == 'Q' || c == 'q') {
                    try {
                        FileOutputStream fileOut =
                                new FileOutputStream("./byow/tile.txt");
                        ObjectOutputStream out = new ObjectOutputStream(fileOut);
                        out.writeObject(load);
                        out.close();
                        fileOut.close();
                    } catch (IOException i) {
                        i.printStackTrace();
                    }
                }
            } else if (c == 'Q' || c == 'q') {
                System.exit(0);
            }
            try {
                Thread.sleep(300);
            }
            catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        return world;
    }

    public static void fillRooms(TETile[][] tiles) {
        rooms = new ArrayList<>();
        int height = tiles[0].length;
        int width = tiles.length;
        visited = new boolean[width][height];
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
        int numRooms = (int) (1 + (.01 * width * height));
        for (int i = 0; i < numRooms; i++) {
            int x = (int) (.9 * width * RANDOM.nextDouble());
            int y = (int) (.9 * height * RANDOM.nextDouble());
            int dimx = (int) (.3 * width * RANDOM.nextDouble());
            while (dimx < 3) {
                dimx = (int) (.3 * width * RANDOM.nextDouble());
            }
            int dimy = (int) (.3 * height * RANDOM.nextDouble());
            while (dimy < 3) {
                dimy = (int) (.3 * height * RANDOM.nextDouble());
            }
            Position p = new Position(x, y);
            buildRect(tiles, p, dimx, dimy);

        }
        for (int i = 0; i < rooms.size() - 1; i++) {
            fillHallways(tiles, rooms.get(i).center, rooms.get(i + 1).center);
        }
    }

    public static void checkDirection(TETile[][] tiles, int x, int y) {
        if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) {
            if (tiles[x][y].equals(Tileset.NOTHING)) {
                tiles[x][y] = Tileset.WALL;
            }
        }
    }

    public static void fillHallways(TETile[][] tiles, Position start, Position end) {
        int smallY, largeY, smallX, largeX;
        if (start.x < end.x) {
            smallX = start.x;
            smallY = start.y;
            largeX = end.x;
        } else {
            smallX = end.x;
            smallY = end.y;
            largeX = start.x;

        }

        for (int i = smallX; i <= largeX; i++) {
            if (tiles[i][smallY].equals(Tileset.WALL)) {
                tiles[i][smallY] = Tileset.FLOOR;
                checkDirection(tiles, i, smallY + 1);
                checkDirection(tiles, i, smallY - 1);
                checkDirection(tiles, i + 1, smallY);
                checkDirection(tiles, i - 1, smallY);
            } else if (tiles[i][smallY].equals(Tileset.NOTHING)) {
                tiles[i][smallY] = Tileset.FLOOR;
                checkDirection(tiles, i, smallY + 1);
                checkDirection(tiles, i, smallY - 1);
                checkDirection(tiles, i + 1, smallY);
                checkDirection(tiles, i - 1, smallY);
            }
        }
        checkDirection(tiles, largeX + 1, smallY);
        checkDirection(tiles, largeX - 1, smallY);


        if (start.y < end.y) {
            smallY = start.y;
            largeY = end.y;
        } else {
            smallY = end.y;
            largeY = start.y;
        }


        for (int i = smallY; i <= largeY; i++) {
            if (tiles[largeX][i].equals(Tileset.WALL)) {
                tiles[largeX][i] = Tileset.FLOOR;
                checkDirection(tiles, largeX + 1, i);
                checkDirection(tiles, largeX - 1, i);
                checkDirection(tiles, largeX, i + 1);
                checkDirection(tiles, largeX, i - 1);
            } else if (tiles[largeX][i].equals(Tileset.NOTHING)) {
                tiles[largeX][i] = Tileset.FLOOR;
                checkDirection(tiles, largeX + 1, i);
                checkDirection(tiles, largeX - 1, i);
                checkDirection(tiles, largeX, i + 1);
                checkDirection(tiles, largeX, i - 1);
            }
        }
        checkDirection(tiles, largeX, largeY + 1);
        checkDirection(tiles, largeX, largeY - 1);

    }

    private static void buildRect(TETile[][] tiles, Position p, int w, int h) {
        int centerX, centerY;
        int actualHeight = 0;
        int actualWidth = 0;
        boolean visit = false;
        for (int x = p.x; x < p.x + w; x++) {
            for (int y = p.y; y < p.y + h; y++) {
                if (x >= WIDTH || y >= HEIGHT) {
                    actualWidth--;
                    break;
                } else if (visited[x][y]) {
                    visit = true;
                } else if (!visited[x][y]) {
                    if (y == p.y || y == p.y + h - 1
                            || x == p.x || x == p.x + w - 1 || x == WIDTH - 1 || y == HEIGHT - 1) {
                        tiles[x][y] = Tileset.WALL;
                        if (x == p.x) {
                            actualHeight++;
                        }
                    } else {
                        tiles[x][y] = Tileset.FLOOR;
                        visited[x][y] = true;
                        if (x == p.x) {
                            actualHeight++;
                        }
                    }
                }
            }
            actualWidth++;
        }
        centerX = actualWidth / 2;
        centerY = actualHeight / 2;
        if (!visit) {
            rooms.add(new Room(p.x + centerX, p.y + centerY, actualWidth, actualHeight));
        }
    }
}
