package cover;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
        boolean[] elementsToCover = getElementsToCoverRepresentation();
        int notCovered = range;

        HashSet<Integer> usedSets = new HashSet<>();

        while (notCovered > 0 && usedSets.size() < sets.size()) {
            int topSetNo = findBestSetCandidate(usedSets, elementsToCover, sets);
            if (topSetNo == -1) {
                break;
            } else {
                usedSets.add(topSetNo);
                notCovered -= sets.get(topSetNo).markCoveredElements(elementsToCover);
                if (notCovered == 0) {
                    return new ArrayList<>(usedSets);
                }
            }
        }

        return List.of(-1);
    }
}
