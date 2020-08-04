package Service;
import ServiceTools.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
/**
 * Class to represent a kitchen or bar where the waiter makes orders or requests from.
 */
public class Kitchen{
    /**
     * Name or identification of a kitchen or bar.
     */
    private String name;

    /**
     * Orders that have been requested for this kitchen to fulfill.
     */
    private ArrayList<Order> orders ;

    /**
     * Orders that have been fulfilled by this kitchen.
     */
    private ArrayList<Order> fulfilled ;

    /**
     * Inquiries that have been sent to the kitchen/bar.
     */
    private ArrayList<String> inquiries;

    /**
     * The Kitchen/Bar has a copy of the menu.
     */
    private Menu menu;


    /**
     * Constructor for our kitchen/bar.
     * @param name The name of our kitchen/bar.
     */
    public Kitchen(String name){
       this.name=name;
       orders=new ArrayList<Order>();
       fulfilled=new ArrayList<Order>();
       inquiries = new ArrayList<String>();
    }


    /**
     * When a kitchen is done making the order. This method contacts the waiter for pickup.
     * The fulfilled time of the order is given at this step.
     * @param o This is the order, which is supposed to be picked up.
     * @return Returns a boolean to determine if the waiter is sent the message or not.
     */
    public boolean requestPickup(Order o){
        //setting the time the order was fulfilled
        o.setFulfilled(LocalDateTime.now());

        //for the actual app, will put boolean logic depending on the network communication here

        //true for compilation
        return true;
    }

    /**
     * The method sends a response to some inquiry given by a waiter.
     * @param s Represents the response
     * @return Returns a boolean if the response was successfully sent.
     */
    public boolean respondInquiry(String s){
        //will fill out/figure out more of this logic when working thru android studio.

        //for compilation
        return true;
    }




    //getters and setters


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public ArrayList<Order> getFulfilled() {
        return fulfilled;
    }

    public void setFulfilled(ArrayList<Order> fulfilled) {
        this.fulfilled = fulfilled;
    }

    public ArrayList<String> getInquiries() {
        return inquiries;
    }

    public void setInquiries(ArrayList<String> inquiries) {
        this.inquiries = inquiries;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}