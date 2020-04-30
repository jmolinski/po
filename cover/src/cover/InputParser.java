package cover;

import java.util.ArrayList;

public class InputParser {

    private final ArrayList<Integer> tokens;
    private int pointer;

    public InputParser(ArrayList<Integer> tokens) {
        this.tokens = tokens;
        this.pointer = 0;
    }

    private boolean hasNextToken() {
        return pointer < tokens.size();
    }

    private int peekNextToken() {
        return tokens.get(pointer);
    }

    private int nextToken() {
        return tokens.get(pointer++);
    }

    public boolean hasNextSet() {
        if (!hasNextToken()) return false;
        return peekNextToken() >= 0;
    }

    public boolean hasNextQuery() {
        if (!hasNextToken()) return false;
        return peekNextToken() < 0;
    }

    private ISet nextSetElement() {
        int start = nextToken();
        if (start == 0) {
            return new FiniteRangeSet(start, start - 1, 0);
        } else if (peekNextToken() >= 0) {
            return new SingleElementSet(start);
        } else {
            int step = nextToken();
            if (peekNextToken() >= 0) {
                return new InfiniteRangeSet(start, step);
            } else {
                return new FiniteRangeSet(start, step, nextToken());
            }
        }
    }

    public ISet nextSet() {
        SetUnion set = new SetUnion();

        while (peekNextToken() > 0) {
            set.addSubset(nextSetElement());
        }

        nextToken(); // terminating 0
        return set;
    }

    public Query nextQuery() {
        int range = nextToken();
        int strategy = nextToken();
        if (strategy == 1) {
            return new ExactQuery(range);
        } else if (strategy == 2) {
            return new GreedyQuery(range);
        } else {
            return new NaiveQuery(range);
        }
    }
}