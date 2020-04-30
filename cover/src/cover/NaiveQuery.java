package cover;

import java.util.ArrayList;
import java.util.List;

public class NaiveQuery extends Query {

    public NaiveQuery(int range) {
        super(range);
    }

    @Override
    public List<Integer> execute(List<ISet> sets) {
        boolean[] elementsToCover = new boolean[range + 1]; // 1..range -> 0..range
        int covered = range; //

        var result = new ArrayList<Integer>();

        for (int setNo = 0; setNo < sets.size(); setNo++) {
            if (sets.get(setNo).coversAnyElement(elementsToCover)) {
                covered -= sets.get(setNo).markCoveredElements(elementsToCover);
                result.add(setNo + 1); // Sety są liczone od 1, nie od 0.
            }
            if (covered == 0) {
                return result;
            }
        }

        return List.of(0);
    }
}
