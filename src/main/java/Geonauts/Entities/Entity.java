package Geonauts.Entities;

import Geonauts.GridItem;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Entity extends GridItem {
    public int health;
    public int range = 1;
    public final ArrayList<Tag> tags = new ArrayList<>();
    public int currentEnergy = 0;
    public int energyForTurn = 50;
    public String name;

    public Entity(String spritePath, String name){
        super();
        this.sprite = new Image(spritePath);
    }

    public void gainEnergy(){
        currentEnergy++;
        if(currentEnergy >= energyForTurn){
            if(onTurn()){
                currentEnergy = 0;
            }
        }
    }

    public boolean onTurn(){
        return true;
    }

    public enum Tag{

    }

}
