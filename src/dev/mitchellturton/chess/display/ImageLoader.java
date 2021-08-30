package dev.mitchellturton.chess.display;

import java.util.*;
import java.lang.Math;
import java.io.IOException;
import java.io.File;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ImageLoader {
    
    static Map<Integer, String> pieceNameMap = new HashMap<Integer, String>()
    {
        {
            put(1, "Pawn");
            put(2, "Knight");
            put(3, "Bishop");
            put(4, "Rook");
            put(5, "Queen");
            put(6, "King");
        }
    };

    static String resPath = "../res/sprites/";

    public static BufferedImage loadImage(int pieceID) {
        if (pieceID != 0) {
            String color = (pieceID < 0) ? "Black" : "White";
            String path = resPath + color + pieceNameMap.get(Math.abs(pieceID)) + ".png";
            File file = new File(path);

            try {
                return ImageIO.read(file);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        return null;
    }
}