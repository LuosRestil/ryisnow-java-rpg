package main.java;

import java.awt.Font;
import java.awt.font.FontRenderContext;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        // handle asset loading, etc., before window is created so player doesn't see incomplete content
        preload();
        GamePanel gamePanel = new GamePanel();
        gamePanel.setup();

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Adventures of Blue Guy");

        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }

    private static void preload() {
        // awt's font initialization is VERY slow, and causes a noticeable delay the first time we draw text in the game
        // this will trigger initialization before we even create a window, so no delay will be obvious to the user
        Font font = new Font("Arial", Font.PLAIN, 12);
        FontRenderContext frc = new FontRenderContext(null, true, true);
        font.getStringBounds(" ", frc);
    }
}
