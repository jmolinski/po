package elections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

abstract public class ElectionsSeatsAllocatingMethod {
    protected final HashMap<Constituency, ConstituencyResults> resultsByConstituency;
    protected final Constituency[] constituencies;
    protected final Party[] parties;

    protected ElectionsSeatsAllocatingMethod(Constituency[] constituencies, Party[] parties) {
        this.constituencies = constituencies;
        this.parties = parties;

        this.resultsByConstituency = castVotesInConstituencies();
    }

    private HashMap<Constituency, ConstituencyResults> castVotesInConstituencies() {
        var resultsByConstituency = new HashMap<Constituency, ConstituencyResults>();
        for (var constituency : constituencies) {
            resultsByConstituency.put(constituency, constituency.castVotes());
        }
        return resultsByConstituency;
    }

    public ElectionResults getResults() {
        for (ConstituencyResults constituencyResults : resultsByConstituency.values()) {
            constituencyResults.mandatesByParty(calculateMandatesByParty(constituencyResults.partyVotesCount(),
                    constituencyResults.constituency().mandatesCount()));
        }
        return new ElectionResults(methodName(), resultsByConstituency);
    }

    protected abstract HashMap<Party, Integer> calculateMandatesByParty(HashMap<Party, Integer> votesPerParty,
                                                                        int mandatesCount);

    protected abstract String methodName();
}

abstract class QuotientBasedSeatsAllocatingMethod extends ElectionsSeatsAllocatingMethod {
    protected QuotientBasedSeatsAllocatingMethod(Constituency[] constituencies, Party[] parties) {
        super(constituencies, parties);
    }

    protected HashMap<Party, Integer> calculateMandatesByParty(HashMap<Party, Integer> votesPerParty,
                                                               int mandatesCount) {
        var mandatesByParty = new HashMap<Party, Integer>();
        for (Party p : votesPerParty.keySet()) {
            mandatesByParty.put(p, 0);
        }

        for (int i = 0; i < mandatesCount; i++) {
            var candidatesForMandate = getCandidatesForNextMandate(mandatesByParty, votesPerParty);
            var party = candidatesForMandate[(new Random().nextInt(candidatesForMandate.length))];
            mandatesByParty.put(party, mandatesByParty.get(party) + 1);
        }

        return mandatesByParty;
    }

    private Party[] getCandidatesForNextMandate(HashMap<Party, Integer> mandates, HashMap<Party, Integer> votes) {
        double maxQuotient = -1;
        for (Party party : mandates.keySet()) {
            double partyQuotient = getQuotient(mandates.get(party), votes.get(party));
            if (partyQuotient > maxQuotient) {
                maxQuotient = partyQuotient;
            }
        }

        var candidates = new ArrayList<Party>();
        for (Party party : mandates.keySet()) {
            double partyQuotient = getQuotient(mandates.get(party), votes.get(party));
            if (Double.compare(partyQuotient, maxQuotient) == 0) {
                candidates.add(party);
            }
        }

        return candidates.toArray(Party[]::new);
    }

    protected abstract double getQuotient(double mandates, double votes);
}


class DHondtMethod extends QuotientBasedSeatsAllocatingMethod {
    public DHondtMethod(Constituency[] constituencies, Party[] parties) {
        super(constituencies, parties);
    }

    protected String methodName() {
        return "D'Hondt Method";
    }

    @Override
    protected double getQuotient(double mandates, double votes) {
        return votes / (mandates + 1);
    }
}


class SainteLagueMethod extends QuotientBasedSeatsAllocatingMethod {
    public SainteLagueMethod(Constituency[] constituencies, Party[] parties) {
        super(constituencies, parties);
    }

    protected String methodName() {
        return "Sainte-Lague Method";
    }

    @Override
    protected double getQuotient(double mandates, double votes) {
        return votes / (2 * mandates + 1);
    }
}


class HareNiemeyerMethod extends ElectionsSeatsAllocatingMethod {
    public HareNiemeyerMethod(Constituency[] constituencies, Party[] parties) {
        super(constituencies, parties);
    }

    protected String methodName() {
        return "Hare-Niemeyer Method";
    }

    protected HashMap<Party, Integer> calculateMandatesByParty(HashMap<Party, Integer> votesPerParty,
                                                               int mandatesCount) {
        var partyScores = getPartyScores(votesPerParty, mandatesCount);
        var mandatesByParty = new HashMap<Party, Integer>();

        int remainingMandates = mandatesCount;
        for (Party party : partyScores.keySet()) {
            int partyMandates = partyScores.get(party).intValue();
            mandatesByParty.put(party, partyMandates);
            partyScores.put(party, partyScores.get(party) - partyMandates);
            remainingMandates -= partyMandates;
        }

        while (remainingMandates > 0) {
            double maxScore = -1;
            Party nextPartyToGetMandate = null;

            for (Party party : partyScores.keySet()) {
                if (partyScores.get(party) > maxScore) {
                    maxScore = partyScores.get(party);
                    nextPartyToGetMandate = party;
                }
            }

            mandatesByParty.put(nextPartyToGetMandate, mandatesByParty.get(nextPartyToGetMandate) + 1);
            partyScores.remove(nextPartyToGetMandate);
            remainingMandates -= 1;
        }

        return mandatesByParty;
    }

    private HashMap<Party, Double> getPartyScores(HashMap<Party, Integer> votesPerParty, int mandatesCount) {
        int totalVotes = 0;
        for (int partyVotes : votesPerParty.values()) {
            totalVotes += partyVotes;
        }

        var partyScores = new HashMap<Party, Double>();
        for (Party party : votesPerParty.keySet()) {
            partyScores.put(party, (double) (votesPerParty.get(party) * mandatesCount) / (double) totalVotes);
        }

        return partyScores;
    }
}