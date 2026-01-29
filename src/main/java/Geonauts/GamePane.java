package Geonauts;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

// This is the UI element associated with the actual gameplay
public class GamePane extends UIElement{

    private final WorldState state;

    public GamePane(int x, int y, int height, int width, Renderer parent, WorldState state) {
        super(x, y, 0, height, width, parent);
        this.state = state;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.save();

        // Clip to pane bounds
        gc.beginPath();
        gc.rect(x, y, width, height);
        gc.clip();

        // Render the game grid
        for (int gridX = 0; gridX < state.grid.getSizeX(); gridX++) {
            for (int gridY = 0; gridY < state.grid.getSizeY(); gridY++) {

                GridSquare g = state.grid.getSquare(gridX, gridY);

                // World position for this tile
                Vector2F tileWorldPos = new Vector2F(
                        state.tileSize * gridX + state.grid.transform.x,
                        state.tileSize * gridY + state.grid.transform.y
                );

                // Convert to pane-local screen coordinates
                Vector2F screenPos = worldToPane(tileWorldPos);

                // Background tile image
                Image tilePicture = g.background;

                if (tilePicture != null) {
                    gc.drawImage(
                            tilePicture,
                            screenPos.x,
                            screenPos.y,
                            state.tileSize * state.cameraZoom,
                            state.tileSize * state.cameraZoom
                    );
                }
                // Render all children of the tile
                for (GridItem child : g.children) {
                    if (child.sprite != null) {
                        gc.drawImage(
                                child.sprite,
                                screenPos.x,
                                screenPos.y,
                                state.tileSize * state.cameraZoom,
                                state.tileSize * state.cameraZoom
                        );
                    }
                }
            }
        }

        gc.restore();
    }

    public Vector2F paneToWorld(Vector2F relCoord) {
        var out = relCoord.clone();
        // Undo pane center offset (relative, so no need to subtract x/y)
        out.x -= width / 2f;
        out.y -= height / 2f;
        // Undo zoom
        out = out.scaled(1 / state.cameraZoom);
        // Undo camera offset
        out.x += state.cameraPos.x;
        out.y += state.cameraPos.y;
        return out;
    }

    private Vector2F worldToPane(Vector2F worldCoord) {
        var out = worldCoord.clone();
        out.x -= state.cameraPos.x;
        out.y -= state.cameraPos.y;
        out = out.scaled(state.cameraZoom);
        // Offset to pane center instead of canvas center
        out.x += x + width / 2f;
        out.y += y + height / 2f;
        return out;
    }
}
