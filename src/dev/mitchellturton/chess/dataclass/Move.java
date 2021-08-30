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

    public int getFinalPos() {
        return finalPos;
    }
}