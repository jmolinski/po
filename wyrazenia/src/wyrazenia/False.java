package wyrazenia;

public class False extends Wyrazenie {
    static False singleton = new False();

    public static False daj() {
        return singleton;
    }

    @Override
    public boolean wartosc(boolean... wartosciowanie_zmiennych) {
        return false;
    }

    @Override
    public String toString() {
        return "F";
    }

    @Override
    protected String toString(int parent_precedence) {
        return toString();
    }
}
