package ServiceTools;

import java.awt.*;
import java.util.HashMap;

/**
 * This class represents an item entry under a category in our Menu class.
 */
public class MenuItem{
    /**
     * Each MenuItem has a name.
     */
    private String name;

    /**
     * Each MenuItem has a selection of prices depending on preparation. I.E higher price for extra cheese or toppings on pizza.
     * If there is only one available selection, then the string associated with the price is null.
     */
    private HashMap<String,Double> prices;

    /**
     * Each MenuItem has a description for the customer to read.
     */
    private String description;


    /**
     * Each MenuItem has an image.
     */
    private Image picture;

    /**
     * Constructor for the menuItem. Initializes description to null and prices to an empty hashmap.
      * @param name This is the name of the item to add to the menu as a MenuItem.
     */
    public MenuItem(String name){
        this.name=name;
        prices=new HashMap<String,Double>();
        description=null;
    }




    //getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Double> getPrices() {
        return prices;
    }

    public void setPrices(HashMap<String, Double> prices) {
        this.prices = prices;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Image getPicture() {
        return picture;
    }

    public void setPicture(Image picture) {
        this.picture = picture;
    }
}