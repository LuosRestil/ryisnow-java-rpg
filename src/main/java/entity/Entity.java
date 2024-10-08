package main.java.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.java.GamePanel;
import main.java.Utils;

public abstract class Entity {
    protected GamePanel gamePanel;
    public int worldX, worldY;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    // TODO allow multiple directions (up/right, down/left, etc.)
    public String direction;
    public int spriteCounter = 0;
    public int spriteNumber = 1;

    public Rectangle hitbox = new Rectangle(0, 0, 48, 48);
    public int hitboxDefaultX, hitboxDefaultY;

    protected int turnCounter = 0;

    protected String[] dialogues = new String[20];
    protected int currentDialogue = 0;

    public Entity(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    protected BufferedImage getImage(String path) {
        try {
            BufferedImage img = ImageIO
                    .read(getClass().getResourceAsStream(String.format("../../resources/%s.png", path)));
            img = Utils.scaleImage(img, gamePanel.tileSize, gamePanel.tileSize);
            return img;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setDirection(String direction) {
    };

    public void update() {
        turnCounter++;
        if (turnCounter > 100) {
            setDirection(null);
            turnCounter = 0;
        }
        switch (direction) {
            case "up":
                worldY -= speed;
                break;
            case "down":
                worldY += speed;
                break;
            case "left":
                worldX -= speed;
                break;
            case "right":
                worldX += speed;
                break;
        }

        gamePanel.collisionChecker.resolveTileCollisions(this);
        gamePanel.collisionChecker.checkPlayer(this);

        spriteCounter++;
        if (spriteCounter > 10) {
            spriteNumber = spriteNumber == 1 ? 2 : 1;
            spriteCounter = 0;
        }

    }

    public void draw(Graphics2D g2d) {
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;
        if (Utils.isOnScreen(screenX, screenY, gamePanel)) {
            g2d.drawImage(
                    getCurrentImage(),
                    screenX,
                    screenY,
                    null);
        }
        // hitbox
        // g2d.setColor(new Color(0f, 0f, 1f, .5f ));
        // g2d.fillRect(screenX + hitbox.x, screenY + hitbox.y, hitbox.width,
        // hitbox.height);
    }

    protected BufferedImage getCurrentImage() {
        BufferedImage image = null;
        switch (direction) {
            case "up":
                image = spriteNumber == 1 ? up1 : up2;
                break;
            case "down":
                image = spriteNumber == 1 ? down1 : down2;
                break;
            case "left":
                image = spriteNumber == 1 ? left1 : left2;
                break;
            case "right":
                image = spriteNumber == 1 ? right1 : right2;
                break;
        }
        return image;
    }

    public void speak() {
        gamePanel.ui.currentDialogue = dialogues[currentDialogue];
        currentDialogue++;
        if (currentDialogue > dialogues.length || dialogues[currentDialogue] == null) {
            currentDialogue = 0;
        }

        switch (gamePanel.player.direction) {
            case ("up"):
                direction = "down";
                break;
            case ("down"):
                direction = "up";
                break;
            case ("left"):
                direction = "right";
                break;
            case ("right"):
                direction = "left";
                break;
            default:
                break;
        }
    };

    protected void facePlayer() {
        // if top of player hitbox is
    }
}
