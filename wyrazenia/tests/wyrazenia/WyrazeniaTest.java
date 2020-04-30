package wyrazenia;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Polecenie:
 * zaimplementuj projekt dotyczÄcy WyraĹźeĹ logicznych zawierajÄcy nastÄpujace klasy:
 * <p>
 * Wyrazenie
 * public boolean wartosc(boolean... wartosciowanie_zmiennych
 * wyliczba wartoĹÄ wyraĹźanie, przy zaĹoĹźeniu, Ĺźe:
 * x_0 := wartosciowanie_zmiennych[0]
 * x_1 := wartosciowanie_zmiennych[1]
 * ...
 * public String toString()
 * public Wyrazenie neg()
 * public Wyrazenie and(Wyrazenie arg)
 * public Wyrazenie or(Wyrazenie arg)
 * public Wyrazenie xor(Wyrazenie arg)
 * True
 * public static True daj()
 * False
 * public static False daj()
 * Zmienna
 * public static Zmienna daj(int i) -> generuje zmiennÄ x_i
 */


public class WyrazeniaTest {

    private Wyrazenie x0 = Zmienna.daj(0);
    private Wyrazenie x1 = Zmienna.daj(1);
    private Wyrazenie x2 = Zmienna.daj(2);
    private Wyrazenie t = True.daj();
    private Wyrazenie f = False.daj();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test_zmienna() {
        assertTrue(x0.wartosc(true));
        assertFalse(x0.wartosc(false));
        assertEquals("x0", x0.toString());
        assertEquals("x1", x1.toString());
        assertEquals("x2", x2.toString());
        assertEquals("x42", (Zmienna.daj(42)).toString());
        assertEquals("x5|x5", Zmienna.daj(5).or(Zmienna.daj(5)).toString());
    }

    @Test
    public void test_stale() {
        assertTrue(t.wartosc());
        assertTrue(t.wartosc(false, true));
        assertEquals("T", t.toString());

        assertFalse(f.wartosc());
        assertFalse(f.wartosc(true, false));
        assertEquals("F", f.toString());

        assertEquals("~T", True.daj().neg().toString());
        assertEquals("~F", False.daj().neg().toString());
    }

    @Test
    public void test_proste_wyrazenia() {
        Wyrazenie w1 = x0.or(x1);
        assertEquals("x0|x1", w1.toString());
        assertTrue(w1.wartosc(true, false));
        assertTrue(w1.wartosc(false, true));
        assertFalse(w1.wartosc(false, false));

        Wyrazenie w2 = x0.xor(x1);
        assertEquals("x0^x1", w2.toString());

        Wyrazenie tautologia = x0.or(x0.neg());
        Wyrazenie sprzecznosc = x0.and(x0.neg());
        assertEquals("x0|~x0", tautologia.toString());
        assertEquals("x0&~x0", sprzecznosc.toString());
    }

    @Test
    public void test_priorytety() {
        Wyrazenie w3 = True.daj().or(False.daj());
        assertEquals("T|F", w3.toString());
        assertEquals("~(T|F)", w3.neg().toString());

        Wyrazenie w1 = x0.or(x1);
        Wyrazenie w2 = w1.and(x2);
        assertEquals("(x0|x1)&x2", w2.toString());
        assertEquals("~((x0|x1)&x2)", w2.neg().toString());
        assertEquals("(x0|x1)&x2^T", w2.xor(True.daj()).toString());
        assertEquals("T|T|T", True.daj().or(True.daj().or(True.daj())).toString());
        assertEquals("T&T&T", True.daj().and(True.daj().and(True.daj())).toString());
        assertEquals("T^T^T", True.daj().xor(True.daj().xor(True.daj())).toString());
        assertEquals("x1^~x2", Zmienna.daj(1).xor(Zmienna.daj(2).neg()).toString());
        assertEquals("~(x1^x2)", Zmienna.daj(1).xor(Zmienna.daj(2)).neg().toString());
    }

    @Test
    public void test_wartosc() {
        Wyrazenie W = Zmienna.daj(0).or(Zmienna.daj(1)).or(Zmienna.daj(2));
        W = W.or(W);
        System.out.print(W.toString());

        Wyrazenie w3 = True.daj().or(False.daj());
        assertTrue(w3.wartosc());
        assertFalse(w3.neg().wartosc());

        Wyrazenie w1 = x0.or(x1);
        Wyrazenie w2 = w1.and(x2);
        assertFalse(w2.wartosc(false, true, false));
        assertFalse(w2.wartosc(false, false, true));
        assertTrue(w2.wartosc(false, true, true));
        assertFalse(w2.neg().wartosc(false, true, true));
        assertTrue(w2.xor(True.daj()).wartosc(false, false, true));
        assertFalse(w2.xor(True.daj()).wartosc(false, true, true));
    }
}
