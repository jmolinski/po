package elections;

public class MergedConstituencies implements Constituency {

    private final SingleConstituency constituency1;
    private final SingleConstituency constituency2;

    public MergedConstituencies(SingleConstituency constituency1, SingleConstituency constituency2) {
        this.constituency1 = constituency1;
        this.constituency2 = constituency2;
    }

    @Override
    public int votersCount() {
        return constituency1.votersCount() + constituency2.votersCount();
    }

    @Override
    public void applyVectorModifierToVoters(int[] vector) {

    }
}
