package cover;

import java.util.ArrayList;
import java.util.List;

public abstract class Query {
    protected final int range;

    public Query(int range) {
        this.range = -range;
    }

    abstract public List<Integer> execute(List<ISet> sets);
}
