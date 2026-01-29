package Geonauts;

import Geonauts.Entities.Entity;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;

public class GameGrid extends Drawable implements UIElement.GeoKeyLisener {
    private int sizeX;
    private int sizeY;
    private final ArrayList<ArrayList<GridSquare>> contents = new ArrayList<>();
    public LevelState state;

    public GameGrid(int sizeX, int sizeY, LevelState state) {
        super(1,1);
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.state = state;
        for(int i =0; i < sizeX; i++){
            var row = new ArrayList<GridSquare>();
            for(int j = 0; j < sizeY; j++){
                row.add(new GridSquare(i, j, this));
            }
            contents.add(row);
        }
    }

    public GridSquare getSquare(int x, int y){
        if(x < 0 || y < 0 || x >= sizeX || y >= sizeY)
            return null;
        return contents.get(x).get(y);
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public boolean move(Entity entity, int dx, int dy){
        var coords = entity.parent.getCoords();
        var target = getSquare(coords.get(0) + dx, coords.get(1) + dy);
        if(target == null)
            return false;
        if(target.canEnter(entity)){
            entity.parent.removeChild(entity);
            target.addChild(entity);
            return true;
        }
        return false;
    }


    @Override
    public void onKeyPress(KeyCode keyCode) {
        switch(keyCode){
            case KeyCode.W:

        }
    }

    public Vector2F gridSquareWorldPos(int squareX, int squareY){
        var out = new Vector2F(squareX, squareY);
        return out.scaled(state.tileSize);
    }
    public Vector2F gridSquareWorldPos(GridSquare g){
        return gridSquareWorldPos(g.x, g.y);
    }
}
