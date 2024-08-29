package main.java;

import main.java.entity.NpcOldMan;
import main.java.object.Boots;
import main.java.object.Chest;
import main.java.object.Door;
import main.java.object.Key;
import main.java.object.SuperObject;
import main.java.entity.Entity;

public class AssetSetter {
    private final GamePanel gamePanel;


    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    
    public void populateObjects() {
        SuperObject key1 = new Key(gamePanel.tileSize, gamePanel.tileSize);
        key1.worldX = 23 * gamePanel.tileSize;
        key1.worldY = 7 * gamePanel.tileSize;
        gamePanel.objects[0] = key1;

        SuperObject key2 = new Key(gamePanel.tileSize, gamePanel.tileSize);
        key2.worldX = 23 * gamePanel.tileSize;
        key2.worldY = 40 * gamePanel.tileSize;
        gamePanel.objects[1] = key2; 

        SuperObject key3 = new Key(gamePanel.tileSize, gamePanel.tileSize);
        key3.worldX = 38 * gamePanel.tileSize;
        key3.worldY = 9 * gamePanel.tileSize;
        gamePanel.objects[2] = key3; 

        SuperObject door1 = new Door(gamePanel.tileSize, gamePanel.tileSize);
        door1.worldX = 10 * gamePanel.tileSize;
        door1.worldY = 11 * gamePanel.tileSize;
        gamePanel.objects[3] = door1; 
        SuperObject door2 = new Door(gamePanel.tileSize, gamePanel.tileSize);
        door2.worldX = 8 * gamePanel.tileSize;
        door2.worldY = 28 * gamePanel.tileSize;
        gamePanel.objects[4] = door2; 
        SuperObject door3 = new Door(gamePanel.tileSize, gamePanel.tileSize);
        door3.worldX = 12 * gamePanel.tileSize;
        door3.worldY = 22 * gamePanel.tileSize;
        gamePanel.objects[5] = door3; 
        
        SuperObject chest1 = new Chest(gamePanel.tileSize, gamePanel.tileSize);
        chest1.worldX = 10 * gamePanel.tileSize;
        chest1.worldY = 9 * gamePanel.tileSize;
        gamePanel.objects[6] = chest1;

        SuperObject boots1 = new Boots(gamePanel.tileSize, gamePanel.tileSize);
        boots1.worldX = 37 * gamePanel.tileSize;
        boots1.worldY = 42 * gamePanel.tileSize;
        gamePanel.objects[7] = boots1;
    }

    public void populateNpcs() {
        Entity oldMan1 = new NpcOldMan(gamePanel);
        oldMan1.worldX = gamePanel.tileSize * 21;
        oldMan1.worldY = gamePanel.tileSize * 21;
        gamePanel.npcs[0] = oldMan1;
    }
}
