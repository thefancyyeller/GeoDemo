package Geonauts;

import Geonauts.Entities.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

// This is the UI element associated with the actual gameplay
public class GamePane extends UIElement implements UIElement.GeoKeyLisener {

    private final LevelState level;
    private final CameraState camera;

    public GamePane(int x, int y, int height, int width, Renderer parent, LevelState level, CameraState camera) {
        super(x, y, 0, height, width, parent);
        this.level = level;
        this.camera = camera;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.save();

        // Clip to pane bounds
        gc.beginPath();
        gc.rect(x, y, width, height);
        gc.clip();

        // Render the game grid
        for (int gridX = 0; gridX < level.grid.getSizeX(); gridX++) {
            for (int gridY = 0; gridY < level.grid.getSizeY(); gridY++) {

                GridSquare g = level.grid.getSquare(gridX, gridY);

                // World position for this tile
                Vector2F tileWorldPos = new Vector2F(
                        level.tileSize * gridX + level.grid.transform.x,
                        level.tileSize * gridY + level.grid.transform.y
                );

                // Convert to pane-local screen coordinates
                Vector2F screenPos = worldToPane(tileWorldPos);

                // Background tile image
                Image tilePicture = g.background;

                float tileScreenSize = level.tileSize * camera.cameraZoom;
                if (tilePicture != null) {
                    gc.drawImage(
                            tilePicture,
                            screenPos.x,
                            screenPos.y,
                            tileScreenSize,
                            tileScreenSize
                    );
                }
                // Render all children of the tile
                for (GridItem child : g.children) {
                    if (child.sprite != null) {
                        gc.drawImage(
                                child.sprite,
                                screenPos.x,
                                screenPos.y,
                                tileScreenSize,
                                tileScreenSize
                        );
                    }
                }
            }
        }

        gc.restore();
    }

    @Override
    public void onKeyPress(KeyCode keyCode) {
        Player player = level.player;
        int dx = 0, dy = 0;
        switch (keyCode) {
            case W: dy = -1; break;
            case S: dy = 1;  break;
            case A: dx = -1; break;
            case D: dx = 1;  break;
            default: return;
        }
        player.pendingAction = player.new MoveAction(level, dx, dy);
    }

    public Vector2F paneToWorld(Vector2F relCoord) {
        var out = relCoord.clone();
        // Undo pane center offset (relative, so no need to subtract x/y)
        out.x -= width / 2f;
        out.y -= height / 2f;
        // Undo zoom
        out = out.scaled(1 / camera.cameraZoom);
        // Undo camera offset
        out.x += camera.cameraPos.x;
        out.y += camera.cameraPos.y;
        return out;
    }

    private Vector2F worldToPane(Vector2F worldCoord) {
        var out = worldCoord.clone();
        out.x -= camera.cameraPos.x;
        out.y -= camera.cameraPos.y;
        out = out.scaled(camera.cameraZoom);
        // Offset to pane center instead of canvas center
        out.x += x + width / 2f;
        out.y += y + height / 2f;
        return out;
    }
}
