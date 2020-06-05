package elections;

public abstract class CampaignStrategy {
    public void runCampaign(Constituency[] constituencies, CampaignAction[] availableActions, Party party) {
        while (true) {
            var actionConstituencyPair = chooseActionConstituencyPair(constituencies, availableActions, party);
            if (actionConstituencyPair == null) {
                break;
            }

            int cost = actionConstituencyPair.action().runOnConstituency(actionConstituencyPair.constituency());
            party.decreaseRemainingBudget(cost);
        }
    }

    protected abstract ActionConstituencyPair chooseActionConstituencyPair(Constituency[] constituencies,
                                                         CampaignAction[] availableActions, Party party);
}
