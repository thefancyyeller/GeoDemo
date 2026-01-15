public class WorldState {
    // Camera Vars
    public Vector2F cameraPos = new Vector2F(0,0);
    public int cameraRotation = 0;
    public float cameraZoom = 1f;
    public GameGrid grid = new GameGrid(100,100);
    public int tileSize = 25;

    public Vector2F worldToCanvas(Vector2F worldVec){
        var out = worldVec.clone();
        out.x -= cameraPos.x;
        out.y -= cameraPos.y;
        out = out.scaled(cameraZoom);
        return out;
    }
}
