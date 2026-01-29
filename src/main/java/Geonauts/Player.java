package Geonauts;

public class Player extends Entity{
    public Action pendingAction = null;
    public Player(){
        super("Warrior.png", "Geonauts.Player Character");
    }

    public abstract class Action{
        public abstract boolean execute();
    }
}
