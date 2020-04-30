package wirusy;

public class Symulacja {
    private final Szczepionka szczepionka;
    private final Wirus wirus;

    public Symulacja(Wirus wirus, Szczepionka szczepionka) {
        this.szczepionka = szczepionka;
        this.wirus = wirus;
    }

    public boolean czyWirusPodatnyPoSymulacji(int ileMiesiecy) {
        for (int i = 0; i < ileMiesiecy; i++) {
            this.wirus.symulujMiesiac();
        }

        return wirus.czyPodatnyNaGenerowanePrzeciwcialo(szczepionka);
    }
}
