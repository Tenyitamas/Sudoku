package util;

import domain.model.Field;
import domain.model.Level;

import java.util.Random;

import static util.Constants.*;

public class SudokuGenerator {



    private Field[][] fields = new Field[GRID_SIZE][GRID_SIZE];

    int[][] grid = new int[GRID_SIZE][GRID_SIZE];

    private Level level;

    private SudokuGenerator(Level level) {
        this.level = (level == null) ? Level.MEDIUM : level;
        fillFields();
    }

    public Field[][] getFields() {
        return fields;
    }

    private int generateRandomInt(int min, int max) {
        return new Random().nextInt(min, max + 1);
    }

    private int randomGenerator(int num) {
        return (int) Math.floor((Math.random() * num + 1));
    }

    public void print() {
        System.out.println("HAHAHAH");
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void fillFields() {
        fillDiagonal();
        fillRemaining(0, GRID_SIZE_SQUARE_ROOT);
        removeDigits();

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                fields[i][j].setValue(grid[i][j]);
                fields[i][j].setEditable(grid[i][j] == 0);
            }
        }
    }

    void fillDiagonal() {
        for (int i = 0; i < GRID_SIZE; i = i + GRID_SIZE_SQUARE_ROOT)
            fillBox(i, i);
    }

    boolean unUsedInBox(int rowStart, int colStart, int num) {
        for (int i = 0; i < GRID_SIZE_SQUARE_ROOT; i++)
            for (int j = 0; j < GRID_SIZE_SQUARE_ROOT; j++)
                if (grid[rowStart + i][colStart + j] == num)
                    return false;

        return true;
    }

    void fillBox(int row, int col) {
        int num;
        for (int i = 0; i < GRID_SIZE_SQUARE_ROOT; i++) {
            for (int j = 0; j < GRID_SIZE_SQUARE_ROOT; j++) {
                do {
                    num = randomGenerator(MAX_DIGIT_VALUE);
                }
                while (!unUsedInBox(row, col, num));

                grid[row + i][col + j] = num;
            }
        }
    }

    boolean checkIfSafe(int i, int j, int num) {
        return (unUsedInRow(i, num) &&
                unUsedInCol(j, num) &&
                unUsedInBox(i - i % GRID_SIZE_SQUARE_ROOT, j - j % GRID_SIZE_SQUARE_ROOT, num));
    }

    boolean unUsedInRow(int i, int num) {
        for (int j = 0; j < GRID_SIZE; j++)
            if (grid[i][j] == num)
                return false;
        return true;
    }

    // check in the row for existence
    boolean unUsedInCol(int j, int num) {
        for (int i = 0; i < GRID_SIZE; i++)
            if (grid[i][j] == num)
                return false;
        return true;
    }

    boolean fillRemaining(int i, int j) {
        //  System.out.println(i+" "+j);
        if (j >= GRID_SIZE && i < GRID_SIZE - 1) {
            i = i + 1;
            j = 0;
        }
        if (i >= GRID_SIZE && j >= GRID_SIZE)
            return true;

        if (i < GRID_SIZE_SQUARE_ROOT) {
            if (j < GRID_SIZE_SQUARE_ROOT)
                j = GRID_SIZE_SQUARE_ROOT;
        } else if (i < GRID_SIZE - GRID_SIZE_SQUARE_ROOT) {
            if (j == (int) (i / GRID_SIZE_SQUARE_ROOT) * GRID_SIZE_SQUARE_ROOT)
                j = j + GRID_SIZE_SQUARE_ROOT;
        } else {
            if (j == GRID_SIZE - GRID_SIZE_SQUARE_ROOT) {
                i = i + 1;
                j = 0;
                if (i >= GRID_SIZE)
                    return true;
            }
        }

        for (int num = 1; num <= GRID_SIZE; num++) {
            if (checkIfSafe(i, j, num)) {
                grid[i][j] = num;
                if (fillRemaining(i, j + 1))
                    return true;

                grid[i][j] = 0;
            }
        }
        return false;
    }

    public void removeDigits() {
        int count = GRID_SIZE * GRID_SIZE - level.numberOfProvidedDigits;
        while (count > 0) {
            int cellId = randomGenerator(GRID_SIZE * GRID_SIZE) - 1;

            int i = (cellId / GRID_SIZE);
            int j = cellId % GRID_SIZE;


            if (grid[i][j] != 0) {
                count--;
                grid[i][j] = 0;
            }
        }
    }


   /* private void fillFields() {
        fillDiagonalBoxes();
        fillRemaining(0, GRID_SIZE_SQUARE_ROOT);
        removeDigits();
    }

    private void removeDigits() {
        int digitsToRemove = GRID_SIZE * GRID_SIZE - level.numberOfProvidedDigits;

        while (digitsToRemove > 0) {
            int randomRow = generateRandomInt(MIN_DIGIT_INDEX, MAX_DIGIT_INDEX);
            int randomColumn = generateRandomInt(MIN_DIGIT_INDEX, MAX_DIGIT_INDEX);

            if(fields.get(randomRow).get(randomColumn).getValue() != 0) {
                int digitToRemove = fields.get(randomRow).get(randomColumn).getValue();
                fields.get(randomRow).get(randomColumn).setValue(0);
                if(!SudokuSolver.solvable(fields)) {
                    fields.get(randomRow).get(randomColumn).setValue(digitToRemove);
                } else {
                    digitsToRemove --;
                }
            }
        }
        System.out.println("Been there done that");
    }

    private boolean fillRemaining(int rowStart, int colStart) {
        int i = rowStart;
        int j = colStart;

        if (j >= GRID_SIZE && i < GRID_SIZE - 1) {
            i++;
            j = 0;
        }
        if (i >= GRID_SIZE && j >= GRID_SIZE) {
            return true;
        }

        if (i < GRID_SIZE_SQUARE_ROOT) {
            if (j < GRID_SIZE_SQUARE_ROOT) {
                j = GRID_SIZE_SQUARE_ROOT;
            }
        } else if (i < GRID_SIZE - GRID_SIZE_SQUARE_ROOT) {
            if (j == (i / GRID_SIZE_SQUARE_ROOT) * GRID_SIZE_SQUARE_ROOT) {
                j += GRID_SIZE_SQUARE_ROOT;
            }
        } else {
            if (j == GRID_SIZE - GRID_SIZE_SQUARE_ROOT) {
                i++;
                j = 0;
                if (i >= GRID_SIZE) return true;
            }
        }

        for (int digit = MIN_DIGIT_VALUE; digit <= MAX_DIGIT_VALUE; digit++) {
            if(isSafeToPutIn(i, j, digit)) {
                fields.get(i).get(j).setValue(digit);
                if(fillRemaining(i, j+1)) {
                    return true;
                }
                fields.get(i).get(j).setValue(0);
            }
        }
        return false;
    }

    private boolean isSafeToPutIn(int row, int column, int digit) {
        return isUnusedInBox(findBoxStart(row), findBoxStart(column), digit)
                && isUnusedInRow(row, digit)
                && isUnusedInColumn(column, digit);
    }

    private boolean isUnusedInColumn(int column, int digit) {
        for (int i = 0; i < GRID_SIZE; i++) {
            if(fields.get(i).get(column).getValue() == digit) {
                return false;
            }
        }
        return true;
    }

    private boolean isUnusedInRow(int row, int digit) {
        for (int i = 0; i < GRID_SIZE; i++) {
            if(fields.get(row).get(i).getValue() == digit) {
                return false;
            }
        }
        return true;
    }

    private int findBoxStart(int index) {
        return index - index % GRID_SIZE_SQUARE_ROOT;
    }

    private void fillDiagonalBoxes() {
        for (int i = 0; i < GRID_SIZE; i += GRID_SIZE_SQUARE_ROOT) {
            fillBox(i, i);
        }
    }

    private void fillBox(int row, int column) {
        int generatedDigit;

        for (int i = 0; i < GRID_SIZE_SQUARE_ROOT; i++) {
            for (int j = 0; j < GRID_SIZE_SQUARE_ROOT; j++) {
                do {
                    generatedDigit = generateRandomInt(MIN_DIGIT_VALUE, MAX_DIGIT_INDEX);
                } while (!isUnusedInBox(row, column, generatedDigit));

                fields.get(row + i).get(column + j).setValue(generatedDigit);
            }

        }
    }

    private boolean isUnusedInBox(int rowStart, int columnStart, int digit) {
        for (int i = 0; i < GRID_SIZE_SQUARE_ROOT; i++) {
            for (int j = 0; j < GRID_SIZE_SQUARE_ROOT; j++) {
                if (fields.get(rowStart + i).get(columnStart + j).getValue() == digit) {
                    return false;
                }
            }
        }
        return true;
    }*/


    static public class Builder {
        Level level;

        public Builder setLevel(Level level) {
            this.level = level;
            return this;
        }

        public SudokuGenerator build() {
            return new SudokuGenerator(this.level);
        }
    }
}