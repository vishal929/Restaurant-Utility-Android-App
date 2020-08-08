package com.example.restaurantserviceutility;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.app.Dialog;
import android.os.Bundle;

import RestaurantClasses.Service.Console;

public class ConsoleScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_console_screen);

        //the name for the console should be in the bundle

        String name = savedInstanceState.getString("name");
        Console console = new Console(name);



    }
}
