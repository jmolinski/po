package cover;

public interface ISet {
    boolean coversAnyElement(final boolean[] array);

    int countCoveredElements(final boolean[] array);

    int markCoveredElements(boolean[] array);
}
