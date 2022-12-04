package domain.model;

import util.SudokuGenerator;

import java.io.Serializable;

public class SudokuGame implements Serializable {

    private GameState gameState;
    private Field[][] fields;
    private long timeElapsed = 0;
    private Level level;

    public SudokuGame(GameState gameState, Field[][] fields, long timeElapsed, Level level) {
        this.gameState = gameState;
        this.fields = fields;
        this.timeElapsed = timeElapsed;
        this.level = level;
    }

    public SudokuGame(Level level) {
        gameState = GameState.ONGOING;
        timeElapsed = 0;
        SudokuGenerator s = new SudokuGenerator.Builder()
                .setLevel(level)
                .build();
        this.fields = s.getFields();
    }

    public Field[][] getFields() {
        return fields;
    }
}