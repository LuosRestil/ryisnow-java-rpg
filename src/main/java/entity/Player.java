package main.java.entity;

// import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import main.java.GamePanel;
import main.java.KeyHandler;

public class Player extends Entity {
    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;

    public int keys = 0;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        super(gamePanel);

        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        this.screenX = gamePanel.screenWidth / 2 - gamePanel.tileSize / 2;
        this.screenY = gamePanel.screenHeight / 2 - gamePanel.tileSize / 2;

        this.hitbox = new Rectangle(9, 20, 30, 28);
        // this.hitbox = new Rectangle(0, 0, gamePanel.tileSize, gamePanel.tileSize);
        this.hitboxDefaultX = this.hitbox.x;
        this.hitboxDefaultY = this.hitbox.y;

        setDefaultValues();
        setImages();
    }

    private void setDefaultValues() {
        worldX = gamePanel.tileSize * 23;
        worldY = gamePanel.tileSize * 21;
        speed = 4;
        direction = "down";
    }

    private void setImages() {
        up1 = getImage("/player/boy_up_1");
        up2 = getImage("/player/boy_up_2");
        down1 = getImage("/player/boy_down_1");
        down2 = getImage("/player/boy_down_2");
        left1 = getImage("/player/boy_left_1");
        left2 = getImage("/player/boy_left_2");
        right1 = getImage("/player/boy_right_1");
        right2 = getImage("/player/boy_right_2");
    }

    public void update() {
        boolean keyPressed = keyHandler.upPressed ||
                keyHandler.downPressed ||
                keyHandler.leftPressed ||
                keyHandler.rightPressed;

        if (keyHandler.upPressed) {
            direction = "up";
        } else if (keyHandler.downPressed) {
            direction = "down";
        } else if (keyHandler.leftPressed) {
            direction = "left";
        } else if (keyHandler.rightPressed) {
            direction = "right";
        }

        if (keyPressed) {
            switch(direction) {
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break;
            }
        }

        gamePanel.collisionChecker.resolveTileCollisions(this);
        int collidedObjectIndex = gamePanel.collisionChecker.checkObjects(this, true);
        if (collidedObjectIndex > -1)
            interactObject(collidedObjectIndex);
        gamePanel.collisionChecker.checkEntities(this, gamePanel.npcs);
        
        if (keyPressed) {
            spriteCounter++;
            if (spriteCounter > 10 && keyPressed) {
                spriteNumber = spriteNumber == 1 ? 2 : 1;
                spriteCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(getCurrentImage(), screenX, screenY, null);

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
