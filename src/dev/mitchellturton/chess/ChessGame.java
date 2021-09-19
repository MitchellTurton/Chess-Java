package dev.mitchellturton.chess;

import java.util.ArrayList;
import java.util.List;

import dev.mitchellturton.chess.ai.RandomMoves;
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

    private int selectedPiece;

    private boolean updateGraphics;

    public ChessGame() {
        currentPosition = new ChessPosition();
        this.legalMoves = MoveGenerator.generateMoves(currentPosition);
        this.history = new ArrayList<ChessPosition>();
        selectedPiece = -1;
    }

    public ChessGame(String fenstring, int playingSide) {
        this.currentPosition = new ChessPosition(fenstring, playingSide);
        currentPosition.setCastlingInfo((byte) 63);
        this.legalMoves = MoveGenerator.generateMoves(currentPosition);
        this.history = new ArrayList<ChessPosition>();
        selectedPiece = -1;
    }

    public void movePiece(Move move) {
        if (Move.listContainsMove(legalMoves, move)) {
            updateHistory();

            this.currentPosition = currentPosition.makeMove(move);

            movePlayed();
        }
    }
 
    private void movePlayed() {
        this.legalMoves = MoveGenerator.generateMoves(currentPosition);
        this.updateGraphics = true;

        if (currentPosition.getPlayingSide() == -1) {
            movePiece(RandomMoves.getMove(legalMoves));
        }
    }

    public void selectPiece(int piecePos) {
        if (currentPosition.getBoard()[piecePos] * currentPosition.getPlayingSide() > 0) {
            selectedPiece = piecePos;
        }
    }

    public void unselectPiece() {
        selectedPiece = -1;
    }

    public int getSelectedPiece() {
        return selectedPiece;
    }

    public boolean isCurrentColor(int pos) {
        if (currentPosition.getBoard()[pos] * currentPosition.getPlayingSide() > 0) 
            return true;
        else 
            return false;
    }

    private void updateHistory() {
        this.history.add(currentPosition);
    }

    public List<Move> getLegalMoves() {
        return this.legalMoves;
    }

    public byte[] getBoard() {
        return this.currentPosition.getBoard();
    }

    public boolean updateGraphics() {
        return updateGraphics;
    }

    public void setUpdateGraphics(boolean val) {
        this.updateGraphics = val;
    }
}
