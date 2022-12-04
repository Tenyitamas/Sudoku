package data.local;

import domain.model.SudokuGame;
import domain.repository.LocalStorage;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LocalStorageImpl implements LocalStorage {

    private static final Path ROOT_PATH = Paths.get(".").normalize().toAbsolutePath();
    private static final String RELATIVE_PATH = "last_game";
    @Override
    public void saveGame(SudokuGame game) throws IOException {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(ROOT_PATH.toString() + "/" + RELATIVE_PATH);
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
            FileInputStream fileInputStream = new FileInputStream(ROOT_PATH + "/" + RELATIVE_PATH);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            SudokuGame s = (SudokuGame) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            return s;

        } catch (Exception e) {
            // Return new sudoku game
            e.printStackTrace();
            return null;
        }
    }
}
