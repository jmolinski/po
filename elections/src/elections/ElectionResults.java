package elections;

import java.util.HashMap;

public class ElectionResults {
    private final String methodName;
    private final HashMap<Constituency, ConstituencyResults> resultsByConstituency;

    public ElectionResults(String methodName, HashMap<Constituency, ConstituencyResults> resultsByConstituency) {
        this.methodName = methodName;
        this.resultsByConstituency = resultsByConstituency;
    }

    public void print() {
        System.out.println("\n" + methodName);

        for (var constituencyResults : resultsByConstituency.values()) {
            System.out.println("Constituency number " + constituencyResults.constituency().name());

            System.out.println("\nVoters' votes: ");
            var votes = constituencyResults.votes();
            for (var voter : votes.keySet()) {
                System.out.println(voter.name() + " " + votes.get(voter).name());
            }

            System.out.println("\nCandidates' received votes: ");
            var candidatesVotes = constituencyResults.candidateVotesCount();
            for (var candidate : candidatesVotes.keySet()) {
                System.out.println(
                        candidate.name() + " " + candidate.party().name() + " " + candidate.numberOnList() + " " +
                                candidatesVotes.get(candidate));
            }

            System.out.println("\nParties' received mandates: ");
            var partiesMandates = constituencyResults.mandatesByParty();
            for (var party : partiesMandates.keySet()) {
                System.out.println("(" + party.name() + ", " + partiesMandates.get(party) + ")");
            }
        }

        printOverallStatistics();
    }

    private void printOverallStatistics() {
        var mandatesPerParty = new HashMap<Party, Integer>();

        for (var constituencyResults : resultsByConstituency.values()) {
            var partiesMandates = constituencyResults.mandatesByParty();
            for (var party : partiesMandates.keySet()) {
                mandatesPerParty.put(party, mandatesPerParty.getOrDefault(party, 0) + partiesMandates.get(party));
            }
        }

        System.out.println("\nMandates per party overall:");
        for (Party party : mandatesPerParty.keySet()) {
            System.out.println("(" + party.name() + ", " + mandatesPerParty.get(party) + ")");
        }
    }
}

