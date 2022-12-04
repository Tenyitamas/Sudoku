package src;

import domain.model.Field;
import domain.model.GameState;
import domain.model.Level;
import domain.model.SudokuGame;
import org.junit.*;
import util.SudokuGenerator;

import static util.Constants.GRID_SIZE;

public class SudokuGameTest {
    SudokuGame s;
    @Before
    public void setup() {
        this.s = new SudokuGame(Level.EASY);
    }

    @Test
    public void sudokuGameConstructorWithOneParamTest() {
        Assert.assertSame(s.getLevel(), Level.EASY);
        Assert.assertEquals(s.getTimeElapsedInSec(), 0L);
        Assert.assertSame(s.getGameState(), GameState.ONGOING);
    }

    @Test
    public void setFieldValueAtLocationTest() {
        boolean foundEditable = false;
        int x = 0;
        int y = 0;
        while (!foundEditable) {
            for (int i = 0; i < GRID_SIZE; i++) {
                for (int j = 0; j < GRID_SIZE; j++) {
                    if (s.getFields()[i][j].isEditable()) {
                        s.setFieldValueAtLocation(i, j, 3);
                        x = i;
                        y = j;
                        foundEditable = true;
                    }
                }
            }
        }

        Assert.assertEquals(3, s.getFields()[x][y].getValue());
    }

    @Test
    public void endGameTest() {
        s.endGame();
        Assert.assertEquals(s.getGameState(), GameState.COMPLETE);
    }

    @Test
    public void incrementTimeElapsedByOneSecTest() {
        for (int i = 0; i < 32; i++) {
            s.incrementTimeElapsedByOneSec();
        }

        Assert.assertEquals(s.getTimeElapsedInSec(), 32L);
    }

}
