package byow.Core;

public class StringInputReader implements InputReader {
    private String input;
    private int index;
    public StringInputReader(String input) {
        this.input = input;
        index = 0;
    }

    @Override
    public char getNextKey() {
        char ReturnChar = input.charAt(index);
        index++;
        return ReturnChar;
    }

    @Override
    public boolean possibleNextInput() {
        return index < input.length();
    }
}