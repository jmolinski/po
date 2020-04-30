package wyrazenia;

public class Xor extends Wyrazenie {
    Wyrazenie dziecko2;

    protected int precedence() {
        return 5;
    }

    public Xor(Wyrazenie rodzic, Wyrazenie rodzic2) {
        this.dziecko = rodzic;
        this.dziecko2 = rodzic2;
    }

    @Override
    public String toString() {
        return dziecko.toString(precedence()) + "^" + dziecko2.toString(precedence());
    }

    @Override
    public boolean wartosc(boolean... wartosciowanie_zmiennych) {
        return dziecko.wartosc(wartosciowanie_zmiennych) ^ dziecko2.wartosc(wartosciowanie_zmiennych);
    }
}
