import data.local.LocalStorageImpl;
import domain.command.move.MakeMoveCommand;
import domain.command.move.UndoMoveCommand;
import domain.model.*;
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
            sudokuFrame.showMessageDialog("You already made some mistakes... Come on, you can do better than this.");
        }
    }

    @Override
    public void onLevelMenuItemClicked(Level level) {
        this.nextGameLevel = level;
        sudokuFrame.setTextForNextGameLevel(level);
    }


    @Override
    public void onFieldClickWithValue(int row, int col, int value) {
        if(sudokuGame.getGameState() == GameState.COMPLETE) return;

        //sudokuGame.setFieldValueAtLocation(row, col, value);
        new MakeMoveCommand(sudokuGame, new Move(
                row, col, sudokuGame.getFieldValueAtLocation(row, col), value
        )) {{
            execute();
        }};

        var result = sudokuGame.checkForResult();
        if(result == BoardResult.NO_MISTAKE) {
            sudokuGame.setGameState(GameState.COMPLETE);
            sudokuFrame.updateViewWithGame(sudokuGame);
        } else if(result == BoardResult.HAS_MISTAKE) {
            sudokuGame.setGameState(GameState.ONGOING);
            sudokuFrame.updateViewWithGame(sudokuGame);
            sudokuFrame.showMessageDialog("You made some mistakes.");
        } else {
            sudokuFrame.updateViewWithGame(sudokuGame);
        }


    }

    @Override
    public void onUndoMenuItemClicked() {
        new UndoMoveCommand(sudokuGame){{
            execute();
        }};
        sudokuFrame.updateViewWithGame(sudokuGame);
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
