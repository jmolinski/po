package wyrazenia;

public class And extends Wyrazenie {
    Wyrazenie dziecko2;

    @Override
    protected int precedence() {
        return 3;
    }

    public And(Wyrazenie rodzic, Wyrazenie rodzic2) {
        this.dziecko = rodzic;
        this.dziecko2 = rodzic2;
    }

    @Override
    public String toString() {
        return dziecko.toString(precedence()) + "&" + dziecko2.toString(precedence());
    }

    @Override
    public boolean wartosc(boolean... wartosciowanie_zmiennych) {
        return dziecko.wartosc(wartosciowanie_zmiennych) && dziecko2.wartosc(wartosciowanie_zmiennych);
    }
}
