package cover;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExactQuery extends Query {
    public ExactQuery(int range) {
        super(range);
    }

    static public List<Integer[]> generateCombinations(int n, int r) {
        List<Integer[]> combinations = new ArrayList<>();
        Integer[] combination = new Integer[r];

        // initialize with lowest lexicographic combination
        for (int i = 0; i < r; i++) {
            combination[i] = i;
        }

        while (combination[r - 1] < n) {
            combinations.add(combination.clone());

            // generate next combination in lexicographic order
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

    @Override
    public List<Integer> execute(List<ISet> sets) {
        for (int k = 1; k <= sets.size(); k++) {
            for (var setsCombination : generateCombinations(sets.size(), k)) {
                boolean[] elementsToCover = new boolean[range + 1]; // 1..range -> 0..range
                int covered = range;

                for (int setNo : setsCombination) {
                    covered -= sets.get(setNo).markCoveredElements(elementsToCover);;
                }
                if (covered == 0) {
                    return Stream.of(setsCombination).map(n -> n + 1).collect(Collectors.toList());
                }
            }
        }

        return List.of(0);
    }
}
