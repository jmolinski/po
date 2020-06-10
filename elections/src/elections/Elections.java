package elections;

public class Elections {
    private final Party[] parties;
    private final Constituency[] constituencies;
    private final CampaignAction[] campaignActions;

    public Elections(ElectionInputData electionData) {
        parties = electionData.parties();
        constituencies = electionData.constituencies();
        campaignActions = electionData.campaignActions();
        runCampaignSimulation();
    }

    private void runCampaignSimulation() {
        for (var party : parties) {
            party.resetBudget();
            party.runCampaign(constituencies, campaignActions);
        }
    }

    public ElectionResults dhondtMethodResults() {
        return (new DHondtMethod(constituencies, parties)).getResults();
    }

    public ElectionResults sainteLagueMethodResults() {
        return (new SainteLagueMethod(constituencies, parties)).getResults();
    }

    public ElectionResults hateNiemeyerResults() {
        return (new HareNiemeyerMethod(constituencies, parties)).getResults();
    }

}

