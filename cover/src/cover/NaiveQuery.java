package cover;

import java.util.ArrayList;
import java.util.List;

public class NaiveQuery extends Query {

    public NaiveQuery(int range) {
        super(range);
    }

    @Override
    public List<Integer> execute(List<ISet> sets) {
        boolean[] elementsToCover = getElementsToCoverRepresentation();
        int notCovered = range;

        var result = new ArrayList<Integer>();

        for (int setNo = 0; setNo < sets.size(); setNo++) {
            if (sets.get(setNo).coversAnyElement(elementsToCover)) {
                notCovered -= sets.get(setNo).markCoveredElements(elementsToCover);
                result.add(setNo);
            }
            if (notCovered == 0) {
                return result;
            }
        }

        return List.of(-1);
    }
}
