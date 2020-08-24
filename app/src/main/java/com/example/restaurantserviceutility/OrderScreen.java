package com.example.restaurantserviceutility;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;

import RestaurantClasses.Service.Waiter;
import RestaurantClasses.ServiceTools.Menu;
import RestaurantClasses.ServiceTools.MenuCategory;
import RestaurantClasses.ServiceTools.MenuItem;
import RestaurantClasses.ServiceTools.Order;
import RestaurantClasses.ServiceTools.OrderItem;

public class OrderScreen extends AppCompatActivity implements ChooseVariantAdditionsPopup.OnItemSelect{

    private Waiter waiter;
    private Menu menu;
    private Order toPlace;
    private ArrayList<OrderItem> orderItems = new ArrayList<OrderItem>();
    private ArrayList<Integer> orderItemsAmount = new ArrayList<Integer>();

    //our views
    private Spinner catSpinner;
    private EditText tableText;
    private EditText optionalDescription;
    private RecyclerView itemList;
    private RecyclerView orderList;


    //BIG TODO: FIGURE OUT A GOOD WAY TO INCORPORATE ADDING ADDITIONALS AND VARIANTS FROM MENU TO ORDER

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_screen);

        //initializing our waiter
        //todo: synchronization
        waiter = (Waiter) getIntent().getExtras().get("waiter");
        //menu = (Menu) getIntent().getExtras().get("menu");

        //setting up our menu
        //todo: synchronization
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

        //setting up our views
        catSpinner = findViewById(R.id.categorySpinner);

        tableText = findViewById(R.id.tableRealInput);
        optionalDescription = findViewById(R.id.descriptionInput);

        itemList = findViewById(R.id.menuOrderList);
        orderList = findViewById(R.id.menuItemOrderList);

        itemList.setLayoutManager(new LinearLayoutManager(this));
        orderList.setLayoutManager(new LinearLayoutManager(this));

        MyRecyclerAdapter.Clickable voidClick = (v->{});
        OrderAdapter orderAdapt = new OrderAdapter(this,orderItems,orderItemsAmount,voidClick);
        orderList.setAdapter(orderAdapt);



        //setting up our spinner
        ArrayAdapter<MenuCategory> e = new ArrayAdapter<MenuCategory>(this,android.R.layout.simple_spinner_dropdown_item,menu.getMenu());
        catSpinner.setAdapter(e);

        //setting on click for our spinner
        catSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //just need to set the menuItem adapter based on this click
                MenuCategory selected = menu.getMenu().get(position);
                MyRecyclerAdapter itemAdapt = new MyRecyclerAdapter(getApplicationContext(),selected.getItems(),voidClick);
                itemList.setAdapter(itemAdapt);
                if (itemAdapt.getItemCount()!=0){
                    itemAdapt.select(0);
                }

            }

            /**
             * Callback method to be invoked when the selection disappears from this
             * view. The selection can disappear for instance when touch is activated
             * or when the adapter becomes empty.
             *
             * @param parent The AdapterView that now contains no selected item.
             */
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void goBack(View v){
        Intent goBack = new Intent(this,WaiterScreen.class);
        startActivity(goBack);
    }

    public void addItemToOrder(View v){
        //get the selected item from the left list and add 1 item to the right
        //first we check the size of the list on the left and throw an error dialog if its empty
        //if not empty, then we add it to our order
        MyRecyclerAdapter menuItemAdapt = (MyRecyclerAdapter) itemList.getAdapter();
        if (menuItemAdapt.getItemCount()==0){
            //then we throw a dialogue informing an error
            new AlertDialog.Builder(this)
                    .setTitle("ERROR!")
                    .setMessage("No Items to Add!")
                    .setPositiveButton("OK",(dialog,id)->{})
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            //we need to show a fragment for picking a variant and picking from a checkbox of additions
            //TODO: MAKE THE FRAGMENT
            ChooseVariantAdditionsPopup pop = ChooseVariantAdditionsPopup.newInstance(menu.getMenu()
            .get(catSpinner.getSelectedItemPosition())
            .getItems()
            .get(menuItemAdapt.getSelectedPosition()));
            FragmentManager frag = getSupportFragmentManager();
            pop.show(frag,"Choose Variant and Additions");

            //to remove
            /*

            OrderItem toAdd=
                    new OrderItem(
                        menu.getMenu()
                                .get(catSpinner.getSelectedItemPosition())
                                .getItems()
                                .get(menuItemAdapt.getSelectedPosition())
                                .getName()
                    ,   "testName"
                    ,   3
                    );
            //incrementing the amount, if applicable
            for (int i=0;i<orderItems.size();i++){
                if (orderItems.get(i).equals(toAdd)){
                    int amount = orderItemsAmount.get(i);
                    amount++;
                    orderItemsAmount.remove(i);
                    orderItemsAmount.add(i,amount);
                }
            }
            orderList.getAdapter().notifyDataSetChanged();
            ((OrderAdapter) orderList.getAdapter())
                    .select(((OrderAdapter) orderList.getAdapter()).getSelectedPosition());

             */
        }
    }

    public void removeItemFromOrder(View v){
        //remove the selected item from the order
        //if the list is empty, i throw a dialogue error
        //otherwise i remove the selected position from the list


        if (orderList.getAdapter().getItemCount()==0){
            new AlertDialog.Builder(this)
                    .setTitle("ERROR!")
                    .setMessage("No Items to Remove!")
                    .setPositiveButton("OK",(dialog,id)->{})
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            ((OrderAdapter) orderList.getAdapter()).remove();
        }
    }

    public void addAdditionalItem(View v){
        //add an additional item in the order
        //basically I just need to add another of the same item
        if (orderItems.size()==0){
            //show error
            new AlertDialog.Builder(this)
                    .setTitle("ERROR!")
                    .setMessage("No Items to Add More To!")
                    .setPositiveButton("OK",(dialog,id)->{})
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            int pos = ((OrderAdapter)orderList.getAdapter()).getSelectedPosition();
            int amount = orderItemsAmount.get(pos);
            orderItemsAmount.remove(pos);
            amount++;
            orderItemsAmount.add(pos,amount);
        }
    }

    public void finalizeOrder(View v){
        //place the order and notify the kitchen to prepare it
        //if no table is selected, we need to throw an error dialog
        if (tableText.getText().toString().trim().isEmpty()){
            new AlertDialog.Builder(this)
                    .setTitle("ERROR!")
                    .setMessage("Please Enter a Table Number!")
                    .setPositiveButton("OK",(dialog,id)->{})
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            return;
        }
        Order toPlace = new Order(waiter,Integer.parseInt(tableText.getText().toString().trim()),orderItems,orderItemsAmount);

        //todo: synchronization to place this order

        Intent waiterScreen = new Intent(this,WaiterScreen.class);
        startActivity(waiterScreen);
    }


    //for return from popup
    @Override
    public void addToOrder(OrderItem c) {
        //we just need to add the orderItem to the order list
        //if the same object is there, then we increment the amount
        //otherwise, we add it and a new amount
        if (orderItems.contains(c)){
            //then we should increment the amount
            int index = orderItems.indexOf(c);

           int amount= orderItemsAmount.get(index);
           amount++;
           orderItemsAmount.remove(index);
           orderItemsAmount.add(index,amount);
        } else {
            //then I just add a new orderItem and amount
            orderItems.add(c);
            orderItemsAmount.add(1);
        }
        //need to update the prices now
        //TODO: update prices
    }

    //todo: implement this method for synchronization
    public void synchronize(){

    }
}
