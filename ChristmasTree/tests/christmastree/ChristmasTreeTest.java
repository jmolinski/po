/**
 * Napisz program, ktĂłry drukuje choinkÄ o wysokoĹci zadanej przez argument wykonania programu
 * (jeĹli brak argumentu powinna zostaÄ wydrukowana choinka o wysokoĹci 3).
 * Choinka powinna mieÄ dodatkowy pieĹ 3x3 (ze znakĂłw .)
 *
 * PrzykĹad:
 *
 * dla
 *
 * java ChristmasTreeTest 3
 *
 * wynikiem poiwnno byÄ:
 *
 *    *
 *   ***
 *  *****
 *   ...
 *   ...
 *   ...
 */
package christmastree;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class ChristmasTreeTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() throws Exception {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(null);
    }

    @Test
    public void test_no_args() {
        assertEquals("  *\n ***\n*****\n ...\n ...\n ...\n", this.run(new String[]{}));
    }

    @Test
    public void test_arg0() {
        assertEquals("...\n...\n...\n", this.run(new String[]{"0"}));
    }

    @Test
    public void test_arg1() {
        assertEquals(" *\n...\n...\n...\n", this.run(new String[]{"1"}));
    }

    @Test
    public void test_arg2() {
        assertEquals(" *\n***\n...\n...\n...\n", this.run(new String[]{"2"}));
    }

    @Test
    public void test_arg2_and_extra() {
        assertEquals(" *\n***\n...\n...\n...\n", this.run(new String[]{"2","3"}));
    }

    private String run(String args[]) {
        outContent.reset();
        ChristmasTree.main(args);
        return outContent.toString();
    }

}