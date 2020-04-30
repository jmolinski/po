/**
 * Zaimplementuj klasÄ Wielomian udostÄpniajÄcÄ:
 * - tworzenie wielomianĂłw poprzez konstruktor ze zmiennÄ liczbÄ argumentĂłw
 * (moĹźna zaĹoĹźyÄ, Ĺźe wspĂłĹczynniki wielomianu bÄdÄ liczbami caĹkowitymi)
 * - wypisywanie wielomianu w sposĂłb czytelny dla czĹowieka (metoda toString)
 * - obliczanie wartoĹci wielomianu w punkcie
 * - dodawanie i mnoĹźenie wielomianĂłw
 * - obliczanie pochodnej
 * - wyznaczanie miejsc zerowych (opcjonalne)
 * <p>
 * Przydatne linki:
 * - https://pl.wikipedia.org/wiki/Schemat_Hornera
 * - https://pl.wikipedia.org/wiki/Metoda_r%C3%B3wnego_podzia%C5%82u
 */
package lab06;


import java.util.Arrays;

public class Wielomian {
    private final int[] wspolczynniki;

    public Wielomian(int... wspolczynniki) {
        this.wspolczynniki = wspolczynniki;
    }

    public double wartosc(double x) {
        double w = 0;

        for (int i = wspolczynniki.length - 1; i >= 0; i--) {
            w = w * x + wspolczynniki[i];
        }

        return w;
    }

    public Wielomian dodaj(Wielomian w) {
        int[] nowe_wspolczynniki;
        Wielomian krotszy;

        if (wspolczynniki.length > w.wspolczynniki.length) {
            nowe_wspolczynniki = Arrays.copyOf(wspolczynniki, wspolczynniki.length);
            krotszy = w;
        } else {
            nowe_wspolczynniki = Arrays.copyOf(w.wspolczynniki, w.wspolczynniki.length);
            krotszy = this;
        }

        for (int i = 0; i < krotszy.wspolczynniki.length; i++) {
            nowe_wspolczynniki[i] += krotszy.wspolczynniki[i];
        }

        return new Wielomian(nowe_wspolczynniki);
    }

    public Wielomian pomnoz(Wielomian w) {
        int[] nowe_wspolczynniki = new int[wspolczynniki.length + w.wspolczynniki.length - 1];

        for (int i = 0; i < wspolczynniki.length; i++) {
            for (int j = 0; j < w.wspolczynniki.length; j++) {
                nowe_wspolczynniki[i + j] += wspolczynniki[i] * w.wspolczynniki[j];
            }
        }

        return new Wielomian(nowe_wspolczynniki);
    }

    public Wielomian pochodna() {
        int[] wspolczynniki_pochodna = new int[Math.max(wspolczynniki.length - 1, 1)];

        for (int i = 1; i < wspolczynniki.length; i++) {
            wspolczynniki_pochodna[i - 1] = i * wspolczynniki[i];
        }

        return new Wielomian(wspolczynniki_pochodna);
    }

    public String toString() {
        StringBuilder reprezentacja = new StringBuilder();

        boolean wymus_znak = false;
        for (int i = wspolczynniki.length - 1; i >= 0; i--) {
            if (wspolczynniki[i] != 0) {
                if (wspolczynniki[i] < 0) {
                    reprezentacja.append('-');
                } else if (wymus_znak) {
                    reprezentacja.append('+');
                }
                wymus_znak = true;


                if (wspolczynniki[i] != 1 && wspolczynniki[i] != -1 || i == 0) {
                    reprezentacja.append(Math.abs(wspolczynniki[i]));
                }

                if (i != 0) {
                    reprezentacja.append('x');
                }
                if (i > 1) {
                    reprezentacja.append("^").append(i);
                }
            }
        }

        String s = reprezentacja.toString();
        return s.equals("") ? "0" : s;
    }
}
