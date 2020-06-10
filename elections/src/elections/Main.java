package elections;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws InvalidInputData {
        assert args.length == 1 : "too few or too many command line arguments";

        var inputFile = new File(args[0]);

        ElectionInputData inputData;
        try {
            inputData = new ElectionInputData(new Scanner(inputFile));
        } catch (FileNotFoundException e) {
            System.err.println("File given as argument does not exist.");
            return;
        }

        Elections electionResults = new Elections(inputData);
        electionResults.dhondtMethodResults().print();
        electionResults.sainteLagueMethodResults().print();
        electionResults.hateNiemeyerResults().print();
    }
}

