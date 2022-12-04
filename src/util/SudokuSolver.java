package util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SudokuSolver {
    private static SudokuSolver instance = null;
    private static int[][] grid;

    public static SudokuSolver getInstance() {
        if(instance == null) {
            instance = new SudokuSolver();
        }

        return instance;
    }
    static public boolean solvable(int [][] grid) {
        SudokuSolver.grid = grid;

        return true;
    }
}


