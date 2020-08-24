package com.example.restaurantserviceutility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import RestaurantClasses.ServiceTools.OrderItem;

//class to show an order for the kitchen
public class KitchenOrderAdapter extends RecyclerView.Adapter<KitchenOrderAdapter.KitchenViewHolder> {

    //need a list of unique orderItems and a list of amounts
    private List<OrderItem> e;
    private List<Integer> amounts;
    private Context context;
    private MyRecyclerAdapter.Clickable c;
    private int selectedPosition=-1;

    public KitchenOrderAdapter(Context context,List<OrderItem> e, List<Integer> amounts,  MyRecyclerAdapter.Clickable c) {
        this.e = e;
        this.amounts = amounts;
        this.context = context;
        this.c = c;
    }

    public class KitchenViewHolder extends RecyclerView.ViewHolder{
        protected TextView name;
        protected TextView amount;
        protected RecyclerView additionsList;
        public KitchenViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.kitchenItemName);
            amount=itemView.findViewById(R.id.kitchenItemAmount);
            additionsList=itemView.findViewById(R.id.kitchenAdditionList);
        }
    }


    @NonNull
    @Override
    public KitchenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.kitchen_order_list_layout,parent,false);
        KitchenViewHolder vh = new KitchenViewHolder(v);
        return vh;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull KitchenViewHolder holder, int position) {
        //setting up my views
        holder.name.setText(e.get(position).toString()+":"+e.get(position).getVariantChosen());
        String amountText = "x"+amounts.get(position).toString();
        holder.amount.setText(amountText);
        holder.additionsList.setLayoutManager(new LinearLayoutManager(context));
        //setting our price adapter for the additions
        holder.additionsList.setAdapter(
                new MyRecyclerAdapter(context,e.get(position).getAdditionsChosen(),(v->{}))
        );

        if(selectedPosition==position) {
            holder.itemView.setBackgroundColor(Color.LTGRAY);
        }
        else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition=position;
                notifyDataSetChanged();
                //clickable interface
                c.onItemSelected(position);

            }
        });
    }


    @Override
    public int getItemCount() {
        return e.size();
    }

    public void select(int pos){
        selectedPosition=pos;
        notifyDataSetChanged();
        c.onItemSelected(pos);
    }



}
