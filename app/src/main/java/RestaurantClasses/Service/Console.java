package Service;


import ServiceTools.*;
import java.util.ArrayList;

/**
 * Class to represent a main console for an operating section of the restaurant. This will faciltate management with logs
 * and offer a link between the kitchen and the waiter.
 */
public class Console{
    /**
     * Name given to this console. Useful for splitting restaurant into different "zones" in the restaurant.
     *
     */
    private String name;

    /**
     * Menu that the console has been given. This menu will be automatically shared with the waiter and kitchen when they pair for operations.
     */
    private Menu menu;

    /**
     * Orders that have been issued in the operating area of this console.
     */
    private ArrayList<Order> orders;

    /**
     * Orders that have been fulfilled (ready to be picked up from kitchen/bar) in the operating area of this console.
     */
    private  ArrayList<Order> fulfilledOrders;

    /**
     * Orders that have been served by a waiter in the operating area of this console.
     */
    private ArrayList<Order> servedOrders;

    /**
     * Complaints given about orders.
     */
    private ArrayList<Complaint> complaints;



    /**
     * A console has other knowledge of the other consoles in the restaurant.
     */
    private ArrayList<Console> consoles;

    /**
     * Constructor for consoles to create.
     * @param name name or identification of the console we wish to create.
     */
    public Console(String name){
        this.name=name;
        orders=new ArrayList<Order>();
        fulfilledOrders=new ArrayList<Order>();
        complaints = new ArrayList<Complaint>();
    }



    //getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        menu = menu;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public ArrayList<Order> getFulfilledOrders() {
        return fulfilledOrders;
    }

    public void setFulfilledOrders(ArrayList<Order> fulfilledOrders) {
        this.fulfilledOrders = fulfilledOrders;
    }

    public ArrayList<Complaint> getComplaints() {
        return complaints;
    }

    public void setComplaints(ArrayList<Complaint> complaints) {
        this.complaints = complaints;
    }

    public ArrayList<Console> getConsoles() {
        return consoles;
    }

    public void setConsoles(ArrayList<Console> consoles) {
        this.consoles = consoles;
    }

    public ArrayList<Order> getServedOrders() {
        return servedOrders;
    }

    public void setServedOrders(ArrayList<Order> servedOrders) {
        this.servedOrders = servedOrders;
    }
}