package dev.mitchellturton.chess.display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import dev.mitchellturton.chess.ChessGame;
import dev.mitchellturton.chess.dataclass.Move;



public class ChessGraphics {
    /*
    Class handling all of the actual drawing to the screen
    */
    private Window window;

    private BufferStrategy bs;
    private Graphics g;

    private int squareSize;

    private Color lightSquareColor;
    private Color darkSquareColor;
    private Color lightHighlightColor;
    private Color darkHighlightColor;

    private ChessGame chessGame;

    private BufferedImage[] sprites;

    public ChessGraphics(String title, int width, int height) {
        window = new Window(title, width, height);

        squareSize = window.getWidth() / 8;

        lightSquareColor = new Color(240, 240, 240);
        darkSquareColor = new Color(46, 139, 87);
        lightHighlightColor = new Color(45, 45, 150);
        darkHighlightColor = new Color(20, 20, 175);
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
        // Draws the pieces from the current position

        for (int i = 0; i < 64; i++) {
            g.drawImage(sprites[i], (i % 8) * squareSize, 
                        (int) Math.floor(i / 8) * squareSize, 
                        squareSize, squareSize, null);
        }
    }

    private void highlightMoves() {
        // Highlights squares a selected piece can move to
        
        for (Move move : chessGame.getLegalMoves()) {
            if (move.initialPos == chessGame.getSelectedPiece()) {
                int row = move.getFinalRow();
                int file = move.getFinalFile();
                
                g.setColor(((row + file) % 2 == 0) ? darkHighlightColor : lightHighlightColor);
                g.fillRect(file * squareSize, row * squareSize, squareSize, squareSize);
            }
        }
    }

    public void renderWindow() {

        if (chessGame.updateGraphics()) {
            updateSprites();
            chessGame.setUpdateGraphics(false);
        }

        bs = window.getCanvas().getBufferStrategy();
        if (bs == null) {
            window.getCanvas().createBufferStrategy(3);
            return;
        }

        g = bs.getDrawGraphics();
        g.clearRect(0, 0, window.getWidth(), window.getHeight());

        drawBoard();
        highlightMoves();
        drawPosition();

        bs.show();
        g.dispose();

        if (window.justClicked()) 
            mousePressed(window.getMouseX(), window.getMouseY());
    }

    public void updateSprites() {
        byte[] chessBoard = chessGame.getBoard();

        sprites = new BufferedImage[64];

        for (int i = 0; i < 64; i++) {
            int pieceID = (int) chessBoard[i];

            sprites[i] = ImageLoader.loadImage(pieceID);
        }
    }

    public void mousePressed(int mouseX, int mouseY) {
        int row = mouseY / squareSize;
        int file = mouseX / squareSize;

        if (chessGame.isCurrentColor(row * 8 + file)) {
            chessGame.selectPiece(row * 8 + file);
        } else if (Move.listContainsMove(chessGame.getLegalMoves(), new Move(chessGame.getSelectedPiece(), row * 8 + file))){
            chessGame.movePiece(new Move(chessGame.getSelectedPiece(), row * 8 + file));
            chessGame.unselectPiece();
        } else {
            chessGame.unselectPiece();
        }
    }

    public void setChessGame(ChessGame game) {
        this.chessGame = game;
        updateSprites();
    }

    public ChessGame getChessGame() {
        return chessGame;
    }
}
