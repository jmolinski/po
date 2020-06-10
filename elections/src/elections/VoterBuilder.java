package elections;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

abstract class VoterStrategy {
    void applyModifierVector(int[] vector) {
    }

    public void saveUpdatedPreferences() {
    }

    public void restoreSavedPreferences() {
    }

    public int getMatchScore(Candidate candidate) {
        return 0;
    }

    abstract public Candidate vote(HashMap<Party, Candidate[]> partyCandidates);
}

class DevoutPartyStrategy extends VoterStrategy {
    private final String partyName;

    DevoutPartyStrategy(String partyName) {
        this.partyName = partyName;
    }

    @Override
    public Candidate vote(HashMap<Party, Candidate[]> partyCandidates) {
        for (Party party : partyCandidates.keySet()) {
            if (party.name().equals(partyName)) {
                var candidates = partyCandidates.get(party);
                return candidates[new Random().nextInt(candidates.length)];
            }
        }

        return null;
    }
}

class DevoutCandidateStrategy extends VoterStrategy {
    private final int numberOnList;
    private final String partyName;

    DevoutCandidateStrategy(String partyName, int numberOnList) {
        this.partyName = partyName;
        this.numberOnList = numberOnList;
    }

    @Override
    public Candidate vote(HashMap<Party, Candidate[]> partyCandidates) {
        for (Party party : partyCandidates.keySet()) {
            if (party.name().equals(partyName)) {
                var candidates = partyCandidates.get(party);
                for (var candidate : candidates) {
                    if (candidate.numberOnList() == numberOnList) {
                        // in case of merged constituencies there are 2
                        // candidates with the same numberOnList and partyName
                        return candidate;
                    }
                }
            }
        }

        return null;
    }
}

abstract class OptimizingStrategy extends VoterStrategy {
    @Override
    public Candidate vote(HashMap<Party, Candidate[]> partyCandidates) {
        var candidates = getCandidatesSet(partyCandidates);
        var optimalCandidates = getOptimalCandidates(candidates);

        if (optimalCandidates.isEmpty()) {
            return null;
        }

        return optimalCandidates.get(new Random().nextInt(optimalCandidates.size()));
    }

    abstract protected List<Candidate> getOptimalCandidates(Candidate[] candidates);

    protected abstract Candidate[] getCandidatesSet(HashMap<Party, Candidate[]> partyCandidates);
}

abstract class OptimizeSingleParameterStrategy extends OptimizingStrategy {
    protected final int attribute;

    OptimizeSingleParameterStrategy(int attribute) {
        this.attribute = attribute;
    }

    protected List<Candidate> getOptimalCandidates(Candidate[] candidates) {
        var optimalCandidates = new ArrayList<Candidate>();
        for (var possibleCandidate : candidates) {
            if (optimalCandidates.isEmpty()) {
                optimalCandidates.add(possibleCandidate);
            } else {
                int comp = compareCandidates(possibleCandidate, optimalCandidates.get(0));
                if (comp > 0) {
                    optimalCandidates.clear();
                    optimalCandidates.add(possibleCandidate);
                } else if (comp == 0) {
                    optimalCandidates.add(possibleCandidate);
                }
            }
        }

        return optimalCandidates;
    }

    private int compareCandidates(Candidate oldCandidate, Candidate newCandidate) {
        int newValue = newCandidate.getPropertyValues()[attribute - 1];
        int oldValue = oldCandidate.getPropertyValues()[attribute - 1];
        if (isBetter(oldValue, newValue)) {
            return 1;
        } else {
            return newValue == oldValue ? 0 : -1;
        }
    }

    protected abstract boolean isBetter(int oldValue, int newValue);

    protected abstract Candidate[] getCandidatesSet(HashMap<Party, Candidate[]> partyCandidates);
}

abstract class MinimizeSingleParameterStrategy extends OptimizeSingleParameterStrategy {
    MinimizeSingleParameterStrategy(int attribute) {
        super(attribute);
    }

    @Override
    protected boolean isBetter(int oldValue, int newValue) {
        return newValue < oldValue;
    }
}

abstract class MaximizeSingleParameterStrategy extends OptimizeSingleParameterStrategy {
    MaximizeSingleParameterStrategy(int attribute) {
        super(attribute);
    }

    @Override
    protected boolean isBetter(int oldValue, int newValue) {
        return newValue > oldValue;
    }
}

class MinimizeSingleParameterAnyPartyStrategy extends MinimizeSingleParameterStrategy {
    MinimizeSingleParameterAnyPartyStrategy(int attribute) {
        super(attribute);
    }

    @Override
    protected Candidate[] getCandidatesSet(HashMap<Party, Candidate[]> partyCandidates) {
        var candidatesList = new ArrayList<Candidate>();
        for (Candidate[] candidates : partyCandidates.values()) {
            candidatesList.addAll(Arrays.asList(candidates));
        }
        return candidatesList.toArray(Candidate[]::new);
    }
}

class MaximizeSingleParameterAnyPartyStrategy extends MaximizeSingleParameterStrategy {
    MaximizeSingleParameterAnyPartyStrategy(int attribute) {
        super(attribute);
    }

    @Override
    protected Candidate[] getCandidatesSet(HashMap<Party, Candidate[]> partyCandidates) {
        var candidatesList = new ArrayList<Candidate>();
        for (Candidate[] candidates : partyCandidates.values()) {
            candidatesList.addAll(Arrays.asList(candidates));
        }
        return candidatesList.toArray(Candidate[]::new);
    }
}

class MinimizeSingleParameterSinglePartyStrategy extends MinimizeSingleParameterStrategy {
    private final String partyName;

    MinimizeSingleParameterSinglePartyStrategy(String partyName, int attribute) {
        super(attribute);
        this.partyName = partyName;
    }

    @Override
    protected Candidate[] getCandidatesSet(HashMap<Party, Candidate[]> partyCandidates) {
        for (Party party : partyCandidates.keySet()) {
            if (party.name().equals(partyName)) {
                return partyCandidates.get(party);
            }
        }
        return null;
    }
}

class MaximizeSingleParameterSinglePartyStrategy extends MaximizeSingleParameterStrategy {
    private final String partyName;

    MaximizeSingleParameterSinglePartyStrategy(String partyName, int attribute) {
        super(attribute);
        this.partyName = partyName;
    }

    @Override
    protected Candidate[] getCandidatesSet(HashMap<Party, Candidate[]> partyCandidates) {
        for (Party party : partyCandidates.keySet()) {
            if (party.name().equals(partyName)) {
                return partyCandidates.get(party);
            }
        }
        return null;
    }
}


abstract class OptimizeMultiParameterStrategy extends OptimizingStrategy {
    protected int[] coefficients;
    protected int[] savedCoefficients;

    OptimizeMultiParameterStrategy(int[] coefficients) {
        this.coefficients = coefficients;
        this.savedCoefficients = Arrays.copyOf(coefficients, coefficients.length);
    }

    @Override
    void applyModifierVector(int[] vector) {
        for (int i = 0; i < vector.length; i++) {
            int newValue = coefficients[i] + vector[i];
            if (newValue < -100) {
                newValue = -100;
            }
            if (newValue > 100) {
                newValue = 100;
            }

            coefficients[i] = newValue;
        }
    }

    @Override
    public int getMatchScore(Candidate candidate) {
        int score = 0;
        int[] candidateProperties = candidate.getPropertyValues();

        for (int i = 0; i < coefficients.length; i++) {
            score += coefficients[i] * candidateProperties[i];
        }

        return score;
    }

    @Override
    public void saveUpdatedPreferences() {
        savedCoefficients = Arrays.copyOf(coefficients, coefficients.length);
    }

    @Override
    public void restoreSavedPreferences() {
        coefficients = Arrays.copyOf(savedCoefficients, savedCoefficients.length);
    }

    @Override
    protected List<Candidate> getOptimalCandidates(Candidate[] candidates) {
        var optimalCandidates = new ArrayList<Candidate>();
        for (var possibleCandidate : candidates) {
            if (optimalCandidates.isEmpty()) {
                optimalCandidates.add(possibleCandidate);
            } else {
                int newCandidateScore = getMatchScore(possibleCandidate);
                int currentBestScore = getMatchScore(optimalCandidates.get(0));
                if (newCandidateScore > currentBestScore) {
                    optimalCandidates.clear();
                    optimalCandidates.add(possibleCandidate);
                } else if (newCandidateScore == currentBestScore) {
                    optimalCandidates.add(possibleCandidate);
                }
            }
        }

        return optimalCandidates;
    }

    protected abstract Candidate[] getCandidatesSet(HashMap<Party, Candidate[]> partyCandidates);
}

class MultiParameterAnyPartyStrategy extends OptimizeMultiParameterStrategy {
    MultiParameterAnyPartyStrategy(int[] coefficients) {
        super(coefficients);
    }

    @Override
    protected Candidate[] getCandidatesSet(HashMap<Party, Candidate[]> partyCandidates) {
        var candidatesList = new ArrayList<Candidate>();
        for (Candidate[] candidates : partyCandidates.values()) {
            candidatesList.addAll(Arrays.asList(candidates));
        }
        return candidatesList.toArray(Candidate[]::new);
    }
}

class MultiParameterSinglePartyStrategy extends OptimizeMultiParameterStrategy {
    private final String partyName;

    MultiParameterSinglePartyStrategy(String partyName, int[] coefficients) {
        super(coefficients);
        this.partyName = partyName;
    }

    @Override
    protected Candidate[] getCandidatesSet(HashMap<Party, Candidate[]> partyCandidates) {
        for (Party party : partyCandidates.keySet()) {
            if (party.name().equals(partyName)) {
                return partyCandidates.get(party);
            }
        }
        return null;
    }
}


public class VoterBuilder {
    private final String firstName;
    private final String lastName;
    private final int baseConstituencyNumber;
    private VoterStrategy strategy;

    public VoterBuilder(String firstName, String lastName, int baseConstituencyNumber, int type,
                        String extraArguments) throws InvalidInputData {
        this.firstName = firstName;
        this.lastName = lastName;
        this.baseConstituencyNumber = baseConstituencyNumber;

        chooseStrategy(type, extraArguments);
    }

    private void chooseStrategy(int type, String extraArguments) throws InvalidInputData {
        switch (type) {
            case 1: {
                strategy = new DevoutPartyStrategy(extraArguments.strip());
                break;
            }
            case 2: {
                var matcher = Pattern.compile("\\s*(\\S+)\\s+(\\d+)").matcher(extraArguments);
                matcher.find();
                strategy = new DevoutCandidateStrategy(matcher.group(1), Integer.parseInt(matcher.group(2)));
                break;
            }
            case 3: {
                strategy = new MinimizeSingleParameterAnyPartyStrategy(Integer.parseInt(extraArguments.strip()));
                break;
            }
            case 4: {
                strategy = new MaximizeSingleParameterAnyPartyStrategy(Integer.parseInt(extraArguments.strip()));
                break;
            }
            case 5: {
                int[] parameterCoefficients =
                        Stream.of(extraArguments.split("\\s")).filter(e -> !e.isEmpty()).mapToInt(Integer::parseInt)
                                .toArray();
                strategy = new MultiParameterAnyPartyStrategy(parameterCoefficients);
                break;
            }
            case 6: {
                var matcher = Pattern.compile("(\\d+)\\s+([]A-Za-z]+)").matcher(extraArguments);
                matcher.find();

                strategy = new MinimizeSingleParameterSinglePartyStrategy(matcher.group(2),
                        Integer.parseInt(matcher.group(1)));
                break;
            }
            case 7: {
                var matcher = Pattern.compile("(\\d+)\\s+([]A-Za-z]+)").matcher(extraArguments);
                matcher.find();
                strategy = new MaximizeSingleParameterSinglePartyStrategy(matcher.group(2),
                        Integer.parseInt(matcher.group(1)));
                break;
            }
            case 8: {
                var matcher = Pattern.compile("((-?\\d+\\s+)+)\\s*([]A-Za-z]+)").matcher(extraArguments);
                matcher.find();
                int[] parameterCoefficients =
                        Stream.of(matcher.group(1).split("\\s")).filter(e -> !e.isEmpty()).mapToInt(Integer::parseInt)
                                .toArray();
                strategy = new MultiParameterSinglePartyStrategy(matcher.group(3), parameterCoefficients);
                break;
            }
            default:
                throw new InvalidInputData();
        }
    }

    public Voter build() {
        return new Voter(firstName, lastName, baseConstituencyNumber, strategy);
    }
}
