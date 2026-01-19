import javafx.scene.canvas.Canvas;

import java.util.ArrayList;

public class InputHandler {
    private Canvas canvas;
    private Vector2I mouseLoc = new Vector2I(0,0);
    private boolean mouseInCanvas = false;
    Renderer renderer;
    public InputHandler(Renderer r) {
        this.renderer = r;
        this.canvas = r.target;
        // Mouse Tracker
        canvas.setOnMouseEntered(e-> mouseInCanvas = true);
        canvas.setOnMouseExited(e-> mouseInCanvas = false);
        canvas.setOnMouseMoved(e->{
            mouseLoc.x = (int) e.getX();
            mouseLoc.y = (int) e.getY();
        });
    }

    private UIElement getAtLocation(Vector2I canvasLocation){
        for(var elm : renderer.uiElements){
            if(canvasLocation.x >= (elm.x) && canvasLocation.x <= (elm.x + elm.width)){
                if(canvasLocation.y >= (elm.y) && canvasLocation.y <= (elm.y + elm.height)){
                    return elm;
                }
            }
        }
        throw(new IllegalStateException("No element at location"));
    }
}
