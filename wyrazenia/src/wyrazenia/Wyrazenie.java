package wyrazenia;

public abstract class Wyrazenie {
    Wyrazenie dziecko;

    protected int precedence() {
        return 0;
    }

    public boolean wartosc(boolean... wartosciowanie_zmiennych) {
        return dziecko.wartosc(wartosciowanie_zmiennych);
    }

    public String toString() {
        if (dziecko != null) {
            return dziecko.toString(precedence());
        }
        return "";
    }

    protected String toString(int parent_precedence) {
        if (parent_precedence < this.precedence()) {
            return "(" + toString() + ")";
        }
        return toString();
    }

    public Wyrazenie neg() {
        return new Neg(this);
    }

    public Wyrazenie and(Wyrazenie w) {
        return new And(this, w);
    }

    public Wyrazenie or(Wyrazenie w) {
        return new Or(this, w);
    }

    public Wyrazenie xor(Wyrazenie w) {
        return new Xor(this, w);
    }
}

