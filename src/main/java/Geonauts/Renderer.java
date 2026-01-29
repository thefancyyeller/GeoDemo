package Geonauts;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;


public class Renderer {
    // Static UI Components
    private GamePane gamePane;
    private ScrollableTextBox playerInfo;

    private final LevelState level;
    private final CameraState camera;
    public final Canvas target;
    public ArrayList<UIElement> uiElements = new ArrayList<>();

    public Renderer(LevelState level, CameraState camera, Canvas target) {
        this.level = level;
        this.camera = camera;
        this.target = target;
        gamePane = new GamePane(0,0,0,0,this, level, camera);
        playerInfo = new ScrollableTextBox(0,0, 1, 0,0, this);
        uiElements.add(gamePane);
        uiElements.add(playerInfo);
        playerInfo.addLine("This is the first line");
    }

    public void update() {
        this.camera.cameraPos = level.grid.gridSquareWorldPos(level.player.parent);
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
        out.x -= camera.cameraPos.x;
        out.y -= camera.cameraPos.y;
        out = out.scaled(camera.cameraZoom);
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
        out = out.scaled(1/camera.cameraZoom);
        // Undo camera adjustment
        out.x += camera.cameraPos.x;
        out.y += camera.cameraPos.y;
        return out;
    }


}
