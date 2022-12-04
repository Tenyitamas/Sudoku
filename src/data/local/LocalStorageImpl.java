package data.local;

import domain.model.Level;
import domain.model.SudokuGame;
import domain.repository.LocalStorage;

import java.io.*;

public class LocalStorageImpl implements LocalStorage {

    private static final String SUDOKU_GAME_PATH = "last_game";
    private static final String LEADERBOARD_PATH = "leaderboard";

    @Override
    public void saveGame(SudokuGame game) throws IOException {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(SUDOKU_GAME_PATH);
            new ObjectOutputStream(fileOutputStream){{
                writeObject(game);
                close();
            }};
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SudokuGame loadGame() throws IOException {
        try {
            FileInputStream fileInputStream = new FileInputStream(SUDOKU_GAME_PATH);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            SudokuGame s = (SudokuGame) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            return s;

        } catch (Exception e) {
            // Return new sudoku game
            e.printStackTrace();
            return new SudokuGame(Level.MEDIUM);
        }
    }
}
