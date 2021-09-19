package dev.mitchellturton.chess.dataclass;
import java.util.HashMap;
import java.util.Map;

public class ChessPosition {
    /*
    Contains all the information needed to represent a single position on the
    chess board
    */

    final private byte[] board;
    final private byte playingSide;
    private byte castleInfo;
    // private byte canCastle;

    final public static String startingFenString = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";

    // Map used to generate a position from the chars of a fenstring to bytes representing a piece
    final public static Map<Character, Integer> charToPieceMap = new HashMap<Character, Integer>()
    {
        {
            put('p', 1);
            put('n', 2);
            put('b', 3);
            put('r', 4);
            put('q', 5);
            put('k', 6);
        }
    };

    final private static Map<Integer, Integer> castlingBitKey = new HashMap<Integer, Integer>()
    {
        {
            put(0, 2);     // Bl    00000010
            put(4, 4);     // Bk    00000100
            put(7, 1);     // Br    00000001
            put(56, 16);   // Wl    00010000
            put(60, 32);   // Wk    00100000
            put(63, 8);    // Wr    00001000
            put(-127, 7);  // All B 00000111
            put(127, 56);  // All W 00111000
        }
    };

    final static private int[] castlingPositions = new int[] {0, 4, 7, 56, 60, 63};

    public ChessPosition() {
        this(ChessPosition.startingFenString, 1);
        this.castleInfo = 63;
    }

    public ChessPosition(String fenString, int playingSide) {
        this.board = ChessPosition.loadFenString(fenString);
        this.playingSide = (byte) playingSide;
    }

    public ChessPosition(byte[] board, int playingSide, byte castleInfo) {
        this(board, (byte) playingSide, castleInfo);
    }

    public ChessPosition(byte[] board, byte playingSide, byte castleInfo) {
        this.board = board;
        this.playingSide = playingSide;
        this.castleInfo = castleInfo;
    }

    public static byte[] loadFenString(String fenString) {
        /*
        Loads a position using the standard FEN representation 
        */
        byte[] board = new byte[64];

        int row = 0, file = 0;

        for (int i = 0; i < fenString.length(); ++i) {  
            char currChar = fenString.charAt(i);

            if (currChar == '/') {
                row++;
                file = 0;
            } else if (Character.isDigit(currChar)) {
                for (int j = 0; j < currChar - '0'; j++) {
                    board[row * 8 + file] = (byte) 0;
                    file++;
                }
            } else {
                int pieceVal = ChessPosition.charToPieceMap.get(Character.toLowerCase(currChar));
                pieceVal *= (Character.isUpperCase(currChar)) ? 1 : -1; // Color
                board[row * 8 + file] = (byte) pieceVal;

                file++;
            }
        }

        return board;
    }

    public ChessPosition makeMove(Move move) {
        /*
        Converts the current position to the next after a certain move is made
        */

        final int initPos = move.initialPos;
        final int finalPos = move.finalPos;

        byte[] newBoard = new byte[64];

        for (int i = 0; i < 64; i++) {
            newBoard[i] = this.board[i];
        }

        newBoard[finalPos] = this.board[initPos];
        newBoard[initPos] = 0;

        boolean isCastle = false;

        for (int i : castlingPositions) {
            if (i == move.initialPos) {
                isCastle = true;
                break;
            }
        }

        if (isCastle) {
            if (Math.abs(newBoard[move.finalPos]) == 6 && Math.abs(move.getInitialFile() - move.getFinalFile()) > 1) {
                final int rookColor = this.playingSide;

                newBoard[finalPos + (initPos - finalPos) / 2] = (byte) (4 * rookColor);
                newBoard[finalPos + ((initPos - finalPos > 0) ? -2 : 1)] = 0;

                updateCastleInfo((finalPos > 32) ? 127 : -127);
            } else {
                updateCastleInfo(initPos);
            }
        }

        return new ChessPosition(newBoard, -this.playingSide, this.castleInfo);
    }

    public void printBoard() {
        /*
        Prints the values of each piece to the terminal for debugging purposes
        */
        System.out.print("[");
        for (int i = 1; i <= 64; i++) {
            System.out.print(this.board[i-1]);

            if (i % 8 == 0) {
                System.out.println("]");
                
                if (i < 64)
                    System.out.print("[");
            } else {
                System.out.print(", ");
            }
        }
    }

    public void updateCastleInfo(int pos) {
        this.castleInfo = (byte) (this.castleInfo ^ castlingBitKey.get(pos));  // XOR
    }


    public byte[] getBoard() {
        return board;
    }

    public int getPlayingSide() {
        return (int) playingSide;
    }

    public byte getCastlingInfo() {
        return castleInfo;
    }

    public void setCastlingInfo(byte castleInfo) {
        this.castleInfo = castleInfo;
    }

    public static void main(String[] args) {
        ChessPosition chess = new ChessPosition();

        chess.printBoard();
    }
}
