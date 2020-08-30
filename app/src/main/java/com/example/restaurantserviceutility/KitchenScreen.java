package com.example.restaurantserviceutility;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.time.LocalDateTime;

import RestaurantClasses.Service.Kitchen;
import RestaurantClasses.Service.Waiter;
import RestaurantClasses.ServiceTools.Menu;
import RestaurantClasses.ServiceTools.Order;

public class KitchenScreen extends AppCompatActivity {

    private RecyclerView ordersList;
    private RecyclerView orderItemList;
    private TextView kitchenName;
    private TextView orderComments;


    //todo: fix up waiter synchronization
    private Waiter toSend = new Waiter("Doug");
    private Kitchen kitchen;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_screen);



        //name for the kitchen should be in the bundle.
        String name = savedInstanceState.getString("name");

        //creating our object for logic
        kitchen = new Kitchen(name);


        //basically we need the main screen to be a list of orders on the left and when someone selects it, all the order items are shown
        //on a scrollable list to the right

        //there should also be a button to cancel the order (which will turn it into a complaint with description cancelled)
        //there should be a button to notify the waiter to pickup that specific order for serving
        //there should be a button to open up the inquiries scrollable list to reply to waiter questions

        //initializing views
        ordersList = findViewById(R.id.orderList);
        orderItemList = findViewById(R.id.orderList);
        kitchenName = findViewById(R.id.kitchenName);
        orderComments=findViewById(R.id.orderComments);

        //setting up kitchen name
        kitchenName.setText(name);

        MyRecyclerAdapter.Clickable voidClick = (v->{});

        MyRecyclerAdapter.Clickable orderClick = (v ->{
                //setting the order description
                String comments = kitchen.getOrders().get(((MyRecyclerAdapter) ordersList.getAdapter()).getSelectedPosition()).getPrepComments();
                orderComments.setText(comments);
                KitchenOrderAdapter itemAdapt= new KitchenOrderAdapter(this,kitchen.getOrders().get(v).getItems(),kitchen.getOrders().get(v).getAmount(),voidClick);
                orderItemList.setAdapter(itemAdapt);
                //setting first position, if it is not empty
                if (itemAdapt.getItemCount()!=0){
                    itemAdapt.select(0);
                }
        }
        );

        MyRecyclerAdapter orderAdapt = new MyRecyclerAdapter(this,kitchen.getOrders(),orderClick);
        ordersList.setAdapter(orderAdapt);

        //selecting first order, if it exists
        if (orderAdapt.getItemCount()!=0){
           orderAdapt.select(0);
        }
    }

    public void goBack(View v){
        //todo: saving state, synchronizing, etc
        Intent goBack = new Intent(this,MainActivity.class);
        startActivity(goBack);
    }

    //goes to screen to view fulfilled orders (orders that have been made by the kitchen (ready to pickup) )
    public void viewFulfilled(View v){
        Intent toFulfilled = new Intent(this,ServedFulfilledOrdersScreen.class);
        toFulfilled.putExtra("fromWaiter",false);
        toFulfilled.putExtra("kitchen",kitchen);
        startActivity(toFulfilled);
    }

    //goes to interface to view any inquiries made by the waiter on behalf of the customer
    public void viewInquiries(View v){
        //goes to inquiries screen
        Intent inquiriesScreen = new Intent(this,InquiriesScreen.class);
        Bundle args = new Bundle();
        args.putBoolean("fromKitchen",true);
        inquiriesScreen.putExtras(args);
        startActivity(inquiriesScreen);
    }

    //ability to edit order
    //todo: figure out how to implement this
    public void editOrder(View v){

    }

    //moves the selected order to the fulfilled list, and contacts the waiter associated with the order that the order is ready for pickup
    public void signalPickup(View v){
       Order toSignal= kitchen.getOrders().get(((MyRecyclerAdapter)ordersList.getAdapter()).getSelectedPosition());
       //removing order from kitchen list
        kitchen.getOrders().remove(toSignal);
        kitchen.getFulfilled().add(toSignal);
        //setting fulfilled time
        toSignal.setFulfilled(LocalDateTime.now());

        //todo: notify waiter to pickup with a popup
        toSend.getRequestedOrders().add(toSignal);
    }


}
