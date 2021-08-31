package dev.mitchellturton.chess.dataclass;

public class Move {
    private int initialPos;
    private int finalPos;

    public Move(int initialPos, int finalPos) {
        this.initialPos = initialPos;
        this.finalPos = finalPos;
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