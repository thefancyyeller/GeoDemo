package Geonauts;

import javafx.scene.image.Image;

public class Item extends GridItem{
    public String name;
    public Item(String name, String spritePath) {
        this.name = name;
        this.sprite = new Image(spritePath);
    }
}
