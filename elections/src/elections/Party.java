package elections;

public class Party {
    private final String name;
    private final int budget;
    private final CampaignStrategy campaignStrategy;
    private int availableBudget;

    public Party(String name, int budget, CampaignStrategy strategy) {
        this.name = name;
        this.budget = budget;
        this.campaignStrategy = strategy;

        availableBudget = budget;
    }

    public void resetBudget() {
        availableBudget = budget;
    }

    public void runCampaign(Constituency[] constituencies, CampaignAction[] availableActions) {
        campaignStrategy.runCampaign(constituencies, availableActions, this);
    }

    public int getRemainingBudget() {
        return availableBudget;
    }

    public void decreaseRemainingBudget(int cost) {
        if (availableBudget < cost) {
            throw new IllegalArgumentException();
        }
        availableBudget -= cost;
    }

    public String name() {
        return name;
    }
}
