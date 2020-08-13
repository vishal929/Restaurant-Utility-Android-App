package RestaurantClasses.ServiceTools;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class represents an item entry under a category in our Menu class.
 */
public class MenuItem{
    /**
     * Each MenuItem has a name.
     */
    private String name;



    /**
     * Different variants of the item. The base variant must be included and cannot be deleted.
     */
    private List<String> variants;

    /**
     * The prices for each variant of this menuItem. Variants are considered big changes to a meal style, such as vegetarian versions, 4 piece vs 5 piece etc.
     */
    private List<Double> variantPrices;

    /**
     * Additions are items which add to the meal, but are considered separate. I.E extra cheese, jalapeno toppings, etc.
     */
    private List<String> additions;

    /**
     * Prices for the additions.
     */
    private List<Double> additionPrices;

    /**
     * Each MenuItem has a description for the item which is optional.
     */
    private String description;




    /**
     * Constructor for the menuItem. Initializes description to null and prices to an empty hashmap.
      * @param name This is the name of the item to add to the menu as a MenuItem.
     * @param basePrice This is the base price of the item. The user can then choose to offer variations with different pricing (i.e large,extra large, etc.)
     */
    public MenuItem(String name,double basePrice){
        this.name=name;
        variants=new ArrayList<String>();
        variantPrices=new ArrayList<Double>();
        additions=new ArrayList<String>();
        additionPrices = new ArrayList<Double>();
        variants.add("Base");
        variantPrices.add(basePrice);
        description=null;
    }


    @Override
    public String toString(){
        return name;
    }

    public boolean addVariant(String name, double price){
        name=name.toLowerCase().trim();
        for (String var:variants){
            if (var.equals(name)){
                return false;
            }
        }
        variants.add(name);
        variantPrices.add(price);
        return true;
    }

    public boolean removeVariant(int pos){
        variants.remove(pos);
        variantPrices.remove(pos);
        return true;
    }

    public boolean addAdditional(String name, double price){
        name=name.toLowerCase().trim();
        for (String add: additions){
            if (add.equals(name)){
                return false;
            }
        }
        additions.add(name);
        additionPrices.add(price);
        return true;
    }

    public boolean removeAdditional(int pos){
        additions.remove(pos);
        additionPrices.remove(pos);
        return true;
    }

    //getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getVariants() {
        return variants;
    }

    public void setVariants(List<String> variants) {
        this.variants = variants;
    }

    public List<Double> getVariantPrices() {
        return variantPrices;
    }

    public void setVariantPrices(List<Double> variantPrices) {
        this.variantPrices = variantPrices;
    }

    public List<String> getAdditions() {
        return additions;
    }

    public void setAdditions(List<String> additions) {
        this.additions = additions;
    }

    public List<Double> getAdditionPrices() {
        return additionPrices;
    }

    public void setAdditionPrices(List<Double> additionPrices) {
        this.additionPrices = additionPrices;
    }
}