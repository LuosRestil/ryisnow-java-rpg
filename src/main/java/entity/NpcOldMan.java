package main.java.entity;

import main.java.GamePanel;

public class NpcOldMan extends Entity {

    public NpcOldMan(GamePanel gamePanel) {
        super(gamePanel);
    
        direction = "down";
        speed = 1;

        setImages();
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

}
