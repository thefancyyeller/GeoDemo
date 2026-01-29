package Geonauts;

import javafx.scene.image.Image;

import java.util.*;

public class GridSquare {
    public Image background = new Image("./Path.png");
    private final GameGrid parent;
    public final ArrayList<GridItem> children = new ArrayList<>();
    public final ArrayList<Tag> tags = new ArrayList<>();

    private int x;
    private int y;

    public GridSquare(int x, int y, GameGrid parent) {
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.tags.add(Tag.Walkable);
    }

    public enum Tag{
        Walkable,
        Room,
    }

    public List<Integer> getCoords(){
        return Arrays.asList(x, y);
    }

    public void addChild(GridItem item){
        item.parent = this;
        children.add(item);
        if(item instanceof Entity)
            parent.state.entities.add((Entity)item);
    }

    public void removeChild(GridItem item){
        item.parent = null;
        assert(this.children.remove(item));// Make sure the item was in this square to begin with.
        if(item instanceof Entity)
            assert(this.parent.state.entities.add((Entity) item));
    }

    public Vector2F gameWorldCoords(){
        var out = this.parent.transform.clone();
        out.x += this.x * parent.state.tileSize;
        out.y += this.y * parent.state.tileSize;
        return out;
    }

    public boolean canEnter(Entity e){
        // Only one entity per square
        for(var c : this.children){
            if(c instanceof Entity)
                return false;
        }
        return this.tags.contains(Tag.Walkable);
    }
}
