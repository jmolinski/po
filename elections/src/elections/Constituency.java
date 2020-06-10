package elections;

public interface Constituency {
    int votersCount();

    void permanentlyUpdateVotersPreferences(int[] vector);

    void temporarilyUpdateVotersPreferences(int[] vector);
    
    int checkCumulativePartyScoreAfterApplyingAction(CampaignAction action, Party party);

    ConstituencyResults castVotes();

    int mandatesCount();
}
