package Geonauts;

import javafx.scene.canvas.Canvas;

import java.util.ArrayList;
import java.util.Comparator;

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
        // Dispatch OnClicks to appropriate UI Components
        canvas.setOnMouseClicked(e->{
            var clickLoc = new Vector2I((int) e.getX(), (int) e.getY());
            var elms = getAtLocation(clickLoc);
            if(elms.isEmpty())
                System.out.println("Warning! No ui components present!");
            for(var elm : elms) {
                if(elm instanceof UIElement.GeoClickListener) {
                    // Transform to relative position
                    clickLoc.x -= elm.x;
                    clickLoc.y -= elm.y;
                    elm.onClick(clickLoc);
                }
            }
        });
    }

    // Returns the first (highest layered) ui component at a target
    private ArrayList<UIElement> getAtLocation(Vector2I canvasLocation){
        renderer.uiElements.sort(new Comparator<UIElement>() { // Sort by layer
            @Override
            public int compare(UIElement o1, UIElement o2) {
                return o1.layer - o2.layer;
            }
        });
        var out = new ArrayList<UIElement>();
        for(var elm : renderer.uiElements){
            if(canvasLocation.x >= (elm.x) && canvasLocation.x <= (elm.x + elm.width)){
                if(canvasLocation.y >= (elm.y) && canvasLocation.y <= (elm.y + elm.height)){
                    out.add(elm);
                }
            }
        }
        return out;
    }
}
