package elections;

import java.util.Arrays;

public class Candidate extends Person {
    private final int[] attributes;

    public Candidate(String firstName, String lastName, int[] attributes) {
        super(firstName, lastName);
        this.attributes = attributes;
    }

    public int[] getPropertyValues() {
        return Arrays.copyOf(attributes, attributes.length);
    }
}
