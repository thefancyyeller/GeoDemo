import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GridSquare {
    public Image background = new Image("./Path.png");
    public ArrayList<GridItem> children;
    public ArrayList<Tag> tags;

    private int x;
    private int y;

    public GridSquare(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public enum Tag{
        Walkable,
        Room,
    }

    public List<Integer> getCoords(){
        return Arrays.asList(x, y);
    }
}
