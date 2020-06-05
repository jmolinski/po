package elections;


class GreedyCampaignStrategy extends CampaignStrategy {
    @Override
    protected ActionConstituencyPair chooseActionConstituencyPair(Constituency[] constituencies,
                                                                  CampaignAction[] availableActions, Party party) {
        return null;
        //● są partie działające “zachłannie”, starają się wybierać takie działanie, które w
        //największym stopniu zwiększy sumę sum ważonych cech swoich kandydatów w
//danym okręgu wyborczym
        // TODO
    }
}

class LavishCampaignStrategy extends CampaignStrategy {
    @Override
    protected ActionConstituencyPair chooseActionConstituencyPair(Constituency[] constituencies,
                                                                  CampaignAction[] availableActions, Party party) {
        //● są partie działające “z rozmachem”, które zawsze wybierają najdroższe możliwe
//działanie (na które pozwala im budżet)
        return null;        // TODO
    }
}

class FrugalCampaignStrategy extends CampaignStrategy {
    @Override
    protected ActionConstituencyPair chooseActionConstituencyPair(Constituency[] constituencies,
                                                                  CampaignAction[] availableActions, Party party) {
        return null;        // TODO
        //● są partie działające “skromnie”, które zawsze wybierają najtańsze możliwe działanie
    }
}

class OwnCampaignStrategy extends CampaignStrategy {
    @Override
    protected ActionConstituencyPair chooseActionConstituencyPair(Constituency[] constituencies,
                                                                  CampaignAction[] availableActions, Party party) {
        return null;        // TODO
    }
}

public class PartyBuilder {
    private final String name;
    private final int budget;
    private final CampaignStrategy campaignStrategy;

    public PartyBuilder(String name, int budget, String strategyIdentifier) throws InvalidInputData {
        this.name = name;
        this.budget = budget;

        switch (strategyIdentifier) {
            case "R":
                campaignStrategy = new LavishCampaignStrategy();
                break;
            case "S":
                campaignStrategy = new FrugalCampaignStrategy();
                break;
            case "W":
                campaignStrategy = new OwnCampaignStrategy();
                break;
            case "Z":
                campaignStrategy = new GreedyCampaignStrategy();
                break;
            default:
                throw new InvalidInputData();
        }
    }

    public Party build() {
        return new Party(name, budget, campaignStrategy);
    }
}
