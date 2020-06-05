package elections;

public class Voter {
    private final String firstName;
    private final String lastName;
    private final int baseConstituencyNumber;
    private final VoterStrategy strategy;

    public Voter(String firstName, String lastName, int baseConstituencyNumber, VoterStrategy strategy) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.baseConstituencyNumber = baseConstituencyNumber;
        this.strategy = strategy;
    }

    public void applyModifierVector(int[] vector) {
        strategy.applyModifierVector(vector);
    }
}
