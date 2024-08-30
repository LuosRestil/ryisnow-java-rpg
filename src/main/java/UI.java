package main.java;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;

import main.java.enums.GameState;
import main.java.object.Key;

public class UI {
    private GamePanel gamePanel;
    private Graphics2D g2d;
    private Font arial40, arial60B;

    private BufferedImage keyImage;

    private boolean showMessage = false;
    private String message = "";
    private int messageCounter = 0;
    private int messageTime = 80;

    public boolean gameFinished = false;

    // dialogue
    private Color subWindowFillColor = new Color(0, 0, 0, 210);
    private Color subWindowStrokeColor = new Color(255, 255, 255);
    private Stroke subWindowStroke = new BasicStroke(5);
public String currentDialogue = "";

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        arial40 = new Font("Arial", Font.PLAIN, 40);
        arial60B = new Font("Arial", Font.BOLD, 60);

        keyImage = new Key(gamePanel.tileSize, gamePanel.tileSize).image;
    }

    public void showMessage(String text) {
        message = text;
        showMessage = true;
    }

    public void draw(Graphics2D g2d) {
        this.g2d = g2d;
        g2d.setFont(arial40);
        g2d.setColor(Color.WHITE);

        if (gameFinished) {
            showEndText(g2d);
            return;
        }

        if (gamePanel.gameState == GameState.PLAY) {
            g2d.drawImage(keyImage, gamePanel.tileSize / 2, gamePanel.tileSize / 2, null);
            g2d.drawString("x " + gamePanel.player.keys, 74, 65);

            if (showMessage) {
                // TODO nonlinear transform
                float opacity = (messageTime - messageCounter) / (float) messageTime;
                g2d.setFont(g2d.getFont().deriveFont(30f));
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                g2d.drawString(message, gamePanel.tileSize / 2, gamePanel.tileSize * 5 - messageCounter);
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                messageCounter++;
                if (messageCounter > messageTime) {
                    showMessage = false;
                    messageCounter = 0;
                }
            }
        } else if (gamePanel.gameState == GameState.PAUSE) {
            showPausedText();
        } else if (gamePanel.gameState == GameState.DIALOGUE) {
            showDialogue();
        }

    }

    private void showEndText(Graphics2D g2d) {
        String text;
        int x, y;

        g2d.setFont(arial40);
        g2d.setColor(Color.WHITE);
        text = "You found the treasure!";
        x = getXForCenteredText(text);
        y = gamePanel.screenHeight / 2 - gamePanel.tileSize * 2;
        g2d.drawString(text, x, y);

        g2d.setFont(arial60B);
        g2d.setColor(Color.YELLOW);
        text = "Congratulations!";
        x = getXForCenteredText(text);
        y = gamePanel.screenHeight / 2 + gamePanel.tileSize * 2;
        g2d.drawString(text, x, y);

        return;
    }

    private void showPausedText() {
        String text = "PAUSED";
        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 80));
        int x = getXForCenteredText(text);
        int y = gamePanel.screenHeight / 2;
        g2d.drawString(text, x, y);
    }

    private void showDialogue() {
        // window
        int x = gamePanel.tileSize * 2;
        int y = gamePanel.tileSize / 2;
        int width = gamePanel.screenWidth - gamePanel.tileSize * 4;
        int height = gamePanel.tileSize * 4;
        drawSubWindow(x, y, width, height);

        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 20));
        x += gamePanel.tileSize;
        y += gamePanel.tileSize;
        for (String line : currentDialogue.split("\n")) {
            g2d.drawString(line, x, y);
            y += 24;
        }
    }

    private void drawSubWindow(int x, int y, int width, int height) {
        g2d.setColor(subWindowFillColor);
        g2d.fillRoundRect(x, y, width, height, 35, 35);
        g2d.setColor(subWindowStrokeColor);
        g2d.setStroke(subWindowStroke);
        g2d.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    private int getTextWidth(String text) {
        return (int) g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
    }

    private int getXForCenteredText(String text) {
        return gamePanel.screenWidth / 2 - getTextWidth(text) / 2;
    }
}
