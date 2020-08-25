package com.example.restaurantserviceutility;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import RestaurantClasses.Service.Kitchen;
import RestaurantClasses.Service.Waiter;
import RestaurantClasses.ServiceTools.Inquiry;

//inquiries screen represents communication between the waiter and the kitchen
//the waiter can send questions to the kitchen, and then the kitchen can respond
public class InquiriesScreen extends AppCompatActivity implements MyTextInputDialog.ReturnText {
    //boolean represents whether the user is from the kitchen or is a waiter
    //the boolean will help setup the button to either be send inquiry (waiter) or respond to inquiry (kitchen)
    private boolean fromKitchen;
    private TextView inquiryText;
    private TextView responseText;
    private int inquiryPosition=0;

    //todo: implement network features
    private Waiter waiter = new Waiter("dave");
    private Kitchen kitchen = new Kitchen("firstFloor");

    private List<Inquiry> inquiries;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiries_screen);
        Bundle extras = getIntent().getExtras();
        fromKitchen = extras.getBoolean("fromKitchen");

        //setting up button view
        Button inquiryButton = findViewById(R.id.respondOrCreateInquiryButton);
        if (fromKitchen){
            //then the button needs to be "respond to inquiry"
            inquiryButton.setText("Respond to Inquiry");
        } else {
            //then the button needs to be "send inquiry"
            inquiryButton.setText("Send Inquiry");
        }

        //fix this later
        inquiries = waiter.getInquiries();

        //setting up our textviews
        inquiryText = findViewById(R.id.inquiryRealText);
        responseText = findViewById(R.id.inquiryResponseText);
        //based on waiter for now
        if (waiter.getInquiries().size()!=0){
           //then I can set up the inquiries
            setInquiries();
        }


    }

    //helper method to set inquiry question and answer
    public void setInquiries(){
        Inquiry toSet = inquiries.get(inquiryPosition);
        inquiryText.setText(toSet.getInquiry());
        if (toSet.getResponse()!=null){
            responseText.setText(toSet.getResponse());
        }

    }

    public void nextInquiry(View v){
        if (inquiryPosition==inquiries.size()-1){
            //then we show an error saying this is the end
            new AlertDialog.Builder(this)
                    .setTitle("ERROR!")
                    .setMessage("Reached the End!")
                    .setPositiveButton("OK",(dialog,id)->{})
                    .show();
        } else {
            inquiryPosition++;
            setInquiries();
        }
    }

    public void previousInquiry(View v){
        if (inquiryPosition==0){
            //then we show an error saying this is the beginning
            new AlertDialog.Builder(this)
                    .setTitle("ERROR!")
                    .setMessage("Reached the Beginning!")
                    .setPositiveButton("OK",(dialog,id)->{})
                    .show();
        } else{
            inquiryPosition--;
            setInquiries();
        }
    }

    public void goBack(View v){
        //goes back to either kitchen screen or waiter screen depending on where we came from
        if (fromKitchen){
            Intent goKitchen = new Intent(this,KitchenScreen.class);
            startActivity(goKitchen);
        } else {
            Intent goWaiter = new Intent(this,WaiterScreen.class);
            startActivity(goWaiter);
        }
    }

    public void respondOrSendInquiry(View v){
       if (fromKitchen){
           //then we should respond to the chosen inquiry
           //if the list is empty we should show and error
           if (inquiries.size()==0){
               new AlertDialog.Builder(this)
                       .setTitle("ERROR!")
                       .setMessage("Reached the Beginning!")
                       .setPositiveButton("OK",(dialog,id)->{})
                       .show();
           } else {
               //starting dialog to respond to the inquiry
               MyTextInputDialog responsePopup = MyTextInputDialog.newInstance("Please Enter a Response:");
               FragmentManager frag = getSupportFragmentManager();
               responsePopup.show(frag,"Inquiry Response");
           }

       } else {
           //then we should create a new inquiry and send it to the kitchen
           //we will use the same dialog for a response and sending an inquiry, we will just change the title based on the context
            MyTextInputDialog sendInquiryPopup = MyTextInputDialog.newInstance("Please Enter an Inquiry");
           FragmentManager frag = getSupportFragmentManager();
           sendInquiryPopup.show(frag,"Send Inquiry");
       }
    }

    //method that clears the inquiries from the device instance. The inquiries will still show up in the kitchen
    //until they are cleared there
    public void clear(View v){
       //todo implement instance networking for clear
        inquiries.clear();
        inquiryText.setText(null);
        responseText.setText(null);
        inquiryPosition=0;
    }

    @Override
    public void onReturnText(String s) {
        //basically if we are in the kitchen, we add a response
        //if we are a waiter, then we add a new inquiry to the list with the given string
        if (fromKitchen){
            inquiries.get(inquiryPosition).setResponse(s);
            responseText.setText(s);
        } else {
            //we add a new inquiry to the end of the list and then jump to it
            inquiries.add(new Inquiry(s));
            inquiryPosition= inquiries.size()-1;
            setInquiries();
        }
    }
}
