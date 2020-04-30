package wirusy;

import java.util.Arrays;

public abstract class Wirus {
    protected Nukleotyd[] kwasNukleinowy;
    protected final int mutacjeNaMiesiac;

    protected Wirus(Nukleotyd[] kwasNukleinowy, int mutacjeNaMiesiac) {
        this.kwasNukleinowy = Arrays.copyOf(kwasNukleinowy, kwasNukleinowy.length);
        this.mutacjeNaMiesiac = mutacjeNaMiesiac;
    }

    public void symulujMiesiac() {
        for (int i = 0; i < mutacjeNaMiesiac; i++) {
            symulujMutacje();
        }
    }

    protected abstract void symulujMutacje();

    public boolean czyPodatnyNaGenerowanePrzeciwcialo(Szczepionka szczepionka) {
        return czyKwasZawieraSekwencje(szczepionka.przeciwcialo());
    }

    private boolean czyKwasZawieraSekwencje(Nukleotyd[] sekwencja) {
        for (int i = 0; i < kwasNukleinowy.length - sekwencja.length + 1; i++) {
            if (czyPoczatekSekwencji(i, sekwencja)) {
                return true;
            }
        }
        return false;
    }

    private boolean czyPoczatekSekwencji(int pozycjaWKwasie, Nukleotyd[] sekwencja) {
        for (int i = 0; i < sekwencja.length; i++) {
            if (i + pozycjaWKwasie > kwasNukleinowy.length || kwasNukleinowy[i + pozycjaWKwasie] != sekwencja[i]) {
                return false;
            }
        }

        return true;
    }
}
