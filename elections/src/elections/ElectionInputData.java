package elections;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class ElectionInputData {
    private int constituenciesCount;
    private int marketingActionsCount;
    private int candidateAttributesCount;

    private Party[] parties;
    private SingleConstituency[] baseConstituencies;
    private Constituency[] constituencies;
    private CampaignAction[] campaignActions;

    public ElectionInputData(Scanner inputScanner) throws InvalidInputData {
        try {
            readFirstLinesWithBasicConfiguration(inputScanner);
            readCandidatesList(inputScanner);
            readVotersList(inputScanner);
            readCampaignActionsList(inputScanner);
        } catch (Exception e) {
            throw new InvalidInputData();
        }
        inputScanner.close();
    }

    public int constituenciesCount() {
        return constituenciesCount;
    }

    public int partiesCount() {
        return parties.length;
    }

    public int marketingActionsCount() {
        return marketingActionsCount;
    }

    public int candidateAttributesCount() {
        return candidateAttributesCount;
    }

    public Party[] parties() {
        return parties;
    }

    public Constituency[] constituencies() {
        return constituencies;
    }

    public CampaignAction[] campaignActions() {
        return campaignActions;
    }

    private String[] getMatchedGroups(String regex, String text) {
        var matcher = Pattern.compile(regex).matcher(text);
        matcher.find();
        String[] groups = new String[matcher.groupCount()];

        for (int i = 0; i < matcher.groupCount(); i++) {
            groups[i] = matcher.group(i + 1);
        }

        return groups;
    }

    private void readFirstLinesWithBasicConfiguration(Scanner scanner) throws InvalidInputData {
        var groups = getMatchedGroups("\\s*(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)", scanner.nextLine());
        constituenciesCount = Integer.parseInt(groups[0]);
        var partiesCount = Integer.parseInt(groups[1]);
        marketingActionsCount = Integer.parseInt(groups[2]);
        candidateAttributesCount = Integer.parseInt(groups[3]);

        groups = getMatchedGroups("\\s*(\\d+)(.*)", scanner.nextLine());
        int numberOfMerges = Integer.parseInt(groups[0]);
        var firstConstituenciesOfToMergePairs = new int[numberOfMerges];
        Matcher m = Pattern.compile("\\((\\d+),\\d+\\)").matcher(groups[1]);
        for (int i = 0; i < numberOfMerges; i++) {
            m.find();
            firstConstituenciesOfToMergePairs[i] = Integer.parseInt(m.group(1));
        }

        readAndInitializeBasePartiesData(scanner, partiesCount);

        var votersByConstituency = Stream.of(scanner.nextLine().split("\\s")).filter(
                e -> !e.isEmpty()).mapToInt(Integer::parseInt).toArray();

        initializeConstituencies(numberOfMerges, votersByConstituency, firstConstituenciesOfToMergePairs);
    }

    private void readAndInitializeBasePartiesData(Scanner scanner, int partiesCount) throws InvalidInputData {
        var partiesNames = Stream.of(scanner.nextLine().split("\\s")).filter(e -> !e.isEmpty()).toArray(String[]::new);
        var partiesBudgets =
                Stream.of(scanner.nextLine().split("\\s")).filter(e -> !e.isEmpty()).mapToInt(Integer::parseInt)
                        .toArray();
        var partiesStrategies =
                Stream.of(scanner.nextLine().split("\\s")).filter(e -> !e.isEmpty()).toArray(String[]::new);

        parties = new Party[partiesCount];
        for (int i = 0; i < partiesCount; i++) {
            parties[i] = (new PartyBuilder(partiesNames[i], partiesBudgets[i], partiesStrategies[i])).build();
        }
    }

    private void initializeConstituencies(int numberOfMerges, int[] votersByConstituency,
                                          int[] firstConstituenciesOfToMergePairs) {
        baseConstituencies = new SingleConstituency[constituenciesCount];
        constituencies = new Constituency[constituenciesCount - numberOfMerges];

        for (int i = 0; i < constituenciesCount; i++) {
            baseConstituencies[i] = new SingleConstituency(votersByConstituency[i], i + 1);
        }
        for (int i = 0, j = 0; i < constituenciesCount; i++, j++) {
            int constituencyNumber = i + 1;
            if (Arrays.stream(firstConstituenciesOfToMergePairs).anyMatch(v -> v == constituencyNumber)) {
                constituencies[j] = new MergedConstituencies(baseConstituencies[i], baseConstituencies[i + 1]);
                i++;
            } else {
                constituencies[j] = baseConstituencies[i];
            }
        }
    }

    private void readCandidatesList(Scanner scanner) {
        for (SingleConstituency constituency : baseConstituencies) {
            for (Party party : parties) {
                for (int positionOnList = 1; positionOnList <= constituency.mandatesCount(); positionOnList++) {
                    constituency.addCandidate(party, parseSingleCandidate(scanner.nextLine(), party));
                }
            }
        }
    }

    private Candidate parseSingleCandidate(String line, Party party) {
        var groups = getMatchedGroups("(\\S+)\\s+(\\S+)\\s+\\d+\\s+\\S+\\s+(\\d+)\\s+(.*)", line);
        var candidateAttributes =
                Stream.of(groups[3].split("\\s")).filter(e -> !e.isEmpty()).mapToInt(Integer::parseInt).toArray();

        return new Candidate(groups[0], groups[1], Integer.parseInt(groups[2]), candidateAttributes, party);
    }

    private void readVotersList(Scanner scanner) throws InvalidInputData {
        for (int constituencyIndex = 0; constituencyIndex < constituenciesCount; constituencyIndex++) {
            for (int voterNumber = 1; voterNumber <= baseConstituencies[constituencyIndex].votersCount();
                 voterNumber++) {
                baseConstituencies[constituencyIndex].addVoter(parseSingleVoter(scanner.nextLine(),
                        constituencyIndex + 1));
            }
        }
    }

    private Voter parseSingleVoter(String line, int baseConstitituencyNumber) throws InvalidInputData {
        var groups = getMatchedGroups("(\\S+)\\s+(\\S+)\\s+(\\d+)\\s+(\\d+)\\s*(.*)", line);
        var voterBuilder = new VoterBuilder(groups[0], groups[1], baseConstitituencyNumber,
                Integer.parseInt(groups[3]), groups[4]);
        return voterBuilder.build();
    }

    private void readCampaignActionsList(Scanner scanner) {
        campaignActions = new CampaignAction[marketingActionsCount];
        for (int i = 0; i < marketingActionsCount; i++) {
            var actionVector = Stream.of(scanner.nextLine().split("\\s")).filter(
                    e -> !e.isEmpty()).mapToInt(Integer::parseInt).toArray();
            campaignActions[i] = new CampaignAction(actionVector);
        }
    }
}
