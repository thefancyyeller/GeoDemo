package Geonauts;

import Geonauts.Entities.Entity;
import Geonauts.Entities.Player;

import java.util.ArrayList;

public class LevelState {
    public GameGrid grid = new GameGrid(100, 100, this);
    public int tileSize = 25;
    protected ArrayList<Entity> entities = new ArrayList<>();
    public Player player;

    public void tick() {
        if (player.pendingAction == null)
            return;
        boolean success = player.pendingAction.execute();
        player.pendingAction = null;
        if (!success)
            return;

        for (var e : entities) {
            e.gainEnergy();
        }
    }
}
