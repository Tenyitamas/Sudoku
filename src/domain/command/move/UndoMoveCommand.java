package domain.command.move;

import domain.command.Command;
import domain.model.Move;
import domain.model.SudokuGame;

public class UndoMoveCommand implements Command {

    SudokuGame game;

    public UndoMoveCommand(SudokuGame game) {
        this.game = game;
    }

    @Override
    public void execute() {
        if(game.getMoves().size() > 0) {
            Move move = game.getMoves().getFirst();
            game.setFieldValueAtLocation(move.getX(), move.getY(), move.getOldValue());
            game.removeLastMove();
        }
    }
}
