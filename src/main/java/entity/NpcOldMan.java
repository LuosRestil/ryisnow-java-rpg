package main.java.entity;

import java.util.Random;

import main.java.GamePanel;

public class NpcOldMan extends Entity {

    public NpcOldMan(GamePanel gamePanel) {
        super(gamePanel);

        direction = "down";
        speed = 1;

        setImages();
        setDialogues();
    }

    private void setImages() {
        up1 = getImage("/npcs/oldman_up_1");
        up2 = getImage("/npcs/oldman_up_2");
        down1 = getImage("/npcs/oldman_down_1");
        down2 = getImage("/npcs/oldman_down_2");
        left1 = getImage("/npcs/oldman_left_1");
        left2 = getImage("/npcs/oldman_left_2");
        right1 = getImage("/npcs/oldman_right_1");
        right2 = getImage("/npcs/oldman_right_2");
    }

    @Override
    public void setDirection(String newDir) {
        if (newDir == null) {
            int randNum = new Random().nextInt(100);

            if (randNum < 25) {
                direction = "up";
            } else if (randNum < 50) {
                direction = "down";
            } else if (randNum < 75) {
                direction = "left";
            } else {
                direction = "right";
            }
        } else {
            direction = newDir;
        }

    }

    private void setDialogues() {
        dialogues[0] = "Hello, lad.";
        dialogues[1] = "So, you came to this island seeking the treasure?";
        dialogues[2] = "I used to be a great wizard, but now...\nI'm a bit too old for taking an adventure.";
        dialogues[3] = "Well, good luck to you.";
    }
}
