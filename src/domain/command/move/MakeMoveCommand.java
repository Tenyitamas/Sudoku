package domain.command.move;

import domain.command.Command;
import domain.model.Move;
import domain.model.SudokuGame;

public class MakeMoveCommand implements Command {
    SudokuGame game;
    Move move;

    public MakeMoveCommand(SudokuGame game, Move move) {
        this.game = game;
        this.move = move;
    }

    @Override
    public void execute() {
        if(game.setFieldValueAtLocation(move.getX(), move.getY(), move.getNewValue())) {
            game.addMove(move);
        }
    }
}
