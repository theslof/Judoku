/**************
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
    private static final String[] FILE_NAME = {"simple.sud","easy.sud","intermediate.sud","expert.sud"};
    private static final String SUDOKU_STRING = "....482...9...28.5.5.6........87...2......679.....1..4.....7...5...9..1.7..15..6.";
    private static final ArrayList<String> examples = new ArrayList<String>();

    public static void main(String[] args) {

        System.out.println("Judoku 0.1");

        FileReader file = null;
        BufferedReader buffer = null;
        try {
            file = new FileReader(FILE_NAME[2]);
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

        if (examples.isEmpty()) {
            System.out.println("No examples in examples list, exiting!");
            return;
        }

        System.out.println("Read " + examples.size() + " examples from file!");

        Random rand = new Random();
        int i = rand.nextInt(examples.size());

        Sudoku a = new Sudoku(new Sudoku(examples.get(i)));

        a.print();
        System.out.println(a);

        long startTime = System.currentTimeMillis();
        long endTime;

        if (!a.solve()) {
            System.out.println("This Sudoku can't be solved!");
        } else {
            endTime = System.currentTimeMillis();
            a.print();
            System.out.println(a);
            System.out.println("This sudoku took " + (endTime - startTime) / 1000.0 + "s to solve!");
        }

        //Import sample.sud, parse and solve
    }

}