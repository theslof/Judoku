package com.theslof;

import java.util.*;

public class Sudoku {
    private static final int GRID_SIZE = 3;
    private static final int ROW_COL_BLK_SIZE = GRID_SIZE * GRID_SIZE;
    private static final int BOARD_SIZE = ROW_COL_BLK_SIZE * ROW_COL_BLK_SIZE;
    private int[] grid = new int[BOARD_SIZE];
    private int[] solution = new int[BOARD_SIZE];
    private Random rand = new Random();

    public Sudoku() {
        //Create a new, blank Sudoku
    }

    public Sudoku(String puzzleString) {
        //Create a Sudoku from a string
        parseString(puzzleString);
    }

    public void revert(){
        solution = grid.clone();
    }

    private int[] getRow(int i) {
        int[] row = new int[ROW_COL_BLK_SIZE];
        System.arraycopy(solution, i * ROW_COL_BLK_SIZE, row, 0, ROW_COL_BLK_SIZE);
        return row;
    }

    private int[] getCol(int i) {
        int[] s = new int[ROW_COL_BLK_SIZE];
        for (int n = 0; n < ROW_COL_BLK_SIZE; n++) {
            s[n] = solution[n * ROW_COL_BLK_SIZE + i];
        }
        return s;
    }

    private int[] getBlock(int y, int x) {
        y *= GRID_SIZE;
        x *= GRID_SIZE;
        int[] s = new int[ROW_COL_BLK_SIZE];
        for (int j = 0; j < GRID_SIZE; j++) {
            System.arraycopy(solution, (y + j) * ROW_COL_BLK_SIZE + x, s, j * GRID_SIZE, GRID_SIZE);
        }
        return s;
    }

    public void print() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            int n = solution[i];
            if ((i % ROW_COL_BLK_SIZE) == 0) {
                if (i % (ROW_COL_BLK_SIZE * GRID_SIZE) == 0) {
                    System.out.print("\n+");
                    for (int j = 1; j < (ROW_COL_BLK_SIZE + GRID_SIZE) * 2; j++) {
                        if ((j) % (GRID_SIZE * 2 + 2) == 0)
                            System.out.print('+');
                        else
                            System.out.print('-');
                    }
                    System.out.print("+");
                }
                System.out.print("\n");
            }
            if (i % GRID_SIZE == 0)
                System.out.print("| ");
            if (n == 0)
                System.out.print("  ");
            else
                System.out.print(n + " ");
            if ((i % ROW_COL_BLK_SIZE) == ROW_COL_BLK_SIZE - 1)
                System.out.print("|");
        }
        System.out.print("\n+");
        for (int j = 1; j < (ROW_COL_BLK_SIZE + GRID_SIZE) * 2; j++) {
            if ((j) % (GRID_SIZE * 2 + 2) == 0)
                System.out.print('+');
            else
                System.out.print('-');
        }
        System.out.print("+");
        System.out.print('\n');
    }

    private boolean isValid(int cell) {
        int row = cell / ROW_COL_BLK_SIZE;
        int col = cell % ROW_COL_BLK_SIZE;
        return checkRow(getRow(row)) && checkRow(getCol(col)) && checkRow(getBlock(row / GRID_SIZE, col / GRID_SIZE));
    }

    private boolean isValid() {
        for (int i = 0; i < ROW_COL_BLK_SIZE; i++) {
            if (!checkRow(getRow(i))) {
                return false;
            }
            if (!checkRow(getCol(i))) {
                return false;
            }
            if (!checkRow(getBlock(i / GRID_SIZE, i % GRID_SIZE))) {
                return false;
            }
        }

        return true;
    }

    private boolean checkRow(int[] row) {
        Set<Integer> nums = new HashSet<>();

        for (int j = 0; j < ROW_COL_BLK_SIZE; j++) {
            if (row[j] != 0) {
                if (nums.contains(row[j])) {
                    return false;
                } else {
                    nums.add(row[j]);
                }
            }
        }

        return true;
    }

    private boolean isFilled() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (solution[i] == 0)
                return false;
        }
        return true;
    }

    boolean solve() {
        return solve(0);
    }

    private boolean solve(int n) {
        if (n >= BOARD_SIZE)
            return true;

        if (solution[n] != 0)
            return solve(n + 1);

        for (int i = 1; i <= ROW_COL_BLK_SIZE; i++) {
            solution[n] = i;
            if (!isValid(n)) {
                solution[n] = 0;
                continue;
            }
            if (solve(n + 1)) {
                return true;
            } else {
                solution[n] = 0;
            }
        }
        return false;
    }

    private void parseString(String s) {
        s = s.replace('.', '0');
        char[] c = s.toCharArray();
        grid = new int[BOARD_SIZE];
        solution = new int[BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE && i < c.length; i++) {
            grid[i] = (int) c[i] - '0';
            solution[i] = (int) c[i] - '0';
        }
    }

    @Override
    public String toString() {
        StringBuilder sudoku = new StringBuilder();
        int n;
        char s;

        for (int i = 0; i < BOARD_SIZE; i++) {
            n = solution[i];
            switch (n) {
                case 0:
                    s = '.';
                    break;
                default:
                    s = (char) (n + '0');
                    break;
            }
            sudoku.append(s);
        }

        return sudoku.toString();
    }
}