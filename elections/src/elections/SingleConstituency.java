package elections;

import java.util.ArrayList;
import java.util.HashMap;

public class SingleConstituency implements Constituency {
    private final int votersCount;
    private final int baseNumber;
    private final ArrayList<Voter> voters;
    private final HashMap<Party, ArrayList<Candidate>> candidatesByParty;

    public SingleConstituency(int votersCount, int baseNumber) {
        this.votersCount = votersCount;
        this.baseNumber = baseNumber;
        voters = new ArrayList<>();
        candidatesByParty = new HashMap<>();
    }

    @Override
    public int votersCount() {
        return votersCount;
    }

    @Override
    public void permanentlyUpdateVotersPreferences(int[] vector) {
        for (var voter : voters) {
            voter.applyModifierVector(vector);
            voter.saveUpdatedPreferences();
        }
    }

    @Override
    public void temporarilyUpdateVotersPreferences(int[] vector) {
        for (var voter : voters) {
            voter.applyModifierVector(vector);
        }
    }

    @Override
    public int checkCumulativePartyScoreAfterApplyingAction(CampaignAction action, Party party) {
        action.temporarilyUpdateVotersPreferences(this);

        int totalScore = 0;
        for (Voter voter : voters) {
            for (Candidate candidate : candidatesByParty.get(party)) {
                totalScore += voter.getCandidateScore(candidate);
            }
            voter.restoreSavedPreferences();
        }

        return totalScore;
    }

    @Override
    public ConstituencyResults castVotes() {
        return null;  // TODO
    }

    public int mandatesCount() {
        return votersCount / 10;
    }

    public void addVoter(Voter voter) {
        voters.add(voter);
    }

    public void addCandidate(Party party, Candidate candidate) {
        if (!candidatesByParty.containsKey(party)) {
            candidatesByParty.put(party, new ArrayList<>());
        }
        candidatesByParty.get(party).add(candidate);
    }
}
