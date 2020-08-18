package com.example.restaurantserviceutility;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import RestaurantClasses.Service.Kitchen;

public class KitchenScreen extends AppCompatActivity {

    private RecyclerView ordersList;
    private RecyclerView orderItemList;
    private TextView kitchenName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_screen);

        //name for the kitchen should be in the bundle.
        String name = savedInstanceState.getString("name");

        //creating our object for logic
        Kitchen kitchen = new Kitchen(name);


        //basically we need the main screen to be a list of orders on the left and when someone selects it, all the order items are shown
        //on a scrollable list to the right

        //there should also be a button to cancel the order (which will turn it into a complaint with description cancelled)
        //there should be a button to notify the waiter to pickup that specific order for serving
        //there should be a button to open up the inquiries scrollable list to reply to waiter questions

        //initializing views
        ordersList = findViewById(R.id.orderList);
        orderItemList = findViewById(R.id.orderItemList);
        kitchenName = findViewById(R.id.kitchenName);

        //setting up kitchen name
        kitchenName.setText(name);

        //setting up my adapters
        MyRecyclerAdapter.Clickable orderClick = (v ->{
                MyRecyclerAdapter itemAdapt = new MyRecyclerAdapter(this,kitchen.getOrders().get(v).getItems(),(p->{}));
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

    public void viewFulfilled(View v){

    }

    public void viewInquiries(View v){

    }

    public void editOrder(View v){

    }

    public void signalPickup(View v){

    }


}
