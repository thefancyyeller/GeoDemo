import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;


public class Renderer {
    // Static UI Components
    private GamePane gamePane;

    private final WorldState state;
    public final Canvas target;
    public ArrayList<UIElement> uiElements = new ArrayList<>();

    public Renderer(WorldState state, Canvas target) {
        this.state = state;
        this.target = target;
        gamePane = new GamePane(0,0,0,0,this);
    }

    public void update() {
        GraphicsContext gc = target.getGraphicsContext2D();
        gc.clearRect(0,0, target.getWidth(), target.getHeight());
        // Resize/adjust the UI

        // Render the game
        for (int gridX = 0; gridX < state.grid.getSizeX(); gridX++) {
            for (int gridY = 0; gridY < state.grid.getSizeY(); gridY++) {

                GridSquare g = state.grid.getSquare(gridX, gridY);

                // World position for this tile
                Vector2F tileWorldPos = new Vector2F(
                        state.tileSize * gridX + state.grid.transform.x,
                        state.tileSize * gridY + state.grid.transform.y
                );

                // Convert to screen pixel coordinates
                Vector2F screenPos = this.worldToCanvas(tileWorldPos);

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
                // Render all children of the tile
                for(var child : g.children){
                    if(child.sprite != null){
                        gc.drawImage(
                                child.sprite,
                                screenPos.x,
                                screenPos.y,
                                state.tileSize * state.cameraZoom,
                                state.tileSize* state.cameraZoom
                        );
                    }
                }
            }
        }
        // Render the UI
    }

    public Vector2F worldToCanvas(Vector2F worldCoord){
        var out = worldCoord.clone();
        out.x -= state.cameraPos.x;
        out.y -= state.cameraPos.y;
        out = out.scaled(state.cameraZoom);
        // Adjust to mid screen
        out.x += ((float) target.getWidth())/2;
        out.y += ((float) target.getHeight())/2;
        return out;
    }


    public Vector2F canvasToWorld(Vector2F canvasCoord) {
        var out = canvasCoord.clone();
        // Undo camera centering & zoom
        out.x -= ((float) target.getWidth()) / 2f;
        out.y -= ((float) target.getHeight()) / 2f;
        out = out.scaled(1/state.cameraZoom);
        // Undo camera adjustment
        out.x += state.cameraPos.x;
        out.y += state.cameraPos.y;
        return out;
    }


}
