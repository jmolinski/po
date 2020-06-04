package elections;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class ElectionInputDataTest {
    private String makeSimpleTestData() {
        var s = "5 1 1 5\n" + "1 (1,2)\n" + "PARTIA\n" + "1\n" + "R\n" + "10 10 10 10 10\n";

        for (int i = 1; i <= 5; i++) {
            s += "JAN KOWALSKI " + i + " PARTIA 1 0 0 0 0 0\n";
        }
        for (int i = 1; i <= 5; i++) {
            for (int j = 0; j < 10; j++) {
                s += "JAN KOWALSKI " + i + " 3 1\n";
            }
        }

        s += "0 0 0 0 0\n";

        return s;
    }

    @Test
    public void testSimpleCase() {
        var mockIn = new ByteArrayInputStream(makeSimpleTestData().getBytes());
        var mockDataScanner = new Scanner(mockIn);
        ElectionInputData electionData = null;
        try {
            electionData = new ElectionInputData(mockDataScanner);
        } catch (InvalidInputData e) {
            fail("InvalidInputData exception raised");
        }

        assertEquals(5, electionData.constituenciesCount());
        assertEquals(1, electionData.partiesCount());
        assertEquals(1, electionData.marketingActionsCount());
        assertEquals(5, electionData.candidateAttributesCount());
    }

    @Test(expected = InvalidInputData.class)
    public void testFailsOnInvalidData() throws InvalidInputData {
        var mockIn = new ByteArrayInputStream("My string".getBytes());
        var mockDataScanner = new Scanner(mockIn);
        var electionData = new ElectionInputData(mockDataScanner);
    }
}

