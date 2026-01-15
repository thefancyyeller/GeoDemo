import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Renderer {

    private WorldState state;

    public Renderer(WorldState state) {
        this.state = state;
    }

    public void update(Canvas target) {
        GraphicsContext gc = target.getGraphicsContext2D();
        gc.clearRect(0,0, target.getWidth(), target.getHeight());
        for (int gridX = 0; gridX < state.grid.getSizeX(); gridX++) {
            for (int gridY = 0; gridY < state.grid.getSizeY(); gridY++) {

                GridSquare g = state.grid.getSquare(gridX, gridY);

                // World position for this tile
                Vector2F tileWorldPos = new Vector2F(
                        state.tileSize * gridX + state.grid.transform.x,
                        state.tileSize * gridY + state.grid.transform.y
                );

                // Convert to screen pixel coordinates
                Vector2F screenPos = state.worldToCanvas(tileWorldPos);

                // Background tile image
                Image tilePicture = g.background;

                if (tilePicture != null) {
                    gc.drawImage(
                            tilePicture,
                            screenPos.x,
                            screenPos.y,
                            state.tileSize * state.cameraZoom,
                            state.tileSize* state.cameraZoom
                    );
                }
            }
        }
    }

}
