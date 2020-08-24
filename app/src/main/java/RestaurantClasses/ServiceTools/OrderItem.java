package RestaurantClasses.ServiceTools;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an item with variants and additions that is actually ordered by a customer.
 */
public class OrderItem {

   /**
    * Each OrderItem has a name
    */
   private String name;


   /**
    * Each OrderItem has a variant that is chosen.
    */
   private String variantChosen;

   /**
    * The price of the variant that was chosen.
    */
   private double variantPrice;

   /**
    * The additions that were chosen by the customer. If no additions were made, this is empty.
    */
   private List<String> additionsChosen;

   /**
    * The prices to match the additions chosen by the customer. If no additions, then this is empty.
    */
   private List<Double> additionPrices;

   /**
    * The total price of this ordered item (without tax). variantPrice + additionPrices.
    */
   private double totalPrice;

   //private constructor because we can only create an order item from MenuItems
   public OrderItem(String name, String variantChosen, double variantPrice) {
     this.name=name;
     this.variantPrice=variantPrice;
     this.variantChosen=variantChosen;
     additionPrices=new ArrayList<Double>();
     additionsChosen = new ArrayList<String>();

     //setting initial total price;
       totalPrice=variantPrice;
   }

   //methods to add additions

    /**
     * We can add additions to an ordered Item.
     * @param addition This is the name of the addition ordered. I.E extra cheese/ toppings.
     * @param price This is the price of the addition that was ordered.
     */
   public void addAddition(String addition, double price){
      additionsChosen.add(addition);
      additionPrices.add(price);
      totalPrice+=price;
   }

   @Override
    public String toString(){
       return name+" : "+variantChosen;
   }

   //overriding equals
   @Override
   public boolean equals(Object o){
       if (o instanceof OrderItem){
           OrderItem toCompare = (OrderItem) o;
           if (toCompare.name.equals(this.name) && toCompare.variantChosen.equals(this.variantChosen)
           && toCompare.additionsChosen.equals(this.additionsChosen)){
               //then these should be the same itemOrdered
               return true;
           } else {
               return false;
           }
       } else {
           return false;
       }
   }

   //getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVariantChosen() {
        return variantChosen;
    }

    public void setVariantChosen(String variantChosen) {
        this.variantChosen = variantChosen;
    }

    public double getVariantPrice() {
        return variantPrice;
    }

    public void setVariantPrice(double variantPrice) {
        this.variantPrice = variantPrice;
    }

    public List<String> getAdditionsChosen() {
        return additionsChosen;
    }

    public void setAdditionsChosen(List<String> additionsChosen) {
        this.additionsChosen = additionsChosen;
    }

    public List<Double> getAdditionPrices() {
        return additionPrices;
    }

    public void setAdditionPrices(List<Double> additionPrices) {
        this.additionPrices = additionPrices;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
