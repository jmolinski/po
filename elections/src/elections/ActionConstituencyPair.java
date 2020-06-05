package elections;

public class ActionConstituencyPair {
    private final CampaignAction action;
    private final Constituency constituency;

    ActionConstituencyPair(CampaignAction action, Constituency constituency) {
        this.action = action;
        this.constituency = constituency;
    }

    public CampaignAction action() {
        return action;
    }

    public Constituency constituency() {
        return constituency;
    }
}
