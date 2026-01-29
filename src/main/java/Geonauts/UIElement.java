package Geonauts;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.awt.event.KeyEvent;

public abstract class UIElement {
    public int x,y,layer,height,width;
    Renderer parent;

    public UIElement(int x, int y, int layer, int height, int width, Renderer parent) {
        this.x = x;
        this.y = y;
        this.layer = layer;
        this.height = height;
        this.width = width;
        this.parent = parent;
    }

    public void onClick(Vector2I relativeClickLocation){
        return;
    }

    public void render(GraphicsContext gc){
        // No-op by default; subclasses override to draw themselves.
    }

    public interface GeoScrollListener{
        public abstract void onScroll(double deltaY);
    }
    public interface GeoClickListener{
        public abstract void onClick(Vector2I relPixelCoords);
    }
    public interface GeoKeyLisener{
        public abstract void onKeyPress(KeyCode keyCode); // Called when key is physically pressed
    }
}
