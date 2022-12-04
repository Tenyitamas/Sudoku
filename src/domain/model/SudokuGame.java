package domain.model;

import util.SudokuGenerator;
import util.SudokuUtils;

import java.io.Serializable;
import java.util.*;

import static util.Constants.*;
import static util.SudokuUtils.collectionHasRepeats;

public class SudokuGame implements Serializable {

    private GameState gameState;
    private Field[][] fields;
    private long timeElapsedInSec = 0;
    private Level level;

    public SudokuGame(GameState gameState, Field[][] fields, long timeElapsedInSec, Level level) {
        this.gameState = gameState;
        this.fields = fields;
        this.timeElapsedInSec = timeElapsedInSec;
        this.level = level;
    }

    public SudokuGame(Level level) {
        this.level = level;
        gameState = GameState.ONGOING;
        timeElapsedInSec = 0;
        SudokuGenerator s = new SudokuGenerator.Builder()
                .setLevel(level)
                .build();
        this.fields = s.getFields();

    }


    public void setFieldValueAtLocation(int row, int col, int value) {
        if (SudokuUtils.isValidIndex(row)
                && SudokuUtils.isValidIndex(col)
                && SudokuUtils.isValidValue(value)
        ) {
            if (fields[row][col].isEditable()) {
                fields[row][col].setValue(value);
            }
        }
    }

    public Field[][] getFields() {
        return fields;
    }

    private void startGame() {
        gameState = GameState.ONGOING;
    }

    public void endGame() {
        this.gameState = GameState.COMPLETE;
    }

    public void setTimeElapsedInSec(long timeElapsedInSec) {
        this.timeElapsedInSec = timeElapsedInSec;
    }

    public void incrementTimeElapsedByOneSec() {
        this.timeElapsedInSec++;
    }

    public Level getLevel() {
        return level;
    }

    public long getTimeElapsedInSec() {
        return timeElapsedInSec;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }


    public BoardResult checkForResult() {
        if (hasEmptySquare()) return BoardResult.NOT_FILLED;
        if (solvable()) return BoardResult.NO_MISTAKE;
        return BoardResult.HAS_MISTAKE;
    }

    public BoardResult checkForMistakes() {
        if(solvable()) return BoardResult.NO_MISTAKE;
        return BoardResult.HAS_MISTAKE;
    }

    private boolean solvable() {
        int[][] grid = new int[fields.length][fields.length];
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields.length; j++) {
                grid[i][j] = fields[i][j].getValue();
            }
        }
        return !(columnsAreInvalid(grid) || rowsAreInvalid(grid) || boxesAreInvalid(grid));
    }

    private boolean columnsAreInvalid(int[][] grid) {
        for (int x = MIN_DIGIT_INDEX; x <= MAX_DIGIT_INDEX; x++) {
            List<Integer> row = new ArrayList<>();
            for (int y = MIN_DIGIT_INDEX; y <= MAX_DIGIT_INDEX; y++) {
                if(grid[x][y] != 0) {
                    row.add(grid[x][y]);
                }
            }

            if (collectionHasRepeats(row)) return true;
        }

        return false;
    }

    private boolean rowsAreInvalid(int[][] grid) {
        for (int y = MIN_DIGIT_INDEX; y <= MAX_DIGIT_INDEX; y++) {
            List<Integer> row = new ArrayList<>();
            for (int x = MIN_DIGIT_INDEX; x <= MAX_DIGIT_INDEX; x++) {
                if(grid[x][y] != 0) {
                    row.add(grid[x][y]);
                }
            }

            if (collectionHasRepeats(row)) return true;
        }

        return false;
    }

    private boolean boxesAreInvalid(int[][] grid) {
        for(int i = MIN_DIGIT_INDEX; i <= MAX_DIGIT_INDEX; i += GRID_SIZE_SQUARE_ROOT) {
            for(int j = MIN_DIGIT_INDEX; j <= MAX_DIGIT_INDEX; j += GRID_SIZE_SQUARE_ROOT) {
                List<Integer> box = new ArrayList<>();
                for (int k = 0; k < GRID_SIZE_SQUARE_ROOT; k++) {
                    for (int l = 0; l < GRID_SIZE_SQUARE_ROOT; l++) {
                        if(grid[i + k][j + l] != 0) {
                            box.add(grid[i + k][j + l]);
                        }
                    }
                }
                if(collectionHasRepeats(box)) return true;
            }
        }

        return false;
    }


    private boolean hasEmptySquare() {
        for (Field[] fieldArray : fields) {
            for (Field field : fieldArray) {
                if (field.getValue() == 0)
                    return true;
            }
        }
        return false;
    }

}