package wirusy;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class WirusSpecyficzny extends Wirus {
    private final Nukleotyd[] nukleotydyPoMutacji;
    private final int[] pozycjePodatneNaMutacje;

    public WirusSpecyficzny(Nukleotyd[] kwasNukleinowy, int mutacjeNaMiesiac,
                            int[] pozycjePodatneNaMutacje,
                            Nukleotyd[] nukleotydyPoMutacji) {
        super(kwasNukleinowy, mutacjeNaMiesiac);
        this.pozycjePodatneNaMutacje = pozycjePodatneNaMutacje;
        this.nukleotydyPoMutacji = Arrays.copyOf(nukleotydyPoMutacji, nukleotydyPoMutacji.length);
    }

    @Override
    protected void symulujMutacje() {
        Random r = new Random();
        int pozycja = pozycjePodatneNaMutacje[r.nextInt(pozycjePodatneNaMutacje.length)];
        Nukleotyd zmutowanyNukleotyd = nukleotydyPoMutacji[r.nextInt(nukleotydyPoMutacji.length)];

        while (zmutowanyNukleotyd == kwasNukleinowy[pozycja]) {
            // w każdym obrocie co najmniej 50% szans na przerwanie pętli
            // (zatem w praktyce zakończy się w rozsądnym czasie)
            zmutowanyNukleotyd = nukleotydyPoMutacji[r.nextInt(nukleotydyPoMutacji.length)];
        }

        kwasNukleinowy[pozycja] = zmutowanyNukleotyd;
    }
}
