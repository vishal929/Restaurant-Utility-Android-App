package com.example.restaurantserviceutility;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import RestaurantClasses.Service.Kitchen;

public class KitchenScreen extends AppCompatActivity {

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



    }
}
