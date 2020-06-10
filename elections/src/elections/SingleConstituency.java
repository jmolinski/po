package elections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        var candidates = new ArrayList<Candidate>();
        for (var partyCandidates : candidatesByParty.values()) {
            candidates.addAll(partyCandidates);
        }
        var parties = candidatesByParty.keySet().toArray(Party[]::new);

        var results = new ConstituencyResults(this, candidates.toArray(Candidate[]::new), parties);

        var candidatesArrayByParty = new HashMap<Party, Candidate[]>();
        for (Party party : candidatesByParty.keySet()) {
            candidatesArrayByParty.put(party, candidatesByParty.get(party).toArray(Candidate[]::new));
        }

        for (Voter voter : voters) {
            results.addVote(voter, voter.castVote(candidatesArrayByParty));
        }

        return results;
    }

    public int mandatesCount() {
        return votersCount / 10;
    }

    @Override
    public String name() {
        return Integer.toString(baseNumber);
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

    public Map<Party, ArrayList<Candidate>> candidatesByParty() {
        return candidatesByParty;
    }

    public Voter[] voters() {
        return voters.toArray(Voter[]::new);
    }
}
