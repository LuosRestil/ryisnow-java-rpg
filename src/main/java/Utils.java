package main.java;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Utils {
    public static BufferedImage scaleImage(BufferedImage img, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, img.getType());
        Graphics2D g2d = scaledImage.createGraphics();
        g2d.drawImage(img, 0, 0, width, height, null);
        g2d.dispose();
        return scaledImage;
    }
}
