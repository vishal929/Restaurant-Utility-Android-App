package com.example.restaurantserviceutility;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import RestaurantClasses.ServiceTools.Menu;

public class MenuItemScreen extends AppCompatActivity implements NamePriceInputPopup.onConfirm,OnlyPriceInput.JustPrice{
    //enums for editing, and adding operations
    private enum Operation{
        EDIT_ADDITION,
        EDIT_VARIATION,
        ADD_ADDITION,
        ADD_VARIATION;
    }
    private Menu menu;
    private int catPosition;
    private int itemPosition;
    private RecyclerView additionsList;
    private RecyclerView variationsList;
    private TextView title;
    private TextView description;
    private TextView itemName;

    private Operation operation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item_screen);

        //we have three extras -> the menu itself, the category position, and the item position
        //the positions will help us seamlessly transition to the other items in the list

        Bundle args = getIntent().getExtras();
        catPosition=args.getInt("catPos");
        itemPosition=args.getInt("itemPos");
        menu = (Menu) args.getSerializable("menu");

        if (menu==null){
            System.out.println("I AM NULL DO NOT PROCEED!");
        }

        //initializing our views
        description = findViewById(R.id.menuItemDescriptionText);

        title = findViewById(R.id.categoryItemText);
        itemName = findViewById(R.id.itemNameTitle);


        //setting up our adapters for the additions and variations list
        //we are using a special adapter for these, which shows price on the right and the name on the left
        additionsList = findViewById(R.id.additionsList);
        variationsList = findViewById(R.id.variationsList);

        //calling our helper method to setup the title and the lists
       setUpScreen();


    }

    //helper method to setup title, lists, and adapters, will help in onCreate and future methods
    public void setUpScreen(){
        //description initialization, if it exists
        if (menu.getMenu().get(catPosition).getItems().get(itemPosition).getDescription()!=null){
           description.setText(menu.getMenu().get(catPosition).getItems().get(itemPosition).getDescription());
        } else {
            description.setText(null);
        }
        //need to initialize our textview for the item name and the category
        String toSet = menu.getMenu().get(catPosition).getName()+" : ";
        String name = menu.getMenu().get(catPosition).getItems().get(itemPosition).getName();
        title.setText(toSet);
        itemName.setText(name);

        //setting up a click interface that does nothing
        MyRecyclerAdapter.Clickable voidClick = (i->{});

        additionsList.setAdapter(new PriceRecyclerAdapter(this,
                menu.getMenu().get(catPosition).getItems().get(itemPosition).getAdditions(),
                menu.getMenu().get(catPosition).getItems().get(itemPosition).getAdditionPrices(),
                voidClick));
        additionsList.setLayoutManager(new LinearLayoutManager(this));

        variationsList.setAdapter(new PriceRecyclerAdapter(this,
                menu.getMenu().get(catPosition).getItems().get(itemPosition).getVariants(),
                menu.getMenu().get(catPosition).getItems().get(itemPosition).getVariantPrices(),
                voidClick));
        variationsList.setLayoutManager(new LinearLayoutManager(this));

        //we should also select the first item in each list, if any
        if (additionsList.getAdapter().getItemCount()!=0){
             ((PriceRecyclerAdapter)additionsList.getAdapter()).select(0);
        }

        if (variationsList.getAdapter().getItemCount()!=0){
            ((PriceRecyclerAdapter)variationsList.getAdapter()).select(0);
        }
    }

    //ToDo: Need to implement some description saving/setting method to call when we goBack, nextItem, or previousItem
    public void goBack(View v){
        //need to send updated menu back to menuScreen
        //then from the menuScreen, if they hit back , it should save the menu and communicate the new menu to all users connected to the console
        Intent goBack = new Intent(this,MenuScreen.class);
        startActivity(goBack);
    }

    //ToDo: implement this method to make the menu a little more fancy
    public void uploadPicture (View v){
       //popup to ask user if they want to upload an existing picture from their device or use their camera
    }

    public void nextItem(View v){
        //goes to next item in the category, or the first item in the next category, if this is the last item in the current category
        //WE NEED TO CHECK IF A CATEGORY EVEN HAS AN ITEM IN IT
        if (menu.getMenu().get(catPosition).getItems().size()-1 == itemPosition){
            //then this is the last item in the current category
            itemPosition=0;
            do{
                catPosition ++;
                if (catPosition==menu.getMenu().size()){
                    //resetting to first one
                    catPosition=0;
                }
            } while(menu.getMenu().get(catPosition).getItems().size()==0);

        } else {
            //then we can move on to the next item
            itemPosition++;

        }
        //doing setup again for titleView, lists and adapters
        setUpScreen();
    }

    public void previousItem(View v){
        //opposite of above method
        if (itemPosition==0){
           //then I need to go to the previous category
           do {
               catPosition--;
               if (catPosition<0){
                   catPosition=menu.getMenu().size()-1;
               }
           } while (menu.getMenu().get(catPosition).getItems().size()==0);
        } else {
           //then I can just move back one
            itemPosition--;
        }
        setUpScreen();
    }

    public void editAddition(View v){
        //i can edit any addition for name and price
        //I will just repurpose my name price input popup for this
        operation=Operation.EDIT_ADDITION;

        NamePriceInputPopup addPop = NamePriceInputPopup.newInstance();
        FragmentManager frag = getSupportFragmentManager();
        addPop.show(frag,"Edit Addition");


    }

    public void editVariation(View v){
        //we can only edit the base price for the base variation (no name editing)
        //any other variation we can edit both
        //ill make a price input
        operation=Operation.EDIT_VARIATION;
        PriceRecyclerAdapter varAdapt = (PriceRecyclerAdapter) variationsList.getAdapter();

        //check if we are editing base price
        if (varAdapt.getSelectedPosition()==0) {
            OnlyPriceInput editBase = OnlyPriceInput.newInstance();
            FragmentManager frag = getSupportFragmentManager();
            editBase.show(frag, "Edit Base Variation");
        } else {
            //then we can edit the name and the price
            NamePriceInputPopup editOtherVar = NamePriceInputPopup.newInstance();
            FragmentManager frag = getSupportFragmentManager();
            editOtherVar.show(frag, "Edit Variation");
        }

    }

    public void addAddition(View v){
        //if we are trying to add an addition, we must set an operation for the addition
        operation=Operation.ADD_ADDITION;
        //adds an addition to the meal
        NamePriceInputPopup addition = NamePriceInputPopup.newInstance();
        FragmentManager frag = getSupportFragmentManager();
        addition.show(frag,"Enter Name and Price");
    }

    public void removeAddition(View v){
        //removes an addition to the meal
        //we can remove any addition

        //we must first get the selected position and then just remove it from both lists and update
        PriceRecyclerAdapter addAdapt = (PriceRecyclerAdapter) additionsList.getAdapter();
        if (!addAdapt.remove()){
         //then the removal was unsuccessful, which means that the list is empty
            //showing error
            new AlertDialog.Builder(this)
                    .setTitle("ERROR!")
                    .setMessage("No Addition to Delete!")
                    .setPositiveButton("OK",(dialog,id)->{})
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    public void addVariation(View v){
        //if we are trying to add a variation, we must set an operation for the variation
        operation=Operation.ADD_VARIATION;
        //adds a variation to the meal
        NamePriceInputPopup variation = NamePriceInputPopup.newInstance();
        FragmentManager frag = getSupportFragmentManager();
        variation.show(frag,"Enter Name and Price");
    }

    public void removeVariation(View v){
        //removes a variation from the meal
        //WE CANNOT REMOVE THE BASE VARIANT

        //checking for base variant (it is always the first position in the list)
        PriceRecyclerAdapter varAdapt = (PriceRecyclerAdapter) variationsList.getAdapter();
        if (varAdapt.getSelectedPosition()==0){
            //then the user is trying to delete the base variation
            //showing error
            new AlertDialog.Builder(this)
                    .setTitle("ERROR!")
                    .setMessage("Cannot Remove Base Variation!")
                    .setPositiveButton("OK",(dialog,id)->{})
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            varAdapt.remove();
        }
    }

    @Override
    public void returnData(String name, double price) {
       //handles the confirm
       //we need to check if we are doing a variation add or an addition add
        boolean error;
        int pos;
        if (operation==Operation.ADD_ADDITION){
            //then we are adding an addition
            error=(!menu.getMenu().get(catPosition).getItems().get(itemPosition).addAdditional(name,price));
            pos = menu.getMenu().get(catPosition).getItems().get(itemPosition).getAdditions().size()-1;
        } else if (operation==Operation.ADD_VARIATION){
            //then we are adding a variation
            error=(!menu.getMenu().get(catPosition).getItems().get(itemPosition).addVariant(name,price));
            pos = menu.getMenu().get(catPosition).getItems().get(itemPosition).getVariants().size()-1;
        } else if (operation==Operation.EDIT_ADDITION){
            //then we are editing an addition
            pos = ((PriceRecyclerAdapter) additionsList.getAdapter()).getSelectedPosition();
            error = (!menu.getMenu().get(catPosition).getItems().get(itemPosition).editAddition(pos,name,price));
        } else {
            //then we are editing a variant
            pos = ((PriceRecyclerAdapter) variationsList.getAdapter()).getSelectedPosition();
            error = (!menu.getMenu().get(catPosition).getItems().get(itemPosition).editVariant(pos,name,price));
        }

        //checking if there was an error or not
        if (error){
            //then adding a variation or addition was not successful because the same name variant/addition
            String errorMessage;
            if (operation==Operation.ADD_ADDITION){
               errorMessage="Addition Already Exists!" ;
            } else if (operation==Operation.ADD_VARIATION){
               errorMessage="Variant Already Exists!";
            } else if (operation==Operation.EDIT_ADDITION){
                errorMessage="Another Addition With That Name Exists!";
            } else {
               //then we were editing a variant
                errorMessage="Another Variant With That Name Exists!";
            }

            //building and showing error
            new AlertDialog.Builder(this)
                    .setTitle("ERROR!")
                    .setMessage(errorMessage)
                    .setPositiveButton("OK",(dialog,id)->{})
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
           //then we should select the new position of the added/edited item
            PriceRecyclerAdapter varAdapt = (PriceRecyclerAdapter) variationsList.getAdapter();
            PriceRecyclerAdapter addAdapt = (PriceRecyclerAdapter) additionsList.getAdapter();
            varAdapt.select(pos);
            addAdapt.select(pos);
            varAdapt.notifyDataSetChanged();
            addAdapt.notifyDataSetChanged();
        }
    }


    //for returning just the price, when editing the base item
    @Override
    public void onReturnPrice(double price) {
        //so we just change the base item price here
        menu.getMenu().get(catPosition).getItems().get(itemPosition).setBasePrice(price);
        variationsList.getAdapter().notifyDataSetChanged();
    }
}
