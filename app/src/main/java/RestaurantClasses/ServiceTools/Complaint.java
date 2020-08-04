package ServiceTools;

import Service.*;

import java.time.LocalDateTime;

public class Complaint{
    /**
     * Each complaint has an issue.
     */
    private String issue;

    /**
     * Each complaint is logged at some time.
     */
    private LocalDateTime complaintTime;

    /**
     * Each complaint is associated with an order.
     */
    private Order complainedOrder;

    /**
     * Each complaint has a resolve time (in the case that the complaint is resolved)
     */
    private LocalDateTime resolvedTime;

    /**
     * Initializing our order with time of initialization.
     * @param o This is the order associated with our complaint
     * @param complaint This is the complaint logged with the order.
     */
    public Complaint(Order o,String complaint){
        complainedOrder=o;
        complaintTime=LocalDateTime.now();
        issue=complaint;
    }




    //getters and setters

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public LocalDateTime getComplaintTime() {
        return complaintTime;
    }

    public void setComplaintTime(LocalDateTime complaintTime) {
        this.complaintTime = complaintTime;
    }

    public Order getComplainedOrder() {
        return complainedOrder;
    }

    public void setComplainedOrder(Order complainedOrder) {
        this.complainedOrder = complainedOrder;
    }

    public LocalDateTime getResolvedTime() {
        return resolvedTime;
    }

    public void setResolvedTime(LocalDateTime resolvedTime) {
        this.resolvedTime = resolvedTime;
    }
}