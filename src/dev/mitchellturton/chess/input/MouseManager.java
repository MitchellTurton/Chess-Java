package dev.mitchellturton.chess.input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseManager extends MouseAdapter {
    
    public int mouseX;
    public int mouseY;

    public boolean mouseClicked;

    public MouseManager() {
        mouseX = -1;
        mouseY = -1;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();

        mouseClicked = true;
    }
}
