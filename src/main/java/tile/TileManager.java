package main.java.tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import javax.imageio.ImageIO;

import main.java.GamePanel;
import main.java.Utils;

public class TileManager {
    GamePanel gamePanel;
    public Tile[] tiles;
    public int[][] tileMap;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.tiles = new Tile[50];
        this.tileMap = new int[gamePanel.maxWorldRow][gamePanel.maxWorldCol];
        getTileImages();
        loadMap("worldV2");
    }

    private void getTileImages() {
        // PLACEHOLDERS
        setup(0, "grass00", false);
        setup(1, "grass00", true);
        setup(2, "grass00", true);
        setup(3, "grass00", false);
        setup(4, "grass00", true);
        setup(5, "grass00", false);
        setup(6, "grass00", false);
        setup(7, "grass00", false);
        setup(8, "grass00", false);
        setup(9, "grass00", false);
        // PLACEHOLDERS

        setup(10, "grass00", false);
        setup(11, "grass01", false);
        setup(12, "water00", true);
        setup(13, "water01", true);
        setup(14, "water02", true);
        setup(15, "water03", true);
        setup(16, "water04", true);
        setup(17, "water05", true);
        setup(18, "water06", true);
        setup(19, "water07", true);
        setup(20, "water08", true);
        setup(21, "water09", true);
        setup(22, "water10", true);
        setup(23, "water11", true);
        setup(24, "water12", true);
        setup(25, "water13", true);
        setup(26, "road00", false);
        setup(27, "road01", false);
        setup(28, "road02", false);
        setup(29, "road03", false);
        setup(30, "road04", false);
        setup(31, "road05", false);
        setup(32, "road06", false);
        setup(33, "road07", false);
        setup(34, "road08", false);
        setup(35, "road09", false);
        setup(36, "road10", false);
        setup(37, "road11", false);
        setup(38, "road12", false);
        setup(39, "earth", false);
        setup(40, "wall", true);
        setup(41, "tree", true);
    }

    private void setup(int idx, String name, boolean isSolid) {
        try {
            tiles[idx] = new Tile();
            BufferedImage img = ImageIO.read(
                    getClass().getResourceAsStream(String.format("../../resources/tiles/%s.png", name)));
            tiles[idx].image = Utils.scaleImage(img, gamePanel.tileSize, gamePanel.tileSize);
            tiles[idx].solid = isSolid;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadMap(String mapName) {
        try {
            InputStream is = getClass().getResourceAsStream(String.format("../../resources/maps/%s.txt", mapName));
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            for (int i = 0; i < gamePanel.maxWorldRow; i++) {
                String line = br.readLine();
                int[] nums = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();
                for (int j = 0; j < gamePanel.maxWorldCol; j++) {
                    tileMap[i][j] = nums[j];
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2d) {
        for (int col = 0; col < gamePanel.maxWorldCol; col++) {
            for (int row = 0; row < gamePanel.maxWorldRow; row++) {
                var tileType = tileMap[row][col];
                int worldX = col * gamePanel.tileSize;
                int worldY = row * gamePanel.tileSize;
                int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
                int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;
                if (screenX >= -gamePanel.tileSize &&
                        screenX < gamePanel.screenWidth &&
                        screenY >= -gamePanel.tileSize &&
                        screenY < gamePanel.screenHeight) {
                    g2d.drawImage(
                            tiles[tileType].image,
                            screenX,
                            screenY,
                            null);
                }
            }
        }
    }
}
