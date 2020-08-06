package com.example.restaurantserviceutility;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//Purpose of this class is to be a login hub for my app
public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

    }


    //transitions to other activities
    //We want there to be a popup after the transition to let the user put in their name to be recognized in the system and for our
    //java classes to be properly constructed

    //method onclick for when user selects to be a waiter
    public void goToWaiterScreen(View v){
        Intent waiterScreen = new Intent(this,WaiterScreen.class);

        startActivity(waiterScreen);
    }

    //method onclick for when user selects to be a console
    public void goToConsoleScreen(View v){


        Intent consoleScreen = new Intent(this,ConsoleScreen.class);

        startActivity(consoleScreen);
    }

    //method onclick for when user selects to be a kitchen/bar
    public void goToKitchenScreen(View v){
        Intent kitchenScreen = new Intent(this,KitchenScreen.class);

        startActivity(kitchenScreen);
    }
}
