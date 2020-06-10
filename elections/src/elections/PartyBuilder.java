package elections;


class GreedyCampaignStrategy extends CampaignStrategy {
    @Override
    protected ActionConstituencyPair chooseActionConstituencyPair(Constituency[] constituencies,
                                                                  CampaignAction[] availableActions, Party party) {
        Constituency bestConstituency = null;
        CampaignAction bestAction = null;
        int bestNewScore = 0;   // TODO zamiast tego wyznaczanie różnicy

        for (Constituency constituency : constituencies) {
            for (CampaignAction action : availableActions) {
                if (action.calculateActionCost(constituency) <= party.getRemainingBudget()) {
                    int scoreAfterApplyingAction = getScoreAfterApplyingAction(constituency, action, party);
                    if (bestAction == null || bestNewScore > scoreAfterApplyingAction) {
                        bestAction = action;
                        bestConstituency = constituency;
                        bestNewScore = scoreAfterApplyingAction;
                    }
                }
            }
        }

        return bestAction == null ? null : new ActionConstituencyPair(bestAction, bestConstituency);
    }

    private int getScoreAfterApplyingAction(Constituency constituency, CampaignAction action, Party party) {
        return constituency.checkCumulativePartyScoreAfterApplyingAction(action, party);
    }
}

abstract class CostOptimizingCampaignStrategy extends CampaignStrategy {
    @Override
    protected ActionConstituencyPair chooseActionConstituencyPair(Constituency[] constituencies,
                                                                  CampaignAction[] availableActions, Party party) {
        Constituency bestConstituency = null;
        CampaignAction bestAction = null;
        int actionCost = getStartingActionPrice(party);

        for (Constituency constituency : constituencies) {
            for (CampaignAction action : availableActions) {
                int thisCombinationCost = action.calculateActionCost(constituency);
                if (shouldPreferNewAction(actionCost, thisCombinationCost, party)) {
                    bestAction = action;
                    bestConstituency = constituency;
                    actionCost = thisCombinationCost;
                }
            }
        }

        return bestAction == null ? null : new ActionConstituencyPair(bestAction, bestConstituency);
    }

    abstract boolean shouldPreferNewAction(int currentCost, int newCost, Party party);

    abstract int getStartingActionPrice(Party party);
}

class LavishCampaignStrategy extends CostOptimizingCampaignStrategy {
    @Override
    boolean shouldPreferNewAction(int currentCost, int newCost, Party party) {
        return newCost > currentCost && newCost < party.getRemainingBudget();
    }

    @Override
    int getStartingActionPrice(Party party) {
        return -1;
    }
}

class FrugalCampaignStrategy extends CostOptimizingCampaignStrategy {
    @Override
    boolean shouldPreferNewAction(int currentCost, int newCost, Party party) {
        return newCost < currentCost && newCost > 0;
    }

    @Override
    int getStartingActionPrice(Party party) {
        return party.getRemainingBudget() + 1;
    }
}

class LazyCampaignStrategy extends CampaignStrategy {
    @Override
    protected ActionConstituencyPair chooseActionConstituencyPair(Constituency[] constituencies,
                                                                  CampaignAction[] availableActions, Party party) {
        for (Constituency constituency : constituencies) {
            for (CampaignAction action : availableActions) {
                if (action.calculateActionCost(constituency) <= party.getRemainingBudget()) {
                    return new ActionConstituencyPair(action, constituency);
                }
            }
        }

        return null;
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
                campaignStrategy = new LazyCampaignStrategy();
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
