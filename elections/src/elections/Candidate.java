package elections;

import java.util.Arrays;

public class Candidate extends Person {
    private final int[] attributes;
    private final int numberOnList;
    private final Party party;

    public Candidate(String firstName, String lastName, int numberOnList, int[] attributes, Party party) {
        super(firstName, lastName);
        this.numberOnList = numberOnList;
        this.attributes = attributes;
        this.party = party;
    }

    public int[] getPropertyValues() {
        return Arrays.copyOf(attributes, attributes.length);
    }

    public int numberOnList() {
        return numberOnList;
    }

    public Party party() {
        return party;
    }
}
