package RestaurantClasses.ServiceTools;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import RestaurantClasses.Service.Waiter;

/**
 * This class represents an order that the waiter requests, the kitchen fulfills, and the waiter then serves.
 */
public class Order implements Serializable {

    /**
     * Each order has an associated Waiter.
     */
    private Waiter waiter;

    /**
     * Each order has an associated table, represented by an integer.
     */
    private int table;

    /**
     * Each order has items to be delivered to the consumers.
     */
    private List<OrderItem> items;

    /**
     * Each order has an amount of each item.
     */
    private List<Integer> amount;

    /**
     * Each order has a time when it is created.
     */
    private LocalDateTime issued;

    /**
     * Each order has a time when it is fulfilled (ready to be picked up from the kitchen/bar).
     */
    private LocalDateTime fulfilled;

    /**
     * Each order has a time when it is served.
     */
    private LocalDateTime served;

    /**
     * Every order has optional prepComments, i.e "make x menuItem extra spicy or mild"
     */
    private String prepComments;

    /**
     * Every order can be complained about.
     */
    private String complaint;

    /**
     * The entire order has a total price.
     */
    private double totalPrice;

    /**
     * Constructing our order. The time is initialized when the order is created.
     * @param waiter Waiter issuing the order.
     * @param table Table which requests the order.
     */
    public Order(Waiter waiter, int table, List<OrderItem> order, List<Integer> amount){
       this.waiter=waiter;
       this.table=table;
       //comment is null unless specified
       prepComments=null;

       //Initializing our ArrayList of items
        items=order;

        this.amount=amount;

        //initializing our construction time
       issued= LocalDateTime.now();
       fulfilled = null;
       served=null;

       //complaint is null unless set by the waiter
       complaint=null;

       double sum = 0;
       for (int i=0;i<order.size();i++){
           sum+= ((order.get(i).getTotalPrice())*amount.get(i));
       }
    }



    /**
     * Method to set a complaint for this order.
     * @param description This is the nature of the complaint.
     */
    public void makeComplaint(String description){
       this.complaint=description;
    }

    public String toString(){
        //basically our info is about the order on the current day
        //the other info about the day it was issued, etc is only on export data option (for convenience)
        //our String will be
        //table : ## -- waiter: ##

        String toReturn ="table : "+this.table + " -- waiter: "+ this.waiter.getName();

        if (fulfilled==null){
            //then not fulfilled or served
            return toReturn+"-- NOT FULFILLED";
        } else if (served ==null){
            //then not served but fulfilled
            return toReturn+"-- FULFILLED";
        } else {
            //then served and fulfilled
            return toReturn+"-- SERVED";
        }
    }







    // getters and setters
    public Waiter getWaiter() {
        return waiter;
    }

    public String getComplaint(){
        return complaint;
    }

    public void setWaiter(Waiter waiter) {
        this.waiter = waiter;
    }

    public int getTable() {
        return table;
    }

    public void setTable(int table) {
        this.table = table;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public LocalDateTime getIssued() {
        return issued;
    }

    public void setIssued(LocalDateTime issued) {
        this.issued = issued;
    }

    public LocalDateTime getFulfilled() {
        return fulfilled;
    }

    public void setFulfilled(LocalDateTime fulfilled) {
        this.fulfilled = fulfilled;
    }

    public LocalDateTime getServed() {
        return served;
    }

    public void setServed(LocalDateTime served) {
        this.served = served;
    }

    public List<Integer> getAmount() {
        return amount;
    }

    public void setAmount(List<Integer> amount) {
        this.amount = amount;
    }

    public String getPrepComments() {
        return prepComments;
    }

    public void setPrepComments(String prepComments) {
        this.prepComments = prepComments;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}