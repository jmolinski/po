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
    public void applyVectorModifierToVoters(int[] vector) {
        for (var voter: voters) {
            voter.applyModifierVector(vector);
        }
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
