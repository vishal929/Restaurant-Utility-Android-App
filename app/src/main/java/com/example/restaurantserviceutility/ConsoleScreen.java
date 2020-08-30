package com.example.restaurantserviceutility;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import RestaurantClasses.Service.Console;
import RestaurantClasses.ServiceTools.Order;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class ConsoleScreen extends AppCompatActivity implements MyTextInputDialog.ReturnText {

    private Button viewComplaintButton;
    private Button placeComplaintButton;
    private RecyclerView orderList;
    private RecyclerView itemList;
    private TextView price;

    private Console console;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_console_screen);

        //the name for the console should be in the bundle

        String name = savedInstanceState.getString("name");
        //making a console
        console = new Console(name);

        //price text
        price = findViewById(R.id.consoleOrderTotalText);

        //setting name for title
        ((TextView) findViewById(R.id.consoleTitleText)).setText("Console : "+name);

        //hiding some buttons
        viewComplaintButton = findViewById(R.id.consoleViewComplaintButton);
        placeComplaintButton = findViewById(R.id.consolePlaceComplaintButton);

        viewComplaintButton.setVisibility(GONE);
        placeComplaintButton.setVisibility(GONE);

        //setting up my lists and adapters
        orderList = findViewById(R.id.consoleOrderList);
        itemList = findViewById(R.id.consoleItemsList);

        orderList.setLayoutManager(new LinearLayoutManager(this));
        itemList.setLayoutManager(new LinearLayoutManager(this));

        //setting up a voidclick
        MyRecyclerAdapter.Clickable voidClick = (p)->{};

        //setting up onClick for my orderList
        MyRecyclerAdapter.Clickable orderClick = new MyRecyclerAdapter.Clickable() {
            @Override
            public void onItemSelected(int position) {
                //basically I want to just adjust the item list to match the order selected
                Order selected = console.getOrders().get(position);
                OrderAdapter itemAdapt = new OrderAdapter(getApplicationContext(),selected.getItems(),selected.getAmount(),voidClick);
                if (selected.getItems().size()>0){
                    itemAdapt.select(0);
                }
                //changing the price
                price.setText(""+selected.getTotalPrice());
                //need to check if the order has a complaint
                if (selected.getComplaint()!=null){
                   //then we view the complaint
                    placeComplaintButton.setVisibility(GONE);
                    viewComplaintButton.setVisibility(VISIBLE);

                } else {
                    //then we can place a complaint
                    viewComplaintButton.setVisibility(GONE);
                    placeComplaintButton.setVisibility(VISIBLE);

                }

            }
        };

        orderList.setAdapter(
                new MyRecyclerAdapter(this,console.getOrders(),orderClick)
        );

        //selecting the first order and the first item, if any
        if (console.getOrders().size()>0){
            ((MyRecyclerAdapter) orderList.getAdapter()).select(0);
        }

    }

    public void goBack(View v){
       Intent back = new Intent(this,MainActivity.class);
       //todo: synchronization with internet/storage
       startActivity(back);
    }

    //clears list for a new day (will show popup saying data will be lost, make sure to export data and make a backup before clearing)
    public void clearAll(View v){
        new AlertDialog.Builder(this)
                .setTitle("WARNING!")
                .setMessage("Make Sure You Export Data Before Clearing!")
                .setPositiveButton("Confirm Clear",(dialog,id)->{
                   console.getOrders().clear();
                   orderList.getAdapter().notifyDataSetChanged();
                   itemList.getAdapter().notifyDataSetChanged();
                })
                .setNegativeButton("Cancel Clear",(dialog,id)->{})
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void placeComplaint(View v){

        MyTextInputDialog complain = MyTextInputDialog.newInstance("Enter the Complaint:");
        FragmentManager frag = getSupportFragmentManager();
        complain.show(frag,"Complaint");
    }

    public void viewComplaint(View v){
        int pos = ((MyRecyclerAdapter) orderList.getAdapter()).getSelectedPosition();
        MyTextViewPopup viewComplaint = MyTextViewPopup.newInstance(console.getOrders().get(pos).getComplaint(),"View Complaint");
        FragmentManager frag = getSupportFragmentManager();
        viewComplaint.show(frag,"Complaint");
    }

    public void cancelOrder(View v){
        //can only cancel an order if it wasnt fulfilled yet
        //todo: kitchen synchronization
    }

    public void deleteItemAddition(View v){
        //removes an item/addition from an order, should
    }

    public void exportData(View v){
        //todo: figure out best way to export this data
    }

    public void viewTime(View v){
        // we will just create a string and use my textview popup to display this
        Order toGet = console.getOrders().get(
                ((MyRecyclerAdapter) orderList.getAdapter()).getSelectedPosition()
        );
        String toShow ="Date: "+toGet.getIssued().getMonth().toString().substring(0,3);
        toShow+=" - "+toGet.getIssued().getDayOfMonth()+" - "+toGet.getIssued().getYear() + "\n";

        toShow += "Time Placed : "+toGet.getIssued().getHour()+ " : "+toGet.getIssued().getMinute()+"\n";

        toShow += "Time Fulfilled: "+toGet.getFulfilled().getHour()+ " : "+toGet.getFulfilled().getMinute()+"\n";

        toShow += "Time Served: "+toGet.getServed().getHour()+ " : "+toGet.getServed().getMinute()+"\n";

        MyTextViewPopup time = MyTextViewPopup.newInstance(toShow, "Time Details:");
        FragmentManager frag = getSupportFragmentManager();
        time.show(frag,"Time");
    }

    public void toMenu(View v){
        Intent menu = new Intent(this,MenuScreen.class);
        startActivity(menu);
    }


    @Override
    public void onReturnText(String s) {
       //basically we just set a complaint, thats all
        Order toComplain = console.getOrders().get(
                ((MyRecyclerAdapter) orderList.getAdapter())
                        .getSelectedPosition()
        );
        toComplain.setComplaint(s);
        placeComplaintButton.setVisibility(GONE);
        viewComplaintButton.setVisibility(VISIBLE);
    }
}
