package christmastree;

public class ChristmasTree {
    static final int defaultHeight = 3;
    static final int lowerPartSize = 3;

    public static void main(String[] args) {
        int height = args.length > 0 ? Integer.parseInt(args[0]) : defaultHeight;
        printUpperPart(height);
        printLowerPart(height);
    }

    private static void printUpperPart(int height) {
        for (int i = 0; i < height; i++) {
            printCharacter(' ', height == 1 ? 1 : height - i - 1);
            printCharacter('*', 2 * i + 1);
            printCharacter('\n', 1);
        }
    }

    private static void printLowerPart(int height) {
        for (int i = 0; i < lowerPartSize; i++) {
            printCharacter(' ', height - lowerPartSize + 1);
            printCharacter('.', lowerPartSize);
            printCharacter('\n', 1);
        }
    }

    private static void printCharacter(char character, int times) {
        for (int i = 0; i < times; i++)
            System.out.print(character);
    }
}
