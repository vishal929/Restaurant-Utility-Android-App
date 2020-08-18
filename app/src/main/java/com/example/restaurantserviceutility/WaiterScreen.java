package com.example.restaurantserviceutility;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import RestaurantClasses.Service.Waiter;
import RestaurantClasses.ServiceTools.Menu;

public class WaiterScreen extends AppCompatActivity {
    private Waiter waiter;
    private Menu menu;
    private TextView waiterName;
    private RecyclerView toServeList;
    private RecyclerView placedOrderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter_screen);


        //name for the waiter should be in the bundle.
        String name = savedInstanceState.getString("name");

        //creating our object for logic
        waiter = new Waiter(name);

        //initializing menu
        //Todo: Need to figure out menu logic
        //in the meantime: we have other logic from menu class
        menu = new Menu();

        //we will add some categories and items to test
        menu.addCategory("Appetizers");
        menu.getMenu().get(0).addItem("sandwich",20.0);
        menu.getMenu().get(0).getItems().get(0).addVariant("grilled sandwich",30);
        menu.getMenu().get(0).getItems().get(0).addAdditional("extra cheese",2);
        menu.addCategory("Beverages");
        menu.getMenu().get(1).addItem("Coke",1.30);
        menu.addCategory("Dessert");
        menu.getMenu().get(2).addItem("Cake",5.25);
        menu.addCategory("Fresh from The Grill");
        menu.getMenu().get(3).addItem("Grilled Paneer",5);

        //initializing views
        waiterName = findViewById(R.id.waiterNameText);
        toServeList = findViewById(R.id.pickupList);
        placedOrderList = findViewById(R.id.placedOrders);

        //setting up waiter name
        waiterName.setText(waiter.getName());

        //setting up adapters
        MyRecyclerAdapter.Clickable voidClick =  (p ->{});

        toServeList.setAdapter(
                new MyRecyclerAdapter(this,waiter.getRequestedOrders(),voidClick)
        );

        placedOrderList.setAdapter(
            new MyRecyclerAdapter(this,waiter.getPlacedOrders(),voidClick)
        );

    }

    public void goBack(View v){
        //todo: save items/ communicate with console
        Intent goBack = new Intent(this,MainActivity.class);
        startActivity(goBack);
    }

    public void viewServedOrders(View v){

    }

    public void placeOrder(View v){

    }


    //todo: implement inquiries feature
    public void viewInquiries(View v){

    }

    public void serveOrder(View v){

    }

    


}
