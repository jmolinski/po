package cover;

import java.util.*;

public class Main {

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
//                var set = (SetUnion)sets.get(10);
//                var subsets = set.subsets;
//                boolean[] test = new boolean[2];
//                for (int i = 0; i < subsets.size(); i++ ) {
//                    System.out.print(i);
//                    System.out.print(' ');
//                    System.out.println(subsets.get(i).countCoveredElements(test));
//                }
//                return;



                var result = new ArrayList<>(parser.nextQuery().execute(sets));
                Collections.sort(result);
                for (int i = 0; i < result.size(); i++) {
                    System.out.print(result.get(i));
                    if (i != result.size() - 1)
                        System.out.print(" ");
                }
                System.out.println();
            }
        }
    }
}
