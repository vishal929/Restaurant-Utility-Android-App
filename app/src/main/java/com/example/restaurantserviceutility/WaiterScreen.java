package com.example.restaurantserviceutility;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import RestaurantClasses.Service.Waiter;

public class WaiterScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter_screen);

        //name for the waiter should be in the bundle.
        String name = savedInstanceState.getString("name");

        //creating our object for logic
        Waiter waiter = new Waiter(name);

    }
}
