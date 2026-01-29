package Geonauts.Entities;

import Geonauts.Item;
import Geonauts.LevelState;

import java.util.ArrayList;

public class Player extends Entity{
    ArrayList<Item> inventory = new ArrayList<>();
    public Action pendingAction = null;
    public Player(){
        super("Warrior.png", "Geonauts.Player Character");
    }

    @Override
    public boolean onTurn(){
        if(pendingAction == null) return false;
        boolean success = pendingAction.execute();
        pendingAction = null;
        return success;
    }

    public abstract class Action{
        public abstract boolean execute();
    }

    public class MoveAction extends Action{
        LevelState s;
        int newX, newY;

        public MoveAction(LevelState s, int newX, int newY) {
            this.s = s;
            this.newX = newX;
            this.newY = newY;
        }

        @Override
        public boolean execute() {
            return s.grid.move(s.player, newX, newY);
        }
    }
}
