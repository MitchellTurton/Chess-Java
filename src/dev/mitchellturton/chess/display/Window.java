package dev.mitchellturton.chess.display;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Dimension;
// import java.awt.Graphics;

public class Window {
    
    private JFrame window;
    private Canvas canvas;
    
    private String title;
    private int winWidth, winHeight;

    public Window() {
        this("Window", 800, 800);
    }

    public Window(String title, int width, int height) {
        this.title = title;
        this.winWidth = width;
        this.winHeight = height;

        initWindow();
    }

    private void initWindow() {
        window = new JFrame(title);
        window.setSize(winWidth, winHeight);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(winWidth, winHeight));
        canvas.setMaximumSize(new Dimension(winWidth, winHeight));
        canvas.setMinimumSize(new Dimension(winWidth, winHeight));

        window.add(canvas);
        window.pack();
    }

    public JFrame getWindow() {
        return window;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWidth() {
        return winWidth;
    }

    public void setWidth(int width) {
        this.winWidth = width;
    }

    public int getHeight() {
        return winHeight;
    }

    public void setHeight(int height) {
        this.winHeight = height;
    }
}
