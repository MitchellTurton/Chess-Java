package dev.mitchellturton.chess;

import dev.mitchellturton.chess.display.ChessGraphics;

public class Launcher {
    public static void main(String[] args) throws Exception {
        ChessGraphics window = new ChessGraphics("Chess", 800, 800);
        // window.setChessGame(new ChessGame("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR", 1));
        window.setChessGame(new ChessGame("8/8/8/8/8/8/8/4n3", -1));

        while (true) {
            window.renderWindow();
        }
    }
}
