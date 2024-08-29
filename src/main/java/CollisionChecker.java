package main.java;

import main.java.entity.Entity;
import main.java.object.SuperObject;

public class CollisionChecker {
    private final GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void resolveTileCollisions(Entity entity) {
        int leftX = entity.worldX + entity.hitbox.x + 1;
        int rightX = entity.worldX + entity.hitbox.x + entity.hitbox.width - 1;
        int topY = entity.worldY + entity.hitbox.y + 1;
        int bottomY = entity.worldY + entity.hitbox.y + entity.hitbox.height - 1;

        int leftCol = leftX / gamePanel.tileSize;
        int rightCol = rightX / gamePanel.tileSize;
        int topRow = topY / gamePanel.tileSize;
        int bottomRow = bottomY / gamePanel.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case ("up"):
                tileNum1 = gamePanel.tileManager.tileMap[topRow][leftCol];
                tileNum2 = gamePanel.tileManager.tileMap[topRow][rightCol];
                if (gamePanel.tileManager.tiles[tileNum1].solid || gamePanel.tileManager.tiles[tileNum2].solid) {
                    entity.worldY = (topRow + 1) * gamePanel.tileSize - entity.hitbox.y;
                }
                break;
            case ("down"):
                tileNum1 = gamePanel.tileManager.tileMap[bottomRow][leftCol];
                tileNum2 = gamePanel.tileManager.tileMap[bottomRow][rightCol];
                if (gamePanel.tileManager.tiles[tileNum1].solid || gamePanel.tileManager.tiles[tileNum2].solid) {
                    entity.worldY = bottomRow * gamePanel.tileSize - entity.hitbox.y - entity.hitbox.height;
                }
                break;
            case ("left"):
                tileNum1 = gamePanel.tileManager.tileMap[topRow][leftCol];
                tileNum2 = gamePanel.tileManager.tileMap[bottomRow][leftCol];
                if (gamePanel.tileManager.tiles[tileNum1].solid || gamePanel.tileManager.tiles[tileNum2].solid) {
                    entity.worldX = (leftCol + 1) * gamePanel.tileSize - entity.hitbox.x;
                }
                break;
            case ("right"):
                tileNum1 = gamePanel.tileManager.tileMap[topRow][rightCol];
                tileNum2 = gamePanel.tileManager.tileMap[bottomRow][rightCol];
                if (gamePanel.tileManager.tiles[tileNum1].solid || gamePanel.tileManager.tiles[tileNum2].solid) {
                    entity.worldX = rightCol * gamePanel.tileSize - entity.hitbox.x - entity.hitbox.width;
                }
                break;
        }
    }

    public int checkObjects(Entity entity, boolean isPlayer) {
        // translate hitbox to world coordinates
        entity.hitbox.x = entity.worldX + entity.hitbox.x;
        entity.hitbox.y = entity.worldY + entity.hitbox.y;

        int collisionObjectIndex = -1;

        for (int i = 0; i < gamePanel.objects.length; i++) {
            SuperObject obj = gamePanel.objects[i];
            if (obj != null) {
                // get object hitbox position
                obj.hitbox.x = obj.worldX + obj.hitbox.x;
                obj.hitbox.y = obj.worldY + obj.hitbox.y;
                boolean collisionDetected = entity.hitbox.intersects(obj.hitbox);

                if (collisionDetected) {
                    switch (entity.direction) {
                        case "up":
                            if (obj.solid) {
                                entity.worldY = obj.hitbox.y + obj.hitbox.height - entity.hitboxDefaultY;
                            }
                            collisionObjectIndex = i;
                            break;
                        case "down":
                            if (obj.solid) {
                                entity.worldY = obj.worldY - entity.hitboxDefaultY - entity.hitbox.height;
                            }
                            collisionObjectIndex = i;
                            break;
                        case "left":
                            if (obj.solid) {
                                entity.worldX = obj.hitbox.x + obj.hitbox.width - entity.hitboxDefaultX;
                            }
                            collisionObjectIndex = i;
                            break;
                        case "right":
                            if (obj.solid) {
                                entity.worldX = obj.hitbox.x - entity.hitboxDefaultX - entity.hitbox.width; 
                            }
                            collisionObjectIndex = i;
                        break;
                    }
                }

                // reset hitbox backt to relative to object
                obj.hitbox.x = obj.hitboxDefaultX;
                obj.hitbox.y = obj.hitboxDefaultY;
            }
        }

        // reset hitbox back to relative to player
        entity.hitbox.x = entity.hitboxDefaultX;
        entity.hitbox.y = entity.hitboxDefaultY;
        return collisionObjectIndex;
    }
}
