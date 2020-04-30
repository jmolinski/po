package cover;

import java.util.ArrayList;
import java.util.Arrays;

public class SetUnion implements ISet {
    public ArrayList<ISet> subsets;

    public SetUnion() {
        subsets = new ArrayList<>();
    }

    public void addSubset(ISet element) {
        subsets.add(element);
    }

    public boolean coversAnyElement(final boolean[] array) {
        for (ISet subset : subsets) {
            if (subset.coversAnyElement(array)) {
                return true;
            }
        }
        return false;
    }

    public int countCoveredElements(final boolean[] array) {
        var arrayCopy = Arrays.copyOf(array, array.length);
        return markCoveredElements(arrayCopy);
    }

    public int markCoveredElements(boolean[] array) {
        int covered = 0;
        for (ISet subset : subsets) {
            covered += subset.markCoveredElements(array);
        }
        return covered;
    }
}
