package dev.mitchellturton.chess.display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import dev.mitchellturton.chess.dataclass.ChessPosition;


public class ChessGraphics {
    private Window window;

    private BufferStrategy bs;
    private Graphics g;

    private int squareSize;

    private Color lightSquareColor;
    private Color darkSquareColor;

    private ChessPosition chessPosition;

    private BufferedImage[] sprites;

    public ChessGraphics(String title, int width, int height) {
        window = new Window(title, width, height);

        squareSize = window.getWidth() / 8;

        lightSquareColor = new Color(240, 240, 240);
        darkSquareColor = new Color(46, 139, 87);
    }

    private void drawBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                g.setColor(((i + j) % 2 == 0) ? darkSquareColor : lightSquareColor);
                g.fillRect(j * squareSize, i * squareSize, squareSize, squareSize);
            }
        }
    }

    private void drawPosition() {
        for (int i = 0; i < 64; i++) {
            g.drawImage(sprites[i], (i % 8) * squareSize, 
                        (int) Math.floor(i / 8) * squareSize, 
                        squareSize, squareSize, null);
        }
    }

    public void renderWindow() {
        bs = window.getCanvas().getBufferStrategy();
        if (bs == null) {
            window.getCanvas().createBufferStrategy(3);
            return;
        }

        g = bs.getDrawGraphics();
        g.clearRect(0, 0, window.getWidth(), window.getHeight());

        drawBoard();
        drawPosition();

        bs.show();
        g.dispose();
    }

    public static void main(String[] args) {
        ChessGraphics graph = new ChessGraphics("Chess", 800, 800);
        
        while (true) {
            graph.renderWindow();
        }
    }

    public void setChessPosition(ChessPosition position) {
        this.chessPosition = position;
        byte[] chessBoard = position.getBoard();

        sprites = new BufferedImage[64];

        for (int i = 0; i < 64; i++) {
            int pieceID = (int) chessBoard[i];

            sprites[i] = ImageLoader.loadImage(pieceID);
        }
    }

    public ChessPosition getChessPosition() {
        return chessPosition;
    }
}
