package lab06;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class WielomianTest {
    Wielomian ZERO = new Wielomian(0);
    Wielomian JEDEN = new Wielomian(1);
    Wielomian X = new Wielomian(0, 1);

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test_wypisywanie() {
        assertEquals("2x+1", new Wielomian(1, 2).toString());
        assertEquals("0", new Wielomian(0).toString());
        assertEquals("1", new Wielomian(1).toString());
        assertEquals("-1", new Wielomian(-1).toString());
        assertEquals("-x^3-1", new Wielomian(-1, 0, 0, -1).toString());
        assertEquals("x^10+x", new Wielomian(0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1).toString());
        assertEquals("2x^2+x+1", new Wielomian(1, 1, 2).toString());
    }

    @Test
    public void test_normalizacja() {
        assertEquals("2x+1", new Wielomian(1, 2, 0, 0, 0, 0).toString());
        assertEquals("0", new Wielomian(0, 0, 0, 0, 0).toString());
    }

    @Test
    public void test_dodawanie() {
        assertEquals("4x+2", new Wielomian(1, 2).dodaj(new Wielomian(1, 2)).toString());
        assertEquals("0", new Wielomian(1, 2).dodaj(new Wielomian(-1, -2)).toString());

        Wielomian w = new Wielomian(1, 2, 3, 4);
        assertEquals("4x^3+3x^2+2x+1", w.dodaj(ZERO).toString());
        assertEquals("4x^3+3x^2+2x+2", w.dodaj(JEDEN).toString());
        assertEquals("4x^3+3x^2+3x+1", w.dodaj(X).toString());
        assertEquals("12x^3+9x^2+6x+3", w.dodaj(w).dodaj(w).toString());
    }

    @Test
    public void test_mnozenie() {
        assertEquals("2x^2+x", new Wielomian(1, 2).pomnoz(new Wielomian(0, 1)).toString());

        Wielomian w = new Wielomian(1, 2, 3, 4);
        assertEquals("0", w.pomnoz(ZERO).toString());
        assertEquals("4x^3+3x^2+2x+1", w.pomnoz(JEDEN).toString());
        assertEquals("x^4", JEDEN.pomnoz(X).pomnoz(X).pomnoz(X).pomnoz(X).toString());

        assertEquals("-x", X.pomnoz(new Wielomian(-1)).toString());
        assertEquals("-4x^3-3x^2-2x-1", w.pomnoz(new Wielomian(-1)).toString());

        assertEquals("x^2+2x+1", (new Wielomian(1, 1, 0)).pomnoz(new Wielomian(1, 1, 0)).toString());
        assertEquals("x^2-2x+1", (new Wielomian(-1, 1, 0)).pomnoz(new Wielomian(-1, 1, 0)).toString());
        assertEquals("x^2-1", (new Wielomian(1, 1, 0)).pomnoz(new Wielomian(-1, 1, 0)).toString());
    }

    @Test
    public void test_wartosc() {
        Wielomian w1 = new Wielomian(2, 0, 1);
        Wielomian w2 = new Wielomian(1, 1, 1, 1);
        double delta = 0.00001;

        for (double x = -2.0; x < 2.0; x = x + 0.1) {
            assertEquals(1, JEDEN.wartosc(x), delta);
            assertEquals(x, X.wartosc(x), delta);
            assertEquals(x * x + 2, w1.wartosc(x), delta);
            assertEquals(((x + 1) * x + 1) * x + 1, w2.wartosc(x), delta);
        }
    }

    @Test
    public void test_pochodna() {
        assertEquals("2x+1", new Wielomian(0, 1, 1).pochodna().toString());
        assertEquals("0", ZERO.pochodna().toString());
        assertEquals("0", JEDEN.pochodna().toString());

        assertEquals("15x^4", (new Wielomian(42, 0, 0, 0, 0, 3)).pochodna().toString());
        assertEquals("4x^3+3x^2+2x+1", (new Wielomian(1, 1, 1, 1, 1)).pochodna().toString());
        assertEquals("4x^3+6x^2+6x+4", (new Wielomian(5, 4, 3, 2, 1)).pochodna().toString());

        assertEquals("-6x+3", (new Wielomian(-5, 3, -3)).pochodna().toString());
    }

}
