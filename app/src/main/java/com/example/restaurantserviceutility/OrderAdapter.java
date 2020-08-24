package com.example.restaurantserviceutility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.restaurantserviceutility.MyRecyclerAdapter.Clickable;


import RestaurantClasses.ServiceTools.OrderItem;

//purpose of this adapter is to include 3 items for an order, the items, the amount of each item, and the price next to it
//since we are using this adapter to build an order, we should always start out with empty lists
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context context;
    private List<OrderItem> e;
    private List<Integer> amounts;
    private Clickable c;
    private int selectedPosition=-1;
    public OrderAdapter(Context context, List<OrderItem> e, List<Integer> amounts,  Clickable clickable) {
        this.context=context;
        this.e=e;
        this.amounts=amounts;
        this.c=clickable;
    }

    //my viewholder
    public class OrderViewHolder extends RecyclerView.ViewHolder{
        protected TextView name;
        protected TextView price;
        protected TextView amount;
        protected RecyclerView additions;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tripleName);
            amount = (TextView) itemView.findViewById(R.id.tripleAmount);
            price = (TextView) itemView.findViewById(R.id.triplePrice);
            additions = (RecyclerView) itemView.findViewById(R.id.additionsNamePriceList);
        }
    }


    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.triple_list_layout,parent,false);
        OrderViewHolder vh = new OrderViewHolder(v);
        return vh;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.name.setText(e.get(position).toString()+":"+e.get(position).getVariantChosen());
        holder.price.setText(String.valueOf(e.get(position).getVariantPrice()));
        String amountText = "x"+amounts.get(position).toString();
        holder.amount.setText(amountText);
        holder.additions.setLayoutManager(new LinearLayoutManager(context));
        //setting our price adapter for the additions
        holder.additions.setAdapter(
                new PriceRecyclerAdapter(context,e.get(position).getAdditionsChosen(),e.get(position).getAdditionPrices(),(v->{}))
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

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return e.size();
    }

    public int getSelectedPosition(){
        return selectedPosition;
    }


    public boolean remove(){
        //removes the currently selected position
        if (selectedPosition<0 || e.size()==0){
            //then list is empty
            return false;
        } else {
            //then we should remove and then notify list changes
            //alternative logic for amounts
            int amount = amounts.get(selectedPosition);
            if (amount>1){
               amounts.remove(selectedPosition);
               amount--;
               amounts.add(selectedPosition,amount);
            } else {
                //then there is only one of this item and we should remove it
                e.remove(selectedPosition);
                amounts.remove(selectedPosition);
            }

            //notifying the list
            notifyDataSetChanged();

            if (e.size()>0){
                if (selectedPosition>(e.size()-1)){
                    //then i should select the end
                    //select(e.size()-1);
                    selectedPosition=e.size()-1;
                } else {
                    //select(selectedPosition);
                }

            }
            return true;
        }
    }

    public void select(int pos){
        selectedPosition=pos;
        notifyDataSetChanged();
        c.onItemSelected(pos);
    }




}
