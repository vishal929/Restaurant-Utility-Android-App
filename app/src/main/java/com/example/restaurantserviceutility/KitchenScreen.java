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
    }
}
