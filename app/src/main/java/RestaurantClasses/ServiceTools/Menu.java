package RestaurantClasses.ServiceTools;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Class that represents our menu in the restaurant.
 */
public class Menu implements Serializable {


    /**
     * A menu is just an arrangement of categories and items which we call MenuItems.
     */
    private List<MenuCategory> menu;


    /**
     * Initializing an empty menu.
     */
    public Menu(){
       menu= new ArrayList<MenuCategory>();

    }



    public boolean addCategory(String category){
        category=category.toLowerCase().trim();
        //we want to avoid repeated categories
        for (MenuCategory cat:menu){
            if (cat.getName().equals(category)){
                return false;
            }
        }
        //then we have no problem adding this
        MenuCategory toAdd = new MenuCategory(category);
        menu.add(toAdd);
        return true;
    }

    public boolean removeCategory(int categoryPos){
        //based on our implementation, we can only remove categories from the GUI which the user can see.
        //therefore, there will be no problems where a user is trying to remove a category which doesnt exist.
        menu.remove(categoryPos);
        //compilation
        return true;
    }




    // getters and setters

    public List<MenuCategory> getMenu() {
        return menu;
    }

    public void setMenu(List<MenuCategory> menu) {
        this.menu = menu;
    }


}