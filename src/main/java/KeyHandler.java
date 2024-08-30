package main.java;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.java.enums.GameState;

public class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
    private GamePanel gamePanel;

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (gamePanel.gameState == GameState.PLAY) {
            switch (code) {
                case KeyEvent.VK_W:
                    upPressed = true;
                    break;
                case KeyEvent.VK_S:
                    downPressed = true;
                    break;
                case KeyEvent.VK_A:
                    leftPressed = true;
                    break;
                case KeyEvent.VK_D:
                    rightPressed = true;
                    break;
                case KeyEvent.VK_P:
                    gamePanel.gameState = GameState.PAUSE;
                    break;
                case KeyEvent.VK_ENTER:
                    enterPressed = true;
                default:
                    break;
            }
        } else if (gamePanel.gameState == GameState.PAUSE) {
            switch(code) {
                case KeyEvent.VK_P:
                    gamePanel.gameState = GameState.PLAY;
                    break;
                default:
                    break;
            }
        } else if (gamePanel.gameState == GameState.DIALOGUE) {
            switch(code) {
                case KeyEvent.VK_ENTER:
                    gamePanel.gameState = GameState.PLAY;
                    break;
                default:
                    break;
            }
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_W:
                upPressed = false;
                break;
            case KeyEvent.VK_S:
                downPressed = false;
                break;
            case KeyEvent.VK_A:
                leftPressed = false;
                break;
            case KeyEvent.VK_D:
                rightPressed = false;
                break;
            case KeyEvent.VK_ENTER:
                enterPressed = false;
            default:
                break;
        }
    }

}
