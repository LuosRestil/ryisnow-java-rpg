package main.java;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import main.java.entity.Entity;
import main.java.entity.Player;
import main.java.enums.GameState;
import main.java.object.SuperObject;
import main.java.tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS
    final int originalTileSize = 16; // px
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; // px
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    // player defaults
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    // limiting fps
    int targetFps = 60;
    double frameTimeNanos = 1_000_000_000 / targetFps;
    double elapsed = 0;
    long lastTime = System.nanoTime();
    // monitoring fps
    long fpsTimer = 0;
    int frames = 0;
    int fps = 0;

    // game state
    public GameState gameState;

    KeyHandler keyHandler = new KeyHandler(this);
    Thread gameThread;

    public Player player = new Player(this, keyHandler);
    public SuperObject[] objects = new SuperObject[10];
    public Entity[] npcs = new Entity[10];

    TileManager tileManager = new TileManager(this);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);

    public SoundManager musicManager = new SoundManager();
    public SoundManager soundEffectManager = new SoundManager();

    public UI ui = new UI(this);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void stopGameThread() {
        gameThread = null;
    }

    public void setup() {
        assetSetter.populateObjects();
        assetSetter.populateNpcs();
        playMusic(0);
        gameState = GameState.PLAY;
    }

    @Override
    public void run() {
        while (gameThread != null) {
            // limiting fps
            long currentTime = System.nanoTime();
            long dtNanos = currentTime - lastTime;
            lastTime = currentTime;
            elapsed += dtNanos;
            fpsTimer += dtNanos;

            if (elapsed >= frameTimeNanos) {
                frames++;
                elapsed -= frameTimeNanos;
                update();
                repaint();
            }

            // fps monitoring
            if (fpsTimer >= 1_000_000_000) {
                fpsTimer -= 1_000_000_000;
                fps = frames;
                frames = 0;
            }
        }
    }

    public void update() {
        if (gameState == GameState.PLAY) {
            player.update();
        } else if (gameState == GameState.PAUSE) {
            // nothing
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        tileManager.draw(g2d);

        for (var obj : objects) 
            if (obj != null) obj.draw(g2d, this);

        for (var npc : npcs)
            if (npc != null) npc.draw(g2d);

        player.draw(g2d);

        ui.draw(g2d);

        // fps
        // g2d.setColor(Color.WHITE);
        // g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 20));
        // g2d.drawString("fps: " + fps, 10, 20);
        g2d.dispose();
    }

    public void end() {
        ui.gameFinished = true;
    }

    public void playMusic(int idx) {
        musicManager.setFile(idx);
        musicManager.play();
        musicManager.loop();
    }

    public void stopMusic() {
        musicManager.stop();
    }

    public void playSoundEffect(int idx) {
        soundEffectManager.setFile(idx);
        soundEffectManager.play();
    }
}
