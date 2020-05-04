package cover;

import java.util.List;

public abstract class Query {
    protected final int range;

    public Query(int range) {
        this.range = -range;
    }

    abstract public List<Integer> execute(List<ISet> sets);

    protected boolean[] getElementsToCoverRepresentation() {
        // Dla ułatwienia indeksowania 1..range -> 0..range, 0 nie musi zostać pokryte.
        return new boolean[range + 1];
    }
}
