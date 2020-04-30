package cover;

public class FiniteRangeSet implements ISet {
    private final int start;
    private final int step;
    private final int stop;

    public FiniteRangeSet(int start, int step, int stop) {
        this.start = start;
        this.step = -step;
        this.stop = -stop;
    }

    public boolean coversAnyElement(final boolean[] array) {
        for (int i = start; i <= stop && i < array.length; i += step) {
            if (!array[i]) {
                return true;
            }
        }
        return false;
    }

    public int countCoveredElements(final boolean[] array) {
        int covered = 0;

        for (int i = start; i <= stop && i < array.length; i += step) {
            if (!array[i]) {
                covered++;
            }
        }

        return covered;
    }

    public int markCoveredElements(boolean[] array) {
        int covered = 0;

        for (int i = start; i <= stop && i < array.length; i += step) {
            if (!array[i]) {
                array[i] = true;
                covered++;
            }
        }

        //System.out.println(covered);

        return covered;
    }
}
