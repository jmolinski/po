package wirusy;

// autor Jakub Moliński 419502

public class Main {

    public static void main(String[] args) {

        // przyklad przebiegu symulacji
        Nukleotyd[] kwasWirusa = new Nukleotyd[]{Nukleotyd.A, Nukleotyd.A, Nukleotyd.A, Nukleotyd.G, Nukleotyd.C};

        Wirus wirus = new WirusDziwny(kwasWirusa, 3);
        Szczepionka szczepionka = new Szczepionka(new Nukleotyd[]{Nukleotyd.C, Nukleotyd.A, Nukleotyd.T});

        Symulacja symulacja = new Symulacja(wirus, szczepionka);

        boolean wynik_symulacji_po_2_miesiacach = symulacja.czyWirusPodatnyPoSymulacji(2);
        boolean wynik_symulacji_po_6_miesiacach = symulacja.czyWirusPodatnyPoSymulacji(4);
    }
}
