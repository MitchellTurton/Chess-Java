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
        // window.setChessGame(new ChessGame("8/8/8/4q3/4Q3/8/8/8", -1));

        while (true) {
            window.renderWindow();
        }
    }
}
