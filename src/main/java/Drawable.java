import java.awt.image.BufferedImage;

public class Drawable{
    public Vector2F transform = new Vector2F(0,0);
    private BufferedImage sprite;
    private String spritePath;

    public Drawable(float x, float y){
        this.transform.x = x;
        this.transform.y = y;
    }
}
