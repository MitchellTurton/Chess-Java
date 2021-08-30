package dev.mitchellturton.chess;

import java.util.List;

import dev.mitchellturton.chess.dataclass.ChessPosition;
import dev.mitchellturton.chess.dataclass.Move;

public class ChessGame {
    /*
    Class to manage the logic or a chess game as well as working as a hub
    for other classes to talk back to
    */

    ChessPosition currentPosition;
    List<ChessPosition> history;
    List<Move> moves;

    public ChessGame() {
        currentPosition = new ChessPosition();
    }

    public void movePiece(Move move) {
        updateHistory();

        byte[] newBoard = currentPosition.getBoard();
        newBoard[move.getFinalPos()] = currentPosition.getBoard()[move.getInitialPos()];
        newBoard[move.getInitialPos()] = 0;

        this.currentPosition = new ChessPosition(newBoard, -currentPosition.getPlayingSide());
    }

    private void updateHistory() {
        this.history.add(this.currentPosition);
    }
}
