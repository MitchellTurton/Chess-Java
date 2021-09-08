package dev.mitchellturton.chess.dataclass;

import java.util.List;

public class Move {
    /*
    DataClass holding an initial and final position a pice can move to
    */

    final private int initialPos;
    final private int finalPos;

    public Move(int initialPos, int finalPos) {
        this.initialPos = initialPos;
        this.finalPos = finalPos;
    }

    static public boolean listContainsMove(List<Move> moveList, Move move) {

        for (Move m : moveList) {
            if (move.getInitialPos() == m.getInitialPos() && move.getFinalPos() == m.getFinalPos()) {
                return true;
            }
        }

        return false;
    }

    public int getInitialPos() {
        return initialPos;
    }

    public int getInitialRow() {
        return (int) Math.floor(initialPos / 8);
    }

    public int getInitialFile() {
        return initialPos % 8;
    }

    public int getFinalPos() {
        return finalPos;
    }

    public int getFinalRow() {
        return (int) Math.floor(finalPos / 8);
    }

    public int getFinalFile() {
        return finalPos % 8;
    }
}
