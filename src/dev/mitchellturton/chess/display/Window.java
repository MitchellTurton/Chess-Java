package dev.mitchellturton.chess.display;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

import dev.mitchellturton.chess.input.MouseManager;

public class Window {
    /*
    Creates and holds all the information for the window that displays to 
    the screen
    */
    
    private JFrame window;
    private Canvas canvas;
    
    private String title;
    private int winWidth, winHeight;

    private MouseManager mouseManager;
    private boolean mouseClicked;

    public Window() {
        this("Window", 800, 800);
    }

    public Window(String title, int width, int height) {
        this.title = title;
        this.winWidth = width;
        this.winHeight = height;

        this.mouseManager = new MouseManager();

        initWindow();
    }

    private void initWindow() {
        // Window Settings

        window = new JFrame(title);
        window.setSize(winWidth, winHeight);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        window.addMouseListener(mouseManager);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(winWidth, winHeight));
        canvas.setMaximumSize(new Dimension(winWidth, winHeight));
        canvas.setMinimumSize(new Dimension(winWidth, winHeight));
        canvas.addMouseListener(mouseManager);

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

    public boolean justClicked() {
        boolean returnVal = mouseManager.mouseClicked;
        mouseManager.mouseClicked = false;
        return returnVal;
    }

    public int getMouseX() {
        return mouseManager.mouseX;
    }

    public int getMouseY() {
        return mouseManager.mouseY;
    }
}
