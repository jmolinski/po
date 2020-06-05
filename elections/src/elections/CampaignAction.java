package elections;

import java.util.Arrays;

public class CampaignAction {
    int[] vector;

    public CampaignAction(int[] actionVector) {
        vector = actionVector;
    }

    public int calculateActionCost(Constituency constituency) {
        return Arrays.stream(vector).map(Math::abs).sum() * constituency.votersCount();
    }

    public int runOnConstituency(Constituency constituency) {
        int cost = calculateActionCost(constituency);
        constituency.applyVectorModifierToVoters(vector);
        return cost;
    }
}
