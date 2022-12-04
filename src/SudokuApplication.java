import data.local.LocalStorageImpl;
import domain.model.BoardResult;
import domain.model.Level;
import domain.model.SudokuGame;
import domain.repository.LocalStorage;
import presentation.SudokuFrame;

import java.io.IOException;

public class SudokuApplication implements SudokuFrame.SudokuFrameListener {

    private Level nextGameLevel = Level.MEDIUM;
    private SudokuFrame sudokuFrame;
    private SudokuGame sudokuGame;
    private final LocalStorage storage = new LocalStorageImpl();


    public void start() {
        sudokuFrame = new SudokuFrame(this);
        sudokuFrame.setTextForNextGameLevel(nextGameLevel);
        sudokuFrame.setVisible(true);
        initSudokuGame();
    }

    private void initSudokuGame() {
        try {
            sudokuGame = storage.loadGame();
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
            sudokuGame = new SudokuGame(nextGameLevel);
        }
        sudokuFrame.updateViewWithGame(sudokuGame);


    }

    @Override
    public void onNewGameMenuItemClicked() {
        sudokuGame = new SudokuGame(nextGameLevel);
        sudokuFrame.updateViewWithGame(sudokuGame);
    }

    @Override
    public void onCheckForMistakesMenuItemClicked() {
        if(sudokuGame.checkForMistakes() == BoardResult.NO_MISTAKE) {
            sudokuFrame.showMessageDialog("No mistakes so far");
        } else {
            sudokuFrame.showMessageDialog("Early dumbass");
        }
    }

    @Override
    public void onLevelMenuItemClicked(Level level) {
        this.nextGameLevel = level;
        sudokuFrame.setTextForNextGameLevel(level);
    }


    @Override
    public void onFieldClickWithValue(int row, int col, int value) {
        sudokuGame.setFieldValueAtLocation(row, col, value);
        sudokuFrame.updateViewWithGame(sudokuGame);
        var result = sudokuGame.checkForResult();
        if(result == BoardResult.NO_MISTAKE) {
            sudokuFrame.showMessageDialog("Congratulations, You won!");
        } else if(result == BoardResult.HAS_MISTAKE) {
            sudokuFrame.showMessageDialog("Dumbass...");
        }
    }

    @Override
    public void onSecondPassed() {
        sudokuGame.incrementTimeElapsedByOneSec();
        sudokuFrame.setTextForTimeLabel(sudokuGame.getTimeElapsedInSec());
    }

    @Override
    public void onWindowClosing() {
        try {
            storage.saveGame(sudokuGame);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
