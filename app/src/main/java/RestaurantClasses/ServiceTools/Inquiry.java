package RestaurantClasses.ServiceTools;

/**
 * Class represents an inquiry that the waiter can ask the kitchen. Then the kitchen can give a response.
 */
public class Inquiry {
    private String inquiry;
    private String response;

    /**
     * The inquiry-response interaction starts with the waiter making an inquiry
     * @param inquiry This is the question asked by the waiter.
     */
    public Inquiry(String inquiry){
       this.inquiry=inquiry;
       response=null;
    }

    public boolean setResponse(String response){
        if (this.response==null){
            this.response=response;
            return true;
        } else {
            //cant respond more than once
            return false;
        }
    }

    public String getInquiry(){
        return inquiry;
    }

    public String getResponse(){
        return response;
    }
}
