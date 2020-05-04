package cover;

import java.util.*;

public class Main {

    private static void executeQuery(Query query, List<ISet> sets) {
        var result = new ArrayList<>(query.execute(sets));
        if (result.size() == 1 && result.get(0) == -1) {
            // Pokrycie zbioru niemożliwe.
            System.out.println(0);
        } else {
            Collections.sort(result);
            for (int i = 0; i < result.size(); i++) {
                System.out.print(result.get(i) + 1);  // Sety są liczone od 1, nie od 0.
                if (i != result.size() - 1)
                    System.out.print(" ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        var tokens = new ArrayList<Integer>();

        while (scanner.hasNext() && scanner.hasNextInt()) {
            tokens.add(scanner.nextInt());
        }

        var parser = new InputParser(tokens);
        var sets = new ArrayList<ISet>();

        while (parser.hasNextSet() || parser.hasNextQuery()) {
            if (parser.hasNextSet()) {
                sets.add(parser.nextSet());
            } else {
                executeQuery(parser.nextQuery(), sets);
            }
        }
    }
}
