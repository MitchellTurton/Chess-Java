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

    static private int[] castlingPositions = new int[] {0, 4, 7, 56, 60, 63};

    private boolean updateGraphics;

    public ChessGame() {
        currentPosition = new ChessPosition();
        updateLegalMoves();
        this.history = new ArrayList<ChessPosition>();
        selectedPiece = -1;
    }

    public ChessGame(String fenstring, int playingSide) {
        this.currentPosition = new ChessPosition(fenstring, playingSide);
        currentPosition.setCastlingInfo((byte) 63);
        updateLegalMoves();
        this.history = new ArrayList<ChessPosition>();
        selectedPiece = -1;
    }

    public void updateLegalMoves() {
        /*
        Loops over each piece in the current position and generates their moves
        */
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

        legalMoves.addAll(MoveGenerator.castlingMoves(currentPosition, legalMoves));
    }

    public void movePiece(Move move) {
        if (Move.listContainsMove(legalMoves, move)) {
            updateHistory();

            byte[] newBoard = currentPosition.getBoard();
            newBoard[move.getFinalPos()] = currentPosition.getBoard()[move.getInitialPos()];
            newBoard[move.getInitialPos()] = 0;

            this.currentPosition = new ChessPosition(newBoard, -currentPosition.getPlayingSide(), currentPosition.getCastlingInfo());

            boolean castleMoved = false;

            // Promoting pawns to queen after reaching the other side
            if (Math.abs(newBoard[move.getFinalPos()]) == 1 && (move.getFinalRow() == 0 || move.getFinalRow() == 7)) {
                newBoard[move.getFinalPos()] *= 5;  // Taking advantage of the fact that the piece value is equal to 1 or -1
            }

            for (int i : castlingPositions) {
                if (i == move.getInitialPos()) {
                    castleMoved = true;
                    break;
                }
            }
            
            if (castleMoved) {
                if (Math.abs(newBoard[move.getFinalPos()]) == 6 && Math.abs(move.getInitialFile() - move.getFinalFile()) > 1) {
                    final int initPos = move.getInitialPos();
                    final int finalPos = move.getFinalPos();
                    final int rookColor = -currentPosition.getPlayingSide();

                    newBoard[finalPos + (initPos - finalPos) / 2] = (byte) (4 * rookColor);
                    newBoard[finalPos + ((initPos - finalPos > 0) ? -2 : 1)] = 0;

                    currentPosition.updateCastleInfo((finalPos > 35) ? 255 : -255);
                } else {
                    currentPosition.updateCastleInfo(move.getInitialPos());
                }
            }


            movePlayed();
        }
    }
 
    private void movePlayed() {
        updateLegalMoves();
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
