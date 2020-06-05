package elections;

public class ElectionResults {
    private final Party[] parties;
    private final Constituency[] constituencies;
    private final CampaignAction[] campaignActions;

    public ElectionResults(ElectionInputData electionData) {
        parties = electionData.parties();
        constituencies = electionData.constituencies();
        campaignActions = electionData.campaignActions();
        runCampaignSimulation();
    }

    private void runCampaignSimulation() {
        for (var party: parties) {
            party.resetBudget();
            party.runCampaign(constituencies, campaignActions);
        }
    }
}

