package dev.mitchellturton.chess.ai.evaluators;

import dev.mitchellturton.chess.dataclass.ChessPosition;

public interface Evaluator {
    public int evaluatePosition(ChessPosition pos);
}
