package elections;

import java.util.regex.Pattern;
import java.util.stream.Stream;

abstract class VoterStrategy {

    void applyModifierVector(int[] vector) {
    }
}

abstract class AbstractMultiParameterStrategy extends VoterStrategy {
    protected int[] coefficients;

    AbstractMultiParameterStrategy(int[] coefficients) {
        this.coefficients = coefficients;
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
}

class DevoutPartyStrategy extends VoterStrategy {
    DevoutPartyStrategy(String partyName) {
        //1. Żelazny elektorat partyjny zawsze głosuje na losowego kandydata na liście danej
        //partii.
        // TODO
    }
}

class DevoutCandidateStrategy extends VoterStrategy {
    DevoutCandidateStrategy(String partyName, int numberOnList) {
        //2. Żelazny elektorat kandydata zawsze głosuje na danego kandydata.
        // TODO
    }
}

class MinimizeSingleParameterStrategy extends VoterStrategy {
    MinimizeSingleParameterStrategy(int attribute) {
        // . Minimalizujący jednocechowy wybiera tego spośród kandydatów wszystkich partii,
        //który ma najniższy poziom wybranej przez niego cechy (jeśli taką wartość ma więcej
        //niż 1 kandydat, to wybór kandydata jest losowy).
        // TODO
    }
}

class MaximizeSingleParameterStrategy extends VoterStrategy {
    MaximizeSingleParameterStrategy(int attribute) {
        //  Maksymalizujący jednocechowy wybiera tego spośród kandydatów wszystkich partii,
        //który ma najwyższy poziom wybranej przez niego cechy (jeśli taką wartość ma więcej
        //niż 1 kandydat, to wybór kandydata jest losowy).
        // TODO
    }
}

class MultiParameterAnyPartyStrategy extends AbstractMultiParameterStrategy {
    MultiParameterAnyPartyStrategy(int[] coefficients) {
        super(coefficients);
        //  Maksymalizujący jednocechowy wybiera tego spośród kandydatów wszystkich partii,
        //który ma najwyższy poziom wybranej przez niego cechy (jeśli taką wartość ma więcej
        //niż 1 kandydat, to wybór kandydata jest losowy).
        // TODO
    }
}

class MinimizeSingleParameterSinglePartyStrategy extends VoterStrategy {
    MinimizeSingleParameterSinglePartyStrategy(String partyName, int attribute) {
        // . Minimalizujący jednocechowy wybiera tego spośród kandydatów wszystkich partii,
        //który ma najniższy poziom wybranej przez niego cechy (jeśli taką wartość ma więcej
        //niż 1 kandydat, to wybór kandydata jest losowy).
        // TODO
    }
}

class MaximizeSingleParameterSinglePartyStrategy extends VoterStrategy {
    MaximizeSingleParameterSinglePartyStrategy(String partyName, int attribute) {
        //  Maksymalizujący jednocechowy wybiera tego spośród kandydatów wszystkich partii,
        //który ma najwyższy poziom wybranej przez niego cechy (jeśli taką wartość ma więcej
        //niż 1 kandydat, to wybór kandydata jest losowy).
        // TODO
    }
}

class MultiParameterSinglePartyStrategy extends AbstractMultiParameterStrategy {
    MultiParameterSinglePartyStrategy(String partyName, int[] coefficients) {
        super(coefficients);
        //  Maksymalizujący jednocechowy wybiera tego spośród kandydatów wszystkich partii,
        //który ma najwyższy poziom wybranej przez niego cechy (jeśli taką wartość ma więcej
        //niż 1 kandydat, to wybór kandydata jest losowy).
        // TODO
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
                strategy = new MinimizeSingleParameterStrategy(Integer.parseInt(extraArguments.strip()));
                break;
            }
            case 4: {
                strategy = new MaximizeSingleParameterStrategy(Integer.parseInt(extraArguments.strip()));
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
