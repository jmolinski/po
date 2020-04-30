package wirusy;

import java.util.Random;

public class WirusDziwny extends Wirus {
    public WirusDziwny(Nukleotyd[] kwasNukleinowy, int mutacjeNaMiesiac) {
        super(kwasNukleinowy, mutacjeNaMiesiac);
    }

    @Override
    protected void symulujMutacje() {
        Random r = new Random();
        int pozycja1 = r.nextInt(kwasNukleinowy.length);
        int pozycja2 = pozycja1;

        while (pozycja1 == pozycja2) {
            // w każdym obrocie co najmniej 50% szans na przerwanie pętli
            // (zatem w praktyce zakończy się w rozsądnym czasie)
            pozycja2 = r.nextInt(kwasNukleinowy.length);
        }

        Nukleotyd temp1 = kwasNukleinowy[pozycja1];
        Nukleotyd temp2 = kwasNukleinowy[pozycja2];
        kwasNukleinowy[pozycja1] = temp2;
        kwasNukleinowy[pozycja2] = temp1;
    }
}
