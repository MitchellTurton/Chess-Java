package dev.mitchellturton.chess;

import dev.mitchellturton.chess.display.ChessGraphics;

public class Launcher {
    /* 
    Class to initiate and start the program
    */
    public static void main(String[] args) throws Exception {
        ChessGraphics window = new ChessGraphics("Chess", 800, 800);
        window.setChessGame(new ChessGame("r3k2r/pp3ppp/8/8/8/8/PP3PPP/R3K2R", 1));
        // window.setChessGame(new ChessGame());
        // window.setChessGame(new ChessGame("8/4P3/8/4k3/4K3/8/4p3/8", -1));

        while (true) {
            window.renderWindow();
        }
    }
}
