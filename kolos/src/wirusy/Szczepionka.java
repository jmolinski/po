package wirusy;

import java.util.Arrays;

public class Szczepionka {
    private final Nukleotyd[] atakowanySegmentKwasu;

    Szczepionka(Nukleotyd[] atakowanySegmentKwasu) {
        this.atakowanySegmentKwasu = Arrays.copyOf(atakowanySegmentKwasu, atakowanySegmentKwasu.length);
    }

    public Nukleotyd[] przeciwcialo() {
        return Arrays.copyOf(atakowanySegmentKwasu, atakowanySegmentKwasu.length);
    }
}
