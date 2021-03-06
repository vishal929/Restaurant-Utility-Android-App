package RestaurantClasses.Service;

import RestaurantClasses.ServiceTools.Inquiry;
import RestaurantClasses.ServiceTools.Order;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the waiter who services the restaurant. They will be handling requests made by customers and updated on
 * requests straight from the kitchen/console.
 */
public class Waiter implements Serializable {
    /**
     * Name or Identification of the Waiter.
     */
    private String name;

    /**
     * Orders that have been requested to be fulfilled from the kitchen/bar.
     */
    private List<Order> requestedOrders;

    /**
     * Orders that have been served by this waiter.
     */
    private List<Order> servedOrders;

    /**
     * Orders that have been placed by the waiter to the kitchen
     */
    private List<Order> placedOrders;

    /**
     * Waiters can make inquiries to the kitchen.
     */
    private List<Inquiry> inquiries;


    /**
     * Constructor for waiter.
     *
     * @param name Name of the waiter we wish to construct.
     */
    public Waiter(String name) {
        this.name = name.toLowerCase().trim();
        requestedOrders = new ArrayList<Order>();
        servedOrders = new ArrayList<Order>();
        placedOrders = new ArrayList<Order>();
        inquiries= new ArrayList<Inquiry>();
    }

    public void sendInquiry(String s){
       Inquiry toSend = new Inquiry(s);
       inquiries.add(toSend);
    }

    /**
     * Method allows a waiter to place an order that the customer has given.
     *
     * @param o This represents the order.
     * @param k This represents the kitchen/bar that is to fulfill the order.
     * @return Returns true if order is successfully sent to the kitchen, false otherwise.
     */
    public boolean placeOrder(Order o, Kitchen k) {
        k.getOrders().add(o);
        return true;
    }

    /**
     * Method that notifies the system that an order has been served by the waiter.
     *
     * @param o This is the order being served.
     * @return Returns true if the served status is successfully communicated to the console. False otherwise.
     */
    public boolean serveOrder(Order o) {
        o.setServed(LocalDateTime.now());

        //removing the order from requested to served
        requestedOrders.remove(o);

        //adding the order to served orders
        servedOrders.add(o);


        //for compilation
        return true;
    }




    /**
     * Allows the waiter to file a complaint on behalf of the customer.
     *
     * @param o           This is the order to turn into a complaint.
     * @param description This is the nature of the complaint, as explained by the customer.
     * @return Returns true if it was successfully logged in the system to the console and kitchen, false otherwise.
     */
    public boolean issueComplaint(Order o, String description) {
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





    public void setRequestedOrders(List<Order> requestedOrders) {
        this.requestedOrders = requestedOrders;
    }

    public List<Order> getRequestedOrders(){
        return this.requestedOrders;
    }
    public void setServedOrders(List<Order> servedOrders) {
        this.servedOrders = servedOrders;
    }

    public List<Order> getServedOrders(){
        return this.servedOrders;
    }

    public List<Order> getPlacedOrders() {
        return placedOrders;
    }

    public void setPlacedOrders(List<Order> placedOrders) {
        this.placedOrders = placedOrders;
    }

    public List<Inquiry> getInquiries() {
        return inquiries;
    }

    public void setInquiries(List<Inquiry> inquiries) {
        this.inquiries = inquiries;
    }
}