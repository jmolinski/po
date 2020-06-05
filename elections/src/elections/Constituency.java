package elections;

public interface Constituency {
    int votersCount();
    void applyVectorModifierToVoters(int[] vector);
}
