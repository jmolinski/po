package wyrazenia;

import java.util.HashMap;

public class Zmienna extends Wyrazenie {
    int numer;
    static HashMap<Integer, Zmienna> zmienne = new HashMap<>();

    @Override
    protected int precedence() {
        return 0;
    }
    
    private Zmienna(int numer) {
        this.numer = numer;
    }

    public static Zmienna daj(int numer) {
        if (zmienne.containsKey(numer)) {
            return zmienne.get(numer);
        }

        zmienne.put(numer, new Zmienna(numer));
        return zmienne.get(numer);
    }

    @Override
    public String toString() {
        return "x" + String.valueOf(numer);
    }

    @Override
    public boolean wartosc(boolean... wartosciowanie_zmiennych) {
        return wartosciowanie_zmiennych[numer];
    }
}
