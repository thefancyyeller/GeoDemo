package Geonauts;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;


public class Renderer {
    // Static UI Components
    private GamePane gamePane;
    private ScrollableTextBox playerInfo;

    private final WorldState state;
    public final Canvas target;
    public ArrayList<UIElement> uiElements = new ArrayList<>();

    public Renderer(WorldState state, Canvas target) {
        this.state = state;
        this.target = target;
        gamePane = new GamePane(0,0,0,0,this, state);
        playerInfo = new ScrollableTextBox(0,0, 1, 0,0, this);
        uiElements.add(gamePane);
        uiElements.add(playerInfo);
        playerInfo.addLine("This is the first line");
    }

    public void update() {
        GraphicsContext gc = target.getGraphicsContext2D();
        gc.clearRect(0,0, target.getWidth(), target.getHeight());
        // Resize/adjust the UI
        gamePane.height = Math.floorDiv((int) target.getHeight(), 3) * 2;
        gamePane.width = Math.floorDiv((int) target.getWidth(), 3) * 2;
        playerInfo.x = gamePane.width;
        playerInfo.y = 0;
        playerInfo.width = Math.floorDiv((int) target.getWidth(), 3);
        playerInfo.height = (int) target.getHeight();

        // Render the UI
        uiElements.sort((a, b) -> a.layer - b.layer);
        for (UIElement elm : uiElements) {
            elm.render(gc);
        }
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
