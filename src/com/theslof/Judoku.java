/***************
 *             *
 *   Judoku    *
 * Java Sudoku *
 *             *
 **************/

package com.theslof;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class Judoku {
    private static final String[] FILE_NAME = {"simple.sud", "easy.sud", "intermediate.sud", "expert.sud"};
    private static final String SUDOKU_STRING = "....482...9...28.5.5.6........87...2......679.....1..4.....7...5...9..1.7..15..6.";
    private static final ArrayList<String> examples = new ArrayList<String>();
    private static final boolean BENCHMARK = false;
    private static final long BENCHMARK_SEED = 342212;

    public static void main(String[] args) {

        System.out.println("Judoku 0.9");

        parseExampleFile(2);

        if (examples.isEmpty()) {
            System.out.println("No examples in examples list, exiting!");
            return;
        }

        System.out.println("Read " + examples.size() + " examples from file!");

        if (BENCHMARK)
            benchmark(50, 2);
        else
            runRandomSudoku();

    }

    private static void parseExampleFile(int difficulty) {
        examples.clear();
        FileReader file = null;
        BufferedReader buffer = null;
        try {
            file = new FileReader(FILE_NAME[difficulty]);
            buffer = new BufferedReader(file);

            String currentLine;

            while ((currentLine = buffer.readLine()) != null) {
                examples.add(currentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (buffer != null)
                    buffer.close();
                if (file != null)
                    file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Run through multiple sudokus and print out total and average time
    private static void benchmark(int runs, int difficulty) {
        //Benchmarks done with 50 runs, difficulty 2:
        //Before isValid optimization: 50.578s, 1.01156s per puzzle
        //After isValid optimization: 6.446s, 0.12892s per puzzle, 87% reduction

        parseExampleFile(difficulty);

        Random rand = new Random(BENCHMARK_SEED);
        int size = examples.size();
        long startTime;
        long endTime;
        Sudoku a;

        startTime = System.currentTimeMillis();

        for (int j = 0; j < runs; j++) {
            a = new Sudoku(examples.get(rand.nextInt(size)));
            if (!a.solve()) {
                System.out.println("This Sudoku can't be solved!");
            }
        }
        endTime = System.currentTimeMillis();
        System.out.println("Solved " + runs + " sudoku in " + (endTime - startTime) / 1000.0 +
                "s, for an average of " + (endTime - startTime) / (1000.0 * runs) + "s per puzzle!");
    }

    private static void runRandomSudoku() {
        Random rand = new Random();
        int size = examples.size();
        long startTime;
        long endTime;
        Sudoku a;

        int i = rand.nextInt(size);

        a = new Sudoku(examples.get(i));

        a.print();
        System.out.println(a);
        startTime = System.currentTimeMillis();
        if (!a.solve()) {
            System.out.println("This Sudoku can't be solved!");
        } else {
            endTime = System.currentTimeMillis();
            a.print();
            System.out.println(a);
            System.out.println("This sudoku took " + (endTime - startTime) / 1000.0 + "s to solve!");
        }
    }
}