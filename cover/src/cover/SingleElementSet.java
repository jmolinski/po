package cover;

public class SingleElementSet implements ISet {
    private final int value;

    public SingleElementSet(int value) {
        this.value = value;
    }

    public boolean coversAnyElement(final boolean[] array) {
        if (value >= array.length) {
            return false;
        }
        return !array[value];
    }

    public int countCoveredElements(final boolean[] array) {
        if (value >= array.length) {
            return 0;
        }
        return array[value] ? 0 : 1;
    }

    public int markCoveredElements(boolean[] array) {
        if (value < array.length && !array[value]) {
            array[value] = true;
            return 1;
        }
        return 0;
    }
}
