import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GridSquare {
    public BufferedImage background = ImageLoader.loadImage("./Path.png");
    public ArrayList<GridItem> children;
    public ArrayList<Tag> tags;

    public enum Tag{
        Walkable,
        Room,
    }
}
