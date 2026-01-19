import javafx.scene.canvas.Canvas;

import javax.swing.*;
import java.util.ArrayList;

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
}
