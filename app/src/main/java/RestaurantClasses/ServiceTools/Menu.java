package ServiceTools;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that represents our menu in the restaurant.
 */
public class Menu{


    /**
     * A menu is just an arrangement of categories and items which we call MenuItems.
     */
    private HashMap<String, ArrayList<MenuItem>> menu;

    /**
     * Initializing an empty menu.
     */
    public Menu(){
       menu=new HashMap<String, ArrayList<MenuItem>>();
    }

    public boolean addItem(){
        //compilation
        return true;
    }

    public boolean removeCategory(){
        //compilation
        return true;
    }

    public boolean removeItem(){
       //compilation
        return true;
    }


    // getters and setters

    public HashMap<String, ArrayList<MenuItem>> getMenu() {
        return menu;
    }

    public void setMenu(HashMap<String, ArrayList<MenuItem>> menu) {
        this.menu = menu;
    }


}