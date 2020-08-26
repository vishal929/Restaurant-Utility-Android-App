package com.example.restaurantserviceutility;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import RestaurantClasses.Service.Kitchen;
import RestaurantClasses.Service.Waiter;
import RestaurantClasses.ServiceTools.Order;

import static android.view.View.GONE;

//class to view served and fulfilled orders (served from waiters perspective and fulfilled from kitchens perspective)
public class ServedFulfilledOrdersScreen extends AppCompatActivity implements MyTextInputDialog.ReturnText {
    private List<Order> fulfilledServedOrders;
    private boolean fromWaiter;
    private Button placeComplaintButton;
    private Button viewComplaintButton;
    private TextView prepComments;
    private TextView title;
    private RecyclerView orderList;
    private RecyclerView orderItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_served_fulfilled_orders_screen);
        placeComplaintButton=findViewById(R.id.placeComplaintButton);
        viewComplaintButton=findViewById(R.id.viewComplaintButton);
        prepComments=findViewById(R.id.servedFulfilledPreparationTextReal);
        title=findViewById(R.id.servedFulfilledOrdersTitle);
        orderList=findViewById(R.id.servedFulfilledOrderList);
        orderItemList = findViewById(R.id.servedFulfilledItemList);

        orderList.setLayoutManager(new LinearLayoutManager(this));
        orderItemList.setLayoutManager(new LinearLayoutManager(this));

        //basically we have to see whether we came from the kitchen or from the waiter screen and adjust accordingly
        Bundle extras = getIntent().getExtras();
        fromWaiter = extras.getBoolean("waiter");

        //the kitchen can only view complaints
        placeComplaintButton.setVisibility(GONE);
        viewComplaintButton.setVisibility(GONE);

        if (fromWaiter){
            //then we came from the waiter
            Waiter waiter = (Waiter) extras.getSerializable("waiter");
            fulfilledServedOrders=waiter.getServedOrders();
            title.setText("Served Orders");

        } else {
            //then we came from the kitchen
            Kitchen kitchen = (Kitchen) extras.getSerializable("kitchen");
            fulfilledServedOrders=kitchen.getFulfilled();
            title.setText("Fulfilled Orders");
        }

        //need an orderadapter for the itemlist and a myRecyclerAdapter for the orderList
        //setting my onClicks
        MyRecyclerAdapter.Clickable voidClick = (p->{});
        MyRecyclerAdapter.Clickable orderClick = (p->{
            String complaint = fulfilledServedOrders.get(p).getComplaint();
            if (complaint!=null){
               viewComplaintButton.setVisibility(View.VISIBLE);
            } else {
                viewComplaintButton.setVisibility(GONE);
                if (fromWaiter){
                    placeComplaintButton.setVisibility(View.VISIBLE);
                } else {
                    placeComplaintButton.setVisibility(GONE);
                }
            }
            //setting the comment to the right one
            if (fulfilledServedOrders.get(p).getPrepComments()!=null){
                prepComments.setText(fulfilledServedOrders.get(p).getPrepComments());
            } else {
                //then I set the text to null/ empty?
                prepComments.setText(null);
            }

            //setting up itemlist
            orderItemList.setAdapter(
                new OrderAdapter(this,fulfilledServedOrders.get(p).getItems(),fulfilledServedOrders.get(p).getAmount(),voidClick)
            );
        });

        //creating adapters
        MyRecyclerAdapter orderAdapt = new MyRecyclerAdapter(this,fulfilledServedOrders,orderClick);



    }

    public void goBack(View v){
        if (fromWaiter){
           Intent toWaiter = new Intent(this,WaiterScreen.class);
           startActivity(toWaiter);
        } else {
           Intent toKitchen = new Intent(this,KitchenScreen.class);
           startActivity(toKitchen);
        }
    }

    public void clear(View v){
       fulfilledServedOrders.clear();
    }

    //just a way for the waiter to place a complaint, we will use my simple text input here
    public void placeComplaint(View v){
        MyTextInputDialog complaint = MyTextInputDialog.newInstance("Enter a Complaint:");
        FragmentManager frag = getSupportFragmentManager();
        complaint.show(frag,"Complaint");
    }

    //way for both kitchen and waiter to view the complaint
    public void viewComplaint(View v){
        //it will just be a simple popup to view the string of the complaint
        MyTextViewPopup viewComplaint = MyTextViewPopup.newInstance(fulfilledServedOrders.get(
                ((MyRecyclerAdapter) orderList.getAdapter())
                .getSelectedPosition()
        ).getComplaint()
        ,"Complaint:");
        FragmentManager frag = getSupportFragmentManager();
        viewComplaint.show(frag,"Complaint View");
    }

    @Override
    public void onReturnText(String s) {
        fulfilledServedOrders.get(
                ((MyRecyclerAdapter) orderList.getAdapter())
                .getSelectedPosition()
        ).makeComplaint(s);

        placeComplaintButton.setVisibility(GONE);
        viewComplaintButton.setVisibility(View.VISIBLE);
    }
}
