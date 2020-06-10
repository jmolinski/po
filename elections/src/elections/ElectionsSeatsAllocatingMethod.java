package elections;

import java.util.HashMap;

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

class DHondtMethod extends ElectionsSeatsAllocatingMethod {
    public DHondtMethod(Constituency[] constituencies, Party[] parties) {
        super(constituencies, parties);
    }

    protected String methodName() {
        return "D'Hondt Method";
    }

    protected HashMap<Party, Integer> calculateMandatesByParty(HashMap<Party, Integer> votesPerParty,
                                                               int mandatesCount) {
        return null;  // TODO
    }
}


class SainteLagueMethod extends ElectionsSeatsAllocatingMethod {
    public SainteLagueMethod(Constituency[] constituencies, Party[] parties) {
        super(constituencies, parties);
    }

    protected String methodName() {
        return "Sainte-Lague Method";
    }

    protected HashMap<Party, Integer> calculateMandatesByParty(HashMap<Party, Integer> votesPerParty,
                                                               int mandatesCount) {
        return null;  // TODO
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
        return null;  // TODO
    }
}