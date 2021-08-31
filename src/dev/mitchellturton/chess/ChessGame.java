package dev.mitchellturton.chess;

import java.util.ArrayList;
import java.util.List;

import dev.mitchellturton.chess.dataclass.ChessPosition;
import dev.mitchellturton.chess.dataclass.Move;

public class ChessGame {
    /*
    Class to manage the logic or a chess game as well as working as a hub
    for other classes to talk back to
    */

    private ChessPosition currentPosition;
    private List<ChessPosition> history;
    private List<Move> legalMoves;

    public ChessGame() {
        currentPosition = new ChessPosition();
        updateLegalMoves();
    }

    public ChessGame(String fenstring, int playingSide) {
        this.currentPosition = new ChessPosition(fenstring, playingSide);
        updateLegalMoves();
    }

    public void updateLegalMoves() {
        legalMoves = new ArrayList<Move>();
        byte[] board = currentPosition.getBoard();

        for (int i = 0; i < 64; i++) {
            if (board[i] * currentPosition.getPlayingSide() > 0) {
                List<Move> pieceMoves;

                switch (Math.abs(board[i])) {
                    case 1:  pieceMoves = MoveGenerator.pawnMoves(currentPosition, i);
                             break;
                    case 2:  pieceMoves = MoveGenerator.knightMoves(currentPosition, i);
                             break;
                    case 3:  pieceMoves = MoveGenerator.diagMoves(currentPosition, i);
                             break;
                    case 4:  pieceMoves = MoveGenerator.horzMoves(currentPosition, i);
                             break;
                    case 5:  pieceMoves = MoveGenerator.diagMoves(currentPosition, i);
                             pieceMoves.addAll(MoveGenerator.horzMoves(currentPosition, i));
                             break;
                    case 6:  pieceMoves = MoveGenerator.kingMoves(currentPosition, i);
                             break;
                    default: pieceMoves = new ArrayList<>();
                             break;
                }

                legalMoves.addAll(pieceMoves);
            }
        }
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


    public List<Move> getLegalMoves() {
        return this.legalMoves;
    }

    public byte[] getBoard() {
        return this.currentPosition.getBoard();
    }
}
