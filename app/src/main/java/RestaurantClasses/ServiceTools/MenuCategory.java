package RestaurantClasses.ServiceTools;

import java.util.ArrayList;

/**
 * Class to symbolize a category in a menu
 */
public class MenuCategory {

    /**
     * A category has a name.
     */
    private String name;


    /**
     * A category has a list of items which fall under it.
     */
    private ArrayList<MenuItem> items;

    public MenuCategory(String name){
       this.name=name;
       items = new ArrayList<MenuItem>();
    }

    public boolean addItem(String name, double basePrice){
       name=name.toLowerCase();
       //checking if this item already exists
        for (int i=0;i<items.size();i++){
            if (items.get(i).getName().equals(name)){
                return false;
            }
        }
       MenuItem toAdd = new MenuItem(name,basePrice);
       items.add(toAdd);
       return true;

    }

    public boolean removeItem(int itemPos){
        //easy to remove and check removal because no errors are guarunteed by our GUI
        items.remove(itemPos);
        return true;
    }

    @Override
    public String toString(){
        return name;
    }


    //getter and setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<MenuItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<MenuItem> items) {
        this.items = items;
    }
}
