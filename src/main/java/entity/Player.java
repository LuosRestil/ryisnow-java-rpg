package main.java.entity;

// import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.java.GamePanel;
import main.java.KeyHandler;
import main.java.Utils;

public class Player extends Entity {
    GamePanel gamePanel;
    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;

    public int keys = 0;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        this.screenX = gamePanel.screenWidth / 2 - gamePanel.tileSize / 2;
        this.screenY = gamePanel.screenHeight / 2 - gamePanel.tileSize / 2;

        this.hitbox = new Rectangle(9, 20, 30, 28);
        // this.hitbox = new Rectangle(0, 0, gamePanel.tileSize, gamePanel.tileSize);
        this.hitboxDefaultX = this.hitbox.x;
        this.hitboxDefaultY = this.hitbox.y;

        setDefaultValues();
        setPlayerImages();
    }

    private void setDefaultValues() {
        worldX = gamePanel.tileSize * 23;
        worldY = gamePanel.tileSize * 21;
        speed = 4;
        direction = "down";
    }

    public void setPlayerImages() {
        up1 = setup("boy_up_1");
        up2 = setup("boy_up_2");
        down1 = setup("boy_down_1");
        down2 = setup("boy_down_2");
        left1 = setup("boy_left_1");
        left2 = setup("boy_left_2");
        right1 = setup("boy_right_1");
        right2 = setup("boy_right_2");
    }

    private BufferedImage setup(String name) {
        try {
            BufferedImage img = ImageIO
                    .read(getClass().getResourceAsStream(String.format("../../resources/player/%s.png", name)));
            img = Utils.scaleImage(img, gamePanel.tileSize, gamePanel.tileSize);
            return img;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void update() {
        boolean keyPressed = keyHandler.upPressed ||
                keyHandler.downPressed ||
                keyHandler.leftPressed ||
                keyHandler.rightPressed;

        if (keyHandler.upPressed) {
            direction = "up";
            worldY -= speed;
        } else if (keyHandler.downPressed) {
            direction = "down";
            worldY += speed;
        } else if (keyHandler.leftPressed) {
            direction = "left";
            worldX -= speed;
        } else if (keyHandler.rightPressed) {
            direction = "right";
            worldX += speed;
        }

        gamePanel.collisionChecker.resolveTileCollisions(this);
        int collidedObjectIndex = gamePanel.collisionChecker.checkObjects(this, true);
        if (collidedObjectIndex > -1)
            interactObject(collidedObjectIndex);
        gamePanel.collisionChecker.checkObjects(this, true);

        if (keyPressed) {
            spriteCounter++;
            if (spriteCounter > 10 && keyPressed) {
                spriteNumber = spriteNumber == 1 ? 2 : 1;
                spriteCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2d) {
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
        g2d.drawImage(image, screenX, screenY, null);

        // hitbox
        // g2d.setColor(new Color(1f, 0f, 0f, .5f));
        // g2d.fillRect(screenX + hitbox.x, screenY + hitbox.y, hitbox.width,
        // hitbox.height);
    }

    private void interactObject(int idx) {
        String objName = gamePanel.objects[idx].name;
        switch (objName) {
            case "Key":
                keys++;
                gamePanel.objects[idx] = null;
                gamePanel.playSoundEffect(1);
                gamePanel.ui.showMessage("You got a key!");
                break;
            case "Door":
                if (keys > 0) {
                    keys--;
                    gamePanel.objects[idx] = null;
                    gamePanel.playSoundEffect(3);
                    gamePanel.ui.showMessage("Door open!");
                } else {
                    gamePanel.ui.showMessage("You need a key...");
                }
                break;
            case "Boots":
                speed += 2;
                gamePanel.objects[idx] = null;
                gamePanel.playSoundEffect(2);
                gamePanel.ui.showMessage("Speed up!");
                break;
            case "Chest":
                win();
                break;
        }
    }

    private void win() {
        gamePanel.ui.gameFinished = true;
        gamePanel.stopMusic();
        gamePanel.playSoundEffect(4);
        gamePanel.stopGameThread();
    }
}
