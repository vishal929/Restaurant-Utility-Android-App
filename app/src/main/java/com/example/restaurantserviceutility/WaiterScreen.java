package com.example.restaurantserviceutility;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import RestaurantClasses.Service.Kitchen;
import RestaurantClasses.Service.Waiter;
import RestaurantClasses.ServiceTools.Menu;
import RestaurantClasses.ServiceTools.Order;

public class WaiterScreen extends AppCompatActivity {
    private Waiter waiter;
    private Menu menu;
    private TextView waiterName;
    private RecyclerView toServeList;
    private RecyclerView placedOrderList;

    //todo: synchronize kitchen list or something
    private Kitchen toSend = new Kitchen("base");

    //todo: menu sychronization
    private Menu toUse = new Menu();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter_screen);

        //todo: menu stuff
        toUse.addCategory("apps");
        toUse.addCategory("main");
        toUse.getMenu().get(0).addItem("sandwich",5);
        toUse.getMenu().get(1).addItem("coke",85.3);


        //name for the waiter should be in the bundle.
        Bundle extra = getIntent().getExtras();
        String name;
        if (extra!=null){

            name = getIntent().getStringExtra("name");
        } else {
            name = "OH NO BUNDLE WAS NULL";
        }

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

        //setting up layout managers
        toServeList.setLayoutManager(new LinearLayoutManager(this));
        placedOrderList.setLayoutManager(new LinearLayoutManager(this));

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
        //goes to a new screen to view a double list with order and the associated items
        //there will be an option to issue a complaint from here
    }

    public void placeOrder(View v){
        //opens a new screen with the ability to change category, choose items, choose table, etc
        //should be a way to cancel and go back here
        Intent placeOrder = new Intent(this,OrderScreen.class);
        placeOrder.putExtra("waiter",waiter);
        startActivity(placeOrder);

    }


    //todo: implement inquiries feature
    public void viewInquiries(View v){
        //goes to inquiries screen
    }

    public void serveOrder(View v){
        //basically sets the order as served and removes it from the toServe list
        MyRecyclerAdapter serveAdapt = (MyRecyclerAdapter) toServeList.getAdapter();
        Order served = waiter.getRequestedOrders().get(serveAdapt.getSelectedPosition());
        //remove item
        serveAdapt.remove();
        //add item to served list
        waiter.getServedOrders().add(served);

    }




}
