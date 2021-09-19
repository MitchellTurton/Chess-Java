package dev.mitchellturton.chess.ai;

import java.util.List;

import dev.mitchellturton.chess.MoveGenerator;
import dev.mitchellturton.chess.ai.evaluators.SimpleEvaluator;
import dev.mitchellturton.chess.dataclass.ChessPosition;
import dev.mitchellturton.chess.dataclass.Move;


public class TreeSearch {

    SimpleEvaluator evaluator;

    public TreeSearch(SimpleEvaluator evaluator) {
        this.evaluator = evaluator;
    }
    
    public static int countMoves(ChessPosition pos, int depth) {
        if (depth == 0) {
            return 1;
        }

        int numMoves = 0;
        List<Move> moves = MoveGenerator.generateMoves(pos);
        
        for (Move move : moves) {
            ChessPosition nextPos = pos.makeMove(move);
            numMoves += TreeSearch.countMoves(nextPos, depth - 1);
        }

        return numMoves;
    }

    // public int evaluateMoves(ChessPosition pos, int depth) {
    //     if (depth == 0) {
    //         return evaluator.evaluatePosition(pos);
    //     }


    // }
    public static void main(String[] args) {
        ChessPosition testPos = new ChessPosition();

        int numMoves = TreeSearch.countMoves(testPos, 4);

        System.out.println("Number of moves: " + numMoves);
    }
}
