package dev.mitchellturton.chess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.mitchellturton.chess.dataclass.ChessPosition;
import dev.mitchellturton.chess.dataclass.Move;

public class MoveGenerator {
    /*
    Generates all the moves for a giben piece in a certain position
    */

    public static Map<Character, Move> castlingRookMoves = new HashMap<Character, Move>()
    {
        {
            put('l', new Move(0, 3));
            put('r', new Move(7, 5));
            put('L', new Move(56, 59));
            put('R', new Move(63, 61));
        }
    };

    static List<Move> pawnMoves(ChessPosition position, int piecePos) {
        List<Move> legalMoves = new ArrayList<>();

        byte[] board = position.getBoard();
        int color = board[piecePos];

        int nextPos = piecePos + 8 * -color;

        // Forward Movement
        if (0 <= nextPos && nextPos < 64) {
            byte nextPiece = board[nextPos];

            if (nextPiece == 0) {
                legalMoves.add(new Move(piecePos, nextPos));

                nextPos = piecePos + 16 * -color;

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
        for (int i = -1; i <= 1; i+=2) {
            nextPos = piecePos + -color * 8 + i;

            if (0 <= nextPos && nextPos < 64) {
                byte nextPiece = board[nextPos];

                if (getColor(nextPiece) == -color && getRow(nextPos) >= 0 && getRow(nextPos) <= 7) {
                    legalMoves.add(new Move(piecePos, nextPos));
                }
            }

        }

        return legalMoves;
    }

    public static List<Move> knightMoves(ChessPosition position, int piecePos) {
        List<Move> legalMoves = new ArrayList<Move>();

        byte[] board = position.getBoard();

        int[] offsets = new int[] {-6, -15, -17, -10, 6, 15, 17, 10};

        for (int offset : offsets) {
            int nextPos = piecePos + offset;

            if(checkInBounds(getRow(nextPos), getFile(nextPos)) 
                && Math.abs(getRow(piecePos) - getRow(nextPos)) < 3
                    && Math.abs(getFile(piecePos) - getFile(nextPos)) < 3) {
                
                if (getColor(board[nextPos]) != getColor(board[piecePos])) {
                    legalMoves.add(new Move(piecePos, nextPos));
                }
            }
        }

        return legalMoves;
    }

    public static List<Move> diagMoves(ChessPosition position, int piecePos) {
        List<Move> legalMoves = new ArrayList<Move>();

        byte[] board = position.getBoard();

        int[] offsets = new int[] {-7, -9, 7, 9};

        for (int offset : offsets) {
            int counter = 0;
            boolean collided = false;

            while (!collided) {
                counter++;

                int nextPos = piecePos + offset * counter;

                // Added the abs() checks to get rid of a bug where moves wrapped around the board
                if (checkInBounds(getRow(nextPos), getFile(nextPos)) 
                    && Math.abs(getRow(piecePos) - getRow(nextPos)) == counter 
                        && Math.abs(getFile(piecePos) - getFile(nextPos)) == counter) {
                    
                    if (getColor(board[nextPos]) != getColor(board[piecePos])) {
                        legalMoves.add(new Move(piecePos, nextPos));
                    }

                    if (getColor(board[nextPos]) != 0) {
                        collided = true;
                    }
                } else {
                    collided = true;
                }
            }

        }

        return legalMoves;
    }

    public static List<Move> horzMoves(ChessPosition position, int piecePos) {
        List<Move> legalMoves = new ArrayList<Move>();

        byte[] board = position.getBoard();

        int[] offsets = new int[] {-8, -1, 8, 1};

        for (int offset : offsets) {
            int counter = 0;
            boolean collided = false;

            while (!collided && counter < 7) {
                counter++;

                int nextPos = piecePos + offset * counter;

                if (checkInBounds(getRow(nextPos), getFile(nextPos)) 
                    && (Math.abs(getRow(piecePos) - getRow(nextPos)) == 0 
                        || Math.abs(getFile(piecePos) - getFile(nextPos)) == 0)) {
                    
                    if (getColor(board[nextPos]) != getColor(board[piecePos])) {
                        legalMoves.add(new Move(piecePos, nextPos));
                    }

                    if (getColor(board[nextPos]) != 0) {
                        collided = true;
                    }
                } else {
                    collided = true;
                }
            }

        }

        return legalMoves;
    }

    public static List<Move> kingMoves(ChessPosition position, int piecePos) {
        List<Move> legalMoves = new ArrayList<Move>();

        byte[] board = position.getBoard();

        int[] offsets = new int[] {-9, -8, -7, -1, 1, 7, 8, 9};

        for (int offset : offsets) {
            int nextPos = piecePos + offset;

            if (checkInBounds(getRow(nextPos), getFile(nextPos)) 
                && Math.abs(getRow(piecePos) - getRow(nextPos)) <= 1 
                    && Math.abs(getFile(piecePos) - getFile(nextPos)) <= 1) {
                
                if (getColor(board[nextPos]) != getColor(board[piecePos])) {
                    legalMoves.add(new Move(piecePos, nextPos));
                }
            }
        }

        return legalMoves;
    }

    public static List<Move> castlingMoves(ChessPosition position, List<Move> moves) {
        List<Move> legalMoves = new ArrayList<Move>();

        byte castleInfo = position.getCastlingInfo();

        if (position.getPlayingSide() == 1) {

            if ((castleInfo & 48) == 48 && Move.listContainsMove(moves, castlingRookMoves.get('L')))
                legalMoves.add(new Move(60, 58));
            
            if ((castleInfo & 40) == 40 && Move.listContainsMove(moves, castlingRookMoves.get('R'))) 
                legalMoves.add(new Move(60, 62));
        } else {

            if ((castleInfo & 6) == 6 && Move.listContainsMove(moves, castlingRookMoves.get('l'))) 
                legalMoves.add(new Move(4, 2));
            if ((castleInfo & 1) == 1 && Move.listContainsMove(moves, castlingRookMoves.get('r'))) 
                legalMoves.add(new Move(4, 6));
        }

        // System.out.println("CastleInfo: " + castleInfo + " Operator: 48 Result: " + (castleInfo & 48));

        return legalMoves;
    }

    public static List<Move> generateMoves(ChessPosition pos) {
        List<Move> moves = new ArrayList<Move>();
        byte[] board = pos.getBoard();

        for (int i = 0; i < 64; i++) {
            if (board[i] * pos.getPlayingSide() > 0) {
                List<Move> pieceMoves;

                switch (Math.abs(board[i])) {
                    case 1:  pieceMoves = MoveGenerator.pawnMoves(pos, i);
                             break;
                    case 2:  pieceMoves = MoveGenerator.knightMoves(pos, i);
                             break;
                    case 3:  pieceMoves = MoveGenerator.diagMoves(pos, i);
                             break;
                    case 4:  pieceMoves = MoveGenerator.horzMoves(pos, i);
                             break;
                    case 5:  pieceMoves = MoveGenerator.diagMoves(pos, i);
                             pieceMoves.addAll(MoveGenerator.horzMoves(pos, i));
                             break;
                    case 6:  pieceMoves = MoveGenerator.kingMoves(pos, i);
                             break;
                    default: pieceMoves = new ArrayList<>();
                             break;
                }

                moves.addAll(pieceMoves);
            }
        }

        moves.addAll(MoveGenerator.castlingMoves(pos, moves));

        return moves;
    }

    private static boolean checkInBounds(int row, int file) {
        return (row <= 7 && row >= 0 && file <=7 && file >= 0);
    }


    private static int getRow(int pos) {
        return (int) Math.floor(pos / 8);
    }

    private static int getFile(int pos) {
        return pos % 8;
    }

    private static int getColor(byte pieceVal) {
        int color = 0;

        if (pieceVal > 0) { color = 1; }
        else if (pieceVal < 0) { color = -1; }

        return color;
    }
}
