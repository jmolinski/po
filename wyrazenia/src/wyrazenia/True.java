package wyrazenia;

public class True extends Wyrazenie {
    private static final True singleton = new True();

    public static True daj() {
        return singleton;
    }

    @Override
    public boolean wartosc(boolean... wartosciowanie_zmiennych) {
        return true;
    }

    @Override
    public String toString() {
        return "T";
    }

    @Override
    protected String toString(int parent_precedence) {
        return toString();
    }
}
