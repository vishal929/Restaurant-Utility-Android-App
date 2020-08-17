package com.example.restaurantserviceutility;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;


import java.util.List;

import RestaurantClasses.ServiceTools.Menu;
import RestaurantClasses.ServiceTools.MenuCategory;
import RestaurantClasses.ServiceTools.MenuItem;

public class MenuScreen extends AppCompatActivity implements NamePriceInputPopup.onConfirm{

    private RecyclerView categoriesList;
    private RecyclerView itemList;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);

        //STUFF BELOW IS FOR WHEN DEVICE CONNECTIONS ARE FIGURED OUT. FOR NOW WE WILL USE A STATIC OBJECT
        /*
         //we should check if a menu exists on this device
         //if no menu exists on this device or an updated menu is found on the local network, we will update our menu to match the latest version

         //checking to see if menu exists
         //this is a file which is my serialized menuObj object
         File menuObj = new File(this.getFilesDir(),"menu");

         //checking devices on the network for the most up to date menu serialization
         WifiP2pManager netMan = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
         WifiP2pManager.Channel channel = netMan.initialize(this,getMainLooper(),null);
         */

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

        //setting up our recyclerviews for the categories and associated items

        categoriesList = findViewById(R.id.categoriesList);
        itemList = findViewById(R.id.menuItemList);

        //creating clickable logic for items
        MyRecyclerAdapter.Clickable itemClick = new MyRecyclerAdapter.Clickable() {
            @Override
            public void onItemSelected(int position) {

            }
        };

        //creating clickable logic for categories
        MyRecyclerAdapter.Clickable click = new MyRecyclerAdapter.Clickable() {
            @Override
            public void onItemSelected(int position) {
                //we want to make the items for each category show on the right when we pick a category
                itemList.setAdapter(new MyRecyclerAdapter(getApplicationContext(),menu.getMenu().get(position).getItems(),itemClick));
                itemList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }
        };

        //binding our categorieslist to the ccategories of our menu
        MyRecyclerAdapter categoryAdapter = new MyRecyclerAdapter(this,menu.getMenu(),click);


        categoriesList.setAdapter(categoryAdapter);

        //setting layout manager
        categoriesList.setLayoutManager(new LinearLayoutManager(this));




    }


    public void removeCategory(View v){
        MyRecyclerAdapter adapter = (MyRecyclerAdapter) categoriesList.getAdapter();
        if (adapter.remove()){
            //then the item was successfully removed
            if (adapter.getItemCount()==0){
                //then we need to reset the MenuItem recyclerview
                itemList.setAdapter(null);
            }

        } else {
            //then the list is empty
            new AlertDialog.Builder(this)
                    .setTitle("ERROR!")
                    .setMessage("No Categories to Delete!")
                    .setPositiveButton("OK",(dialog,id)->{})
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

    }

    public void addCategory(View v){
       //we have a dialog input for the category
       //if the category add is failed, then we show another dialog for the error
        EditText e = new EditText(this);
        new AlertDialog.Builder(this)
                .setTitle("Category Input")
                .setMessage("Please Enter a Name!")
                .setView(e)
                .setNegativeButton("Cancel",(dialog, which) -> dialog.dismiss())
                //confirm button should check content of the string
                .setPositiveButton("Confirm",(dialog, which) -> {
                    String toConsider = e.getText().toString().toLowerCase().trim();
                    if (toConsider.isEmpty()){
                        //then we should show error dialog
                        new AlertDialog.Builder(this)
                                .setTitle("ERROR!")
                                .setMessage("Please Fill in A Name!")
                                .setPositiveButton("OK",(dialogTwo,id)->{dialogTwo.dismiss();})
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    } else if (menu.addCategory(toConsider)){
                        //then add was successsful and we need to notify the adapter
                        categoriesList.getAdapter().notifyItemInserted(menu.getMenu().size()-1);
                        categoriesList.getAdapter().notifyItemRangeInserted(menu.getMenu().size()-1,menu.getMenu().size());
                        //we should also autoclick this selection
                        ((MyRecyclerAdapter) categoriesList.getAdapter()).select(menu.getMenu().size()-1);
                    } else {
                        //then the category already exists
                        new AlertDialog.Builder(this)
                                .setTitle("ERROR!")
                                .setMessage("Category Already Exists!")
                                .setPositiveButton("OK",(dialogThree,id)->{dialogThree.dismiss();})
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();

                    }
                })
                .show();
    }

    public void removeItem(View v){
        MyRecyclerAdapter adapter = (MyRecyclerAdapter) itemList.getAdapter();
        if (adapter.remove()){
            //then the item was successfully removed
            if (adapter.getItemCount()==0){
                //then we need to reset the MenuItem recyclerview
                itemList.setAdapter(null);
            }
        } else {
            //then the list is empty
            new AlertDialog.Builder(this)
                    .setTitle("ERROR!")
                    .setMessage("No Items to Delete!")
                    .setPositiveButton("OK",(dialog,id)->{})
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    public void addItem(View v){
        //if there are no categories, we must show an error

        MyRecyclerAdapter adapt = (MyRecyclerAdapter) categoriesList.getAdapter();
        if (adapt.getItemCount()==0){
            //then we show an error
            new AlertDialog.Builder(this)
                    .setTitle("ERROR!")
                    .setMessage("First Add a Category!")
                    .setPositiveButton("OK",(dialog,id)->{})
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            NamePriceInputPopup enterNamePrice = NamePriceInputPopup.newInstance();
            FragmentManager frag = getSupportFragmentManager();
            enterNamePrice.show(frag,"Enter Name and Price");
        }


    }

    //returning from namePrice input
    @Override
    public void returnData(String name, double price) {
        //if the item name exists, we throw an error
        //otherwise we are good to add to the list
        MyRecyclerAdapter adapt = (MyRecyclerAdapter) categoriesList.getAdapter();
        MenuCategory cat = (MenuCategory) adapt.get().get(adapt.getSelectedPosition());
        if (!cat.addItem(name,price)){
            //then the add wasnt successful and we need to show an error to the user
            new AlertDialog.Builder(this)
                    .setTitle("ERROR!")
                    .setMessage("Item Already Exists!")
                    .setPositiveButton("OK",(dialog,id)->{})
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        }
        adapt.select(adapt.getSelectedPosition());
        MyRecyclerAdapter itemAdapt = (MyRecyclerAdapter) itemList.getAdapter();
        itemAdapt.select(itemAdapt.getItemCount()-1);

    }


    //logic to go to view item screen
    public void viewItem(View v){
        //will need to bundle the currently selected menuItem
        MyRecyclerAdapter catadapt = (MyRecyclerAdapter) categoriesList.getAdapter();
        MyRecyclerAdapter itadapt = (MyRecyclerAdapter) itemList.getAdapter();
        MenuItem selected = menu.getMenu()
                .get(catadapt.getSelectedPosition())
                .getItems()
                .get(itadapt.getSelectedPosition());

        Intent goToItem = new Intent(this,MenuItemScreen.class);
        //sending menu over
        goToItem.putExtra("menu",menu);
        //sending category info over
        goToItem.putExtra("catPos",catadapt.getSelectedPosition());
        //sending item info over
        goToItem.putExtra("itemPos",itadapt.getSelectedPosition());
        startActivity(goToItem);
    }

    //method to go back (we have to make sure to save this menu and notify users connected to the console that the menu has been altered)
    public void goBack(View v){
       Intent goBack = new Intent(this,MainActivity.class);
       startActivity(goBack);
    }
}
