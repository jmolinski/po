package cover;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class GreedyQuery extends Query {
    public GreedyQuery(int range) {
        super(range);
    }

    private int findBestSetCandidate(HashSet<Integer> usedSets, final boolean[] elementsToCover, List<ISet> sets) {
        int topSetNo = -1;
        int elementsCoveredByTopSet = -1;

        for (int i = 0; i < sets.size(); i++) {
            if (usedSets.contains(i)) {
                continue;
            }
            int elementsCoveredBySet = sets.get(i).countCoveredElements(elementsToCover);
            if (elementsCoveredByTopSet < elementsCoveredBySet) {
                topSetNo = i;
                elementsCoveredByTopSet = elementsCoveredBySet;
            }
        }

        return topSetNo;
    }

    @Override
    public List<Integer> execute(List<ISet> sets) {
        boolean[] elementsToCover = new boolean[range + 1]; // 1..range -> 0..range
        int covered = range;

        HashSet<Integer> usedSets = new HashSet<>();

        while (covered > 0 && usedSets.size() < sets.size()) {
            int topSetNo = findBestSetCandidate(usedSets, elementsToCover, sets);
            if (topSetNo == -1) {
                break;
            } else {
                usedSets.add(topSetNo);
                covered -= sets.get(topSetNo).markCoveredElements(elementsToCover);
                if (covered == 0) {
                    return usedSets.stream().map(setNo -> setNo + 1).collect(Collectors.toList());
                }
            }
        }

        return List.of(0);
    }
}
