package dev.mitchellturton.chess.ai;

import java.util.List;
import java.util.Random;

import dev.mitchellturton.chess.dataclass.Move;

public class RandomMoves {
    
    static Random randGen = new Random();

    public static Move getMove(List<Move> moves) {
        return moves.get(randGen.nextInt(moves.size()));
    }
}