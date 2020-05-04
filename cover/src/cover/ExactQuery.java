package cover;

import java.util.*;

public class ExactQuery extends Query {
    public ExactQuery(int range) {
        super(range);
    }

    static private List<Integer[]> getSetCombinations(int n, int r) {
        List<Integer[]> combinations = new ArrayList<>();
        Integer[] combination = new Integer[r];

        for (int i = 0; i < r; i++) {
            combination[i] = i;
        }

        while (combination[r - 1] < n) {
            // generuje kolejne kombinacje w leksykograficznej kolejności

            combinations.add(combination.clone());

            int t = r - 1;
            while (t != 0 && combination[t] == n - r + t) {
                t--;
            }
            combination[t]++;
            for (int i = t + 1; i < r; i++) {
                combination[i] = combination[i - 1] + 1;
            }
        }

        return combinations;
    }

    private boolean canCoverWithChosenSets(Integer[] chosenSetsNumbers, ISet[] allSets) {
        boolean[] elementsToCover = getElementsToCoverRepresentation();
        int notCovered = range;

        for (int setNo : chosenSetsNumbers) {
            int coveredBySet = allSets[setNo].markCoveredElements(elementsToCover);
            if (coveredBySet == 0) {
                return false;  // Ten wybór nie jest optymalny.
            }
            notCovered -= coveredBySet;
        }

        return notCovered == 0;
    }

    @Override
    public List<Integer> execute(List<ISet> sets) {

        ISet[] setsArray = new ISet[sets.size()];
        setsArray = sets.toArray(setsArray);

        for (int k = 1; k <= setsArray.length; k++) {
            for (var setsCombination : getSetCombinations(setsArray.length, k)) {
                if (canCoverWithChosenSets(setsCombination, setsArray)) {
                    return Arrays.asList(setsCombination);
                }
            }
        }

        return List.of(-1);
    }
}
