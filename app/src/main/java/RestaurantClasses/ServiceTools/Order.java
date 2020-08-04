package ServiceTools;

import Service.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * This class represents an order that the waiter requests, the kitchen fulfills, and the waiter then serves.
 */
public class Order{

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
    private ArrayList<MenuItem> items;

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
     * Constructing our order. The time is initialized when the order is created.
     * @param waiter Waiter issuing the order.
     * @param table Table which requests the order.
     */
    public Order(Waiter waiter, int table, ArrayList<MenuItem> order){
       this.waiter=waiter;
       this.table=table;

       //Initializing our ArrayList of items
        items=order;

        //initializing our construction time
       issued= LocalDateTime.now();
    }

    /**
     * Method to return a complaint from this order. The order and complaint both exist separately because the complaint can be resolved and the order can still be filled.
     * @param description This is the nature of the complaint.
     * @return Returns the created complaint.
     */
    public Complaint makeComplaint(String description){
       return new Complaint(this,description);
    }



    // getters and setters
    public Waiter getWaiter() {
        return waiter;
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

    public ArrayList<MenuItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<MenuItem> items) {
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
}