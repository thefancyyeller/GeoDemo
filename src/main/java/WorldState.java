import java.util.ArrayList;

public class WorldState {
    // Camera Vars
    public Vector2F cameraPos = new Vector2F(0,0);
    public int cameraRotation = 0;
    public float cameraZoom = 1f;
    public GameGrid grid = new GameGrid(100,100, this);
    public int tileSize = 25;
    protected ArrayList<Entity> entities = new ArrayList<>();
    public Entity player;
}
