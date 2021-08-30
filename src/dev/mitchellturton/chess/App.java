package dev.mitchellturton.chess;

import dev.mitchellturton.chess.dataclass.ChessPosition;
import dev.mitchellturton.chess.display.ChessGraphics;

public class App {
    public static void main(String[] args) throws Exception {
        ChessGraphics window = new ChessGraphics("Chess", 800, 800);
        window.setChessPosition(new ChessPosition("8/8/3rR3/8/2P5/8/8/8", 1));

        while (true) {
            window.renderWindow();
        }
    }
}
