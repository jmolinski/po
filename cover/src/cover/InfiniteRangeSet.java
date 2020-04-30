package cover;

public class InfiniteRangeSet implements ISet {
    private final int start;
    private final int step;

    public InfiniteRangeSet(int start, int step) {
        this.start = start;
        this.step = -step;
    }

    public boolean coversAnyElement(final boolean[] array) {
        for (int i = start; i < array.length; i += step) {
            if (!array[i]) {
                return true;
            }
        }
        return false;
    }

    public int countCoveredElements(final boolean[] array) {
        int covered = 0;

        for (int i = start; i < array.length; i += step) {
            if (!array[i]) {
                covered++;
            }
        }

        return covered;
    }

    public int markCoveredElements(boolean[] array) {
        int covered = 0;

        for (int i = start; i < array.length; i += step) {
            if (!array[i]) {
                array[i] = true;
                covered++;
            }
        }

        return covered;
    }
}
