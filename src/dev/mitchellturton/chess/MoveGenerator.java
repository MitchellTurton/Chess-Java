package dev.mitchellturton.chess;

import dev.mitchellturton.chess.dataclass.Move;
import dev.mitchellturton.chess.dataclass.ChessPosition;
import java.util.*;
import java.lang.Math;

public class MoveGenerator {

    static List<Move> PawnMoves(ChessPosition position, int piecePos) {
        List<Move> legalMoves = new ArrayList<>();

        byte[] board = position.getBoard();
        int color = board[piecePos];
        int direction = board[piecePos];

        int nextPos = color + 8 * direction;

        // Forward Movement
        if (0 <= nextPos && nextPos < 64) {
            byte nextPiece = board[nextPos];

            if (nextPiece == 0) {
                legalMoves.add(new Move(piecePos, nextPos));

                nextPos = piecePos + 16 * direction;

                if ((getRow(piecePos) == 6 || getRow(piecePos) == 1) 
                    && 0 <= nextPos && nextPos < 64) {
                    
                    nextPiece = board[nextPos];

                    if (nextPiece == 0) {
                        legalMoves.add(new Move(piecePos, nextPos));
                    }
                }
                
            }
            
        }

        // Diagnal captures
        for (int i = -1; i <= 1; i++) {
            nextPos = piecePos + direction * 8 + i;

            if (0 <= nextPos && nextPos < 64) {
                byte nextPiece = board[nextPos];

                if (getColor(nextPiece) == -color && getRow(nextPos) >= 0 && getRow(nextPos) <= 7) {
                    legalMoves.add(new Move(piecePos, nextPos));
                }
            }

        }

        return legalMoves;
    }

    private static int getRow(int pos) {
        return (int) Math.floor(pos / 8);
    }

    private static int getColor(byte pieceVal) {
        int color = 0;

        if (pieceVal > 0) { color = 1; }
        else if (pieceVal < 0) { color = -1; }

        return color;
    }
}
