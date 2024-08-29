package main.java.object;

// import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.java.GamePanel;
import main.java.Utils;

public abstract class SuperObject {
    public BufferedImage image;
    public String name;
    public boolean solid = false;
    public int worldX, worldY;
    public Rectangle hitbox = new Rectangle(0, 0, 48, 48);
    public int hitboxDefaultX = 0;
    public int hitboxDefaultY = 0;

    public void draw(Graphics2D g2d, GamePanel gamePanel) {
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;
        if (screenX >= -gamePanel.tileSize &&
                screenX < gamePanel.screenWidth &&
                screenY >= -gamePanel.tileSize &&
                screenY < gamePanel.screenHeight) {
            g2d.drawImage(
                    image,
                    screenX,
                    screenY,
                    null);
        }
        // hitbox
        // g2d.setColor(new Color(0f, 0f, 1f, .5f ));
        // g2d.fillRect(screenX + hitbox.x, screenY + hitbox.y, hitbox.width,
        // hitbox.height);
    }

    void setupImage(String name, int width, int height) {
        try {
            BufferedImage img = ImageIO.read(getClass().getResourceAsStream(String.format("../../resources/objects/%s.png", name)));
            if (img == null) System.out.println("OH GOD, OH FUCK");
            img = Utils.scaleImage(img, width, height);
            this.image = img;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
