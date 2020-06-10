package elections;

public class MergedConstituencies implements Constituency {
    private final SingleConstituency constituency1;
    private final SingleConstituency constituency2;

    public MergedConstituencies(SingleConstituency constituency1, SingleConstituency constituency2) {
        this.constituency1 = constituency1;
        this.constituency2 = constituency2;
    }

    @Override
    public int votersCount() {
        return constituency1.votersCount() + constituency2.votersCount();
    }

    @Override
    public void permanentlyUpdateVotersPreferences(int[] vector) {
        constituency1.permanentlyUpdateVotersPreferences(vector);
        constituency2.permanentlyUpdateVotersPreferences(vector);
    }

    @Override
    public void temporarilyUpdateVotersPreferences(int[] vector) {
        constituency1.temporarilyUpdateVotersPreferences(vector);
        constituency2.temporarilyUpdateVotersPreferences(vector);
    }

    @Override
    public int checkCumulativePartyScoreAfterApplyingAction(CampaignAction action, Party party) {
        int score1 = constituency1.checkCumulativePartyScoreAfterApplyingAction(action, party);
        int score2 = constituency2.checkCumulativePartyScoreAfterApplyingAction(action, party);
        return score1 + score2;
    }
}
