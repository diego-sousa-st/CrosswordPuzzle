package dcc.diegosousa;

import java.util.*;

public class Solution {

    static String[] crosswordPuzzle(String[] crossword, String hints) {

        CrosswordSolution crosswordSolution = new CrosswordSolution(crossword, hints);

        return crosswordSolution.solve();

    }

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        String[] crossword = new String[10];

        for (int crossword_i = 0; crossword_i < 10; crossword_i++) {

            crossword[crossword_i] = in.next();

        }

        String hints = in.next();
        String[] result = crosswordPuzzle(crossword, hints);

        for (int i = 0; i < result.length; i++) {

            System.out.print(result[i] + (i != result.length - 1 ? "\n" : ""));

        }

        System.out.println("");

        in.close();

    }

}
