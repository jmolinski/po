package wyrazenia;

public class Neg extends Wyrazenie {
    public Neg(Wyrazenie rodzic) {
        this.dziecko = rodzic;
    }

    protected int precedence() {
        return 2;
    }

    @Override
    public String toString() {
        return "~" + dziecko.toString(2);
    }

    @Override
    public boolean wartosc(boolean... wartosciowanie_zmiennych) {
        return !dziecko.wartosc(wartosciowanie_zmiennych);
    }
}
