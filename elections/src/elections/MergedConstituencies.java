package elections;

import java.util.ArrayList;
import java.util.HashMap;

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
    public void permanentlyUpdateVotersPreferences(int[] vector) {
        constituency1.permanentlyUpdateVotersPreferences(vector);
        constituency2.permanentlyUpdateVotersPreferences(vector);
    }

    @Override
    public void temporarilyUpdateVotersPreferences(int[] vector) {
        constituency1.temporarilyUpdateVotersPreferences(vector);
        constituency2.temporarilyUpdateVotersPreferences(vector);
    }

    @Override
    public int checkCumulativePartyScoreAfterApplyingAction(CampaignAction action, Party party) {
        int score1 = constituency1.checkCumulativePartyScoreAfterApplyingAction(action, party);
        int score2 = constituency2.checkCumulativePartyScoreAfterApplyingAction(action, party);
        return score1 + score2;
    }

    @Override
    public int mandatesCount() {
        return constituency1.mandatesCount() + constituency2.mandatesCount();
    }

    @Override
    public String name() {
        return "(" + constituency1.name() + "," + constituency2.name() + ")";
    }

    @Override
    public ConstituencyResults castVotes() {
        var candidates = new ArrayList<Candidate>();
        for (var partyCandidates : constituency1.candidatesByParty().values()) {
            candidates.addAll(partyCandidates);
        }
        for (var partyCandidates : constituency2.candidatesByParty().values()) {
            candidates.addAll(partyCandidates);
        }
        var parties = constituency1.candidatesByParty().keySet().toArray(Party[]::new);

        var results = new ConstituencyResults(this, candidates.toArray(Candidate[]::new), parties);

        var candidatesArrayByParty = new HashMap<Party, Candidate[]>();
        for (Party party : parties) {
            var constituencyCandidates = new ArrayList<Candidate>();
            constituencyCandidates.addAll(constituency1.candidatesByParty().get(party));
            constituencyCandidates.addAll(constituency2.candidatesByParty().get(party));
            candidatesArrayByParty.put(party, constituencyCandidates.toArray(Candidate[]::new));
        }

        for (Voter voter : constituency1.voters()) {
            results.addVote(voter, voter.castVote(candidatesArrayByParty));
        }
        for (Voter voter : constituency1.voters()) {
            results.addVote(voter, voter.castVote(candidatesArrayByParty));
        }

        return results;
    }
}
