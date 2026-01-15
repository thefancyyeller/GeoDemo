import javafx.scene.image.Image;


public class Drawable{
    public Vector2F transform = new Vector2F(0,0);
    private Image sprite;
    private String spritePath;

    public Drawable(float x, float y){
        this.transform.x = x;
        this.transform.y = y;
    }
}
