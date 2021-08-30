package dev.mitchellturton.chess.dataclass;
import java.util.HashMap;
import java.util.Map;

public class ChessPosition {
    final private byte[] board;
    final private byte playingSide;

    public static String startingFenString = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
    public static Map<Character, Integer> charToPieceMap = new HashMap<Character, Integer>()
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

    public ChessPosition() {
        this(ChessPosition.startingFenString, 1);
    }

    public ChessPosition(String fenString, int playingSide) {
        this.board = ChessPosition.loadFenString(fenString);
        this.playingSide = (byte) playingSide;
    }

    public ChessPosition(byte[] board, int playingSide) {
        this.board = board;
        this.playingSide = (byte) playingSide;
    }

    public ChessPosition(byte[] board, byte playingSide) {
        this.board = board;
        this.playingSide = playingSide;
    }

    public static byte[] loadFenString(String fenString) {
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
                pieceVal *= (Character.isUpperCase(currChar)) ? 1 : -1;
                board[row * 8 + file] = (byte) pieceVal;

                file++;
            }
        }

        return board;
    }

    public ChessPosition makeMove(ChessPosition pos, Move move) {
        byte[] newBoard = this.board;
        newBoard[move.getFinalPos()] = this.board[move.getInitialPos()];
        newBoard[move.getInitialPos()] = 0;

        return new ChessPosition(newBoard, -this.playingSide);
    }

    public void printBoard() {
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

    /**
     * @return byte[] return the board
     */
    public byte[] getBoard() {
        return board;
    }

    /**
     * @return byte return the playingSide
     */
    public int getPlayingSide() {
        return (int) playingSide;
    }

    public static void main(String[] args) {
        ChessPosition chess = new ChessPosition();

        chess.printBoard();

        // System.out.println("R: " + ChessPosition.charToPieceMap.get('r'));
    }
}