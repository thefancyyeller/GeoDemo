package Geonauts;

import java.util.ArrayList;

public class GameGrid extends Drawable{
    private int sizeX;
    private int sizeY;
    private final ArrayList<ArrayList<GridSquare>> contents = new ArrayList<>();
    public WorldState state;

    public GameGrid(int sizeX, int sizeY, WorldState state) {
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
        assert(x < sizeX);
        assert(y < sizeY);
        return contents.get(x).get(y);
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }


}
