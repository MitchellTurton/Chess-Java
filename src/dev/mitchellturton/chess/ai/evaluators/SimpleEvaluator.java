package dev.mitchellturton.chess.ai.evaluators;

import java.util.HashMap;
import java.util.Map;

import dev.mitchellturton.chess.dataclass.ChessPosition;

public class SimpleEvaluator implements Evaluator{
    final private static Map<Integer, Integer> pieceValues = new HashMap<Integer, Integer>()
    {
        {
            put(1, 100);
            put(2, 300);
            put(3, 300);
            put(4, 500);
            put(5, 900);
            put(6, 10000);
        }
    };

    @Override
    public int evaluatePosition(ChessPosition pos) {
        int evaluation = 0;
        int currColor = pos.getPlayingSide();

        for (byte piece : pos.getBoard()) {
            if (piece != 0) {
                evaluation += currColor * (piece > 0 ? 1 : -1) * pieceValues.get(Math.abs(piece));
            }
        }

        return evaluation;
    }

    public static void main(String[] args) {
        // ChessPosition pos = new ChessPosition("8/8/3Q4/8/8/2r5/8/8", -1);
        // pos.setCastlingInfo((byte) 0);
        ChessPosition pos = new ChessPosition();

        SimpleEvaluator evaluator = new SimpleEvaluator();

        int evaluation = evaluator.evaluatePosition(pos);

        System.out.println("Evaluation: " + evaluation);
    }
}
