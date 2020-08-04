package Service;

import ServiceTools.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * This class represents the waiter who services the restaurant. They will be handling requests made by customers and updated on
 * requests straight from the kitchen/console.
 */
public class Waiter{
    /**
     * Name or Identification of the Waiter.
     */
    private String name;

    /**
     * Orders that have been requested to be fulfilled from the kitchen/bar.
     */
    private ArrayList<Order> requestedOrders;

    /**
     * Orders that have been served by this waiter.
     */
    private ArrayList<Order> servedOrders;

    /**
     *  Constructor for waiter.
      * @param name Name of the waiter we wish to construct.
     */
    public Waiter(String name){
        this.name=name;
        requestedOrders=new ArrayList<Order>();
        servedOrders=new ArrayList<Order>();
    }

    /**
     * Method allows a waiter to place an order that the customer has given.
     * @param o This represents the order.
     * @param k This represents the kitchen/bar that is to fulfill the order.
     * @return Returns true if order is successfully sent to the kitchen, false otherwise.
     */
    public boolean requestOrder(Order o, Kitchen k){
        k.getOrders().add(o);
        return true;
    }

    /**
     * Method that notifies the system that an order has been served by the waiter.
      * @param o This is the order being served.
     * @return Returns true if the served status is successfully communicated to the console. False otherwise.
     */
    public boolean serveOrder(Order o){
        o.setServed(LocalDateTime.now());

        //removing the order from requested to served
        requestedOrders.remove(o);

        //adding the order to served orders
        servedOrders.add(o);


       //for compilation
        return true;
    }

    /**
     * Method for a waiter to send a quick question to the kitchen/bar.
     * @param k This is the kitchen/bar to send the question to.
     * @param s This is the question to ask.
     * @return Returns true if the message was successfully sent. False otherwise.
     */
    public boolean askInquiry(Kitchen k, String s){
       k.getInquiries().add(s);

       //for compilation
        return true;
    }


    /**
     * Allows the waiter to file a complaint on behalf of the customer.
     * @param o This is the order to turn into a complaint.
     * @param description This is the nature of the complaint, as explained by the customer.
     * @return Returns true if it was successfully logged in the system to the console and kitchen, false otherwise.
     */
    public boolean issueComplaint(Order o,String description){
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

    public ArrayList<Order> getRequestedOrders() {
        return requestedOrders;
    }

    public void setRequestedOrders(ArrayList<Order> requestedOrders) {
        this.requestedOrders = requestedOrders;
    }

    public ArrayList<Order> getServedOrders() {
        return servedOrders;
    }

    public void setServedOrders(ArrayList<Order> servedOrders) {
        this.servedOrders = servedOrders;
    }
}