package elections;

import java.util.HashMap;

public class ConstituencyResults {
    private final Constituency constituency;

    private final HashMap<Voter, Candidate> votes;
    private final HashMap<Candidate, Integer> candidateVotesCount;
    private final HashMap<Party, Integer> partyVotesCount;
    private HashMap<Party, Integer> mandatesByParty;

    ConstituencyResults(Constituency constituency, Candidate[] candidates, Party[] parties) {
        this.constituency = constituency;

        votes = new HashMap<>();

        candidateVotesCount = new HashMap<>();
        for (var candidate : candidates) {
            candidateVotesCount.put(candidate, 0);
        }

        partyVotesCount = new HashMap<>();
        for (var party : parties) {
            partyVotesCount.put(party, 0);
        }
    }

    void addVote(Voter voter, Candidate candidate) {
        votes.put(voter, candidate);
        candidateVotesCount.put(candidate, candidateVotesCount.get(candidate) + 1);
        partyVotesCount.put(candidate.party(), partyVotesCount.get(candidate.party()) + 1);
    }

    public Constituency constituency() {
        return constituency;
    }

    public HashMap<Voter, Candidate> votes() {
        return votes;
    }

    public HashMap<Candidate, Integer> candidateVotesCount() {
        return candidateVotesCount;
    }

    public HashMap<Party, Integer> partyVotesCount() {
        return partyVotesCount;
    }

    public HashMap<Party, Integer> mandatesByParty() {
        return mandatesByParty;
    }

    public void mandatesByParty(HashMap<Party, Integer> mandatesByParty) {
        this.mandatesByParty = mandatesByParty;
    }
}
