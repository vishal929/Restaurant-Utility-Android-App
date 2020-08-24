package com.example.restaurantserviceutility;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

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

    //todo: implement with internet features
    public boolean checkMenu(){
        //if there is no menu found on the network, we return false
        //otherwise return true

        //reason is because if there is no menu, we should not go to kitchen or waiter screens and show an error
        return true;
    }

    //transitions to other activities
    //We want there to be a popup after the transition to let the user put in their name to be recognized in the system and for our
    //java classes to be properly constructed

    //method onclick for when user selects to be a waiter
    public void goToWaiterScreen(View v){

        enterName(0);



    }

    //method onclick for when user selects to be a console
    public void goToConsoleScreen(View v){

        enterName(1);

    }

    //method onclick for when user selects to be a kitchen/bar
    public void goToKitchenScreen(View v){

       enterName(2);

    }

    //method to move to menu edit screen
    public void goToMenuScreen(View v){
        //logic to switch to menu screen
        //idea: basically our arbitrary rule is that consoles will have the most up to date menu data
                //so, if this device has no menu data, it will first instruct the user on these steps
                //it will check the internet to see if there are consoles with menu data
                //then it will check the menu data which was most recently updated and grab that
                //then it will allow editing
                //once editing is done and submitted, it will notify devices on the internet that a new up to date menu is available to download.
                //Hopefully this download can occur in the background
        Intent i = new Intent(this,MenuScreen.class);
        startActivity(i);
    }

    //method to show a popup to enter the devices name before going to operational menu
    //method returns false if there was an issue entering the name, returns true otherwise and continues
    public void enterName(int i){
        EnterNamePopup namePopup = EnterNamePopup.newInstance(i);
        FragmentManager manager = getSupportFragmentManager();
        namePopup.show(manager,"Enter Name");


    }



}
