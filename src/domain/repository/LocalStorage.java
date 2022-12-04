package domain.repository;

import domain.model.SudokuGame;

import java.io.IOException;

public interface LocalStorage {
    void saveGame(SudokuGame game) throws IOException;
    SudokuGame loadGame() throws IOException;
}
