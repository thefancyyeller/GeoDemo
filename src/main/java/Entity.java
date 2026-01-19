import javafx.scene.image.Image;

import java.util.ArrayList;

public class Entity extends GridItem {
    public int health;
    public int range = 1;
    public final ArrayList<Tag> tags = new ArrayList<>();
    public int currentEnergy = 0;
    public int energyForTurn = 50;

    public Entity(String spritePath){
        super();
        this.sprite = new Image(spritePath);
    }

    public void gainEnergy(){
        currentEnergy++;
        if(energyForTurn == currentEnergy){
            currentEnergy = 0;
            onTurn();
        }
    }

    public void onTurn(){
        return;
    }

    public enum Tag{

    }

}
