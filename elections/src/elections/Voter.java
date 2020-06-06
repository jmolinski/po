package elections;

public class Voter extends Person {
    private final int baseConstituencyNumber;
    private final VoterStrategy strategy;

    public Voter(String firstName, String lastName, int baseConstituencyNumber, VoterStrategy strategy) {
        super(firstName, lastName);
        this.baseConstituencyNumber = baseConstituencyNumber;
        this.strategy = strategy;
    }

    public void applyModifierVector(int[] vector) {
        strategy.applyModifierVector(vector);
    }

    public void saveUpdatedPreferences() {
        strategy.saveUpdatedPreferences();
    }

    public void restoreSavedPreferences() {
        strategy.restoreSavedPreferences();
    }

    public int getCandidateScore(Candidate candidate) {
        return strategy.getMatchScore(candidate);
    }
}
