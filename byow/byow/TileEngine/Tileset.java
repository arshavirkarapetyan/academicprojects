package byow.TileEngine;

import java.awt.Color;
import edu.princeton.cs.introcs.StdDraw;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 *
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 *
 * Ex:
 *      world[x][y] = Tileset.FLOOR;
 *
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */

public class Tileset {
    public static final TETile BEARY = new TETile('@', Color.white, Color.black, "you");
    public static final TETile WALL = new TETile('❀', new Color(216, 128, 128), Color.darkGray,
            "wall");
    public static final TETile FLOOR = new TETile('"',
            new Color(48, 142, 51), new Color(194, 234, 195), "floor");
    public static final TETile NOTHING = new TETile(' ', StdDraw.DARK_GRAY,
            StdDraw.BOOK_LIGHT_BLUE, "nothing");
    public static final TETile GRASS = new TETile('"', Color.green, Color.black, "grass");
    public static final TETile WATER = new TETile('≈', Color.blue,
            new Color(194, 234, 195), "water");
    public static final TETile FLOWER = new TETile('❀', Color.magenta, Color.pink, "flower");
    public static final TETile LOCKED_DOOR = new TETile('█', Color.orange, Color.black,
            "locked door");
    public static final TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black,
            "unlocked door");
    public static final TETile SAND = new TETile('▒', Color.yellow, Color.black, "sand");
    public static final TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "mountain");
    public static final TETile TREE = new TETile('♠', Color.green, Color.black, "tree");
    public static final TETile HONEY = new TETile(' ', null, null,
            "honey", "./byow/TileEngine/honey.png");
    public static final TETile BEAR = new TETile(' ', null, null,
            "bear", "./byow/TileEngine/bear.png");
    public static final TETile BEAR2 = new TETile(' ', null, null,
            "bear2", "./byow/TileEngine/bear2.png");
    public static final TETile BEAR3 = new TETile(' ', null, null,
            "bear3", "./byow/TileEngine/bear3.png");
}