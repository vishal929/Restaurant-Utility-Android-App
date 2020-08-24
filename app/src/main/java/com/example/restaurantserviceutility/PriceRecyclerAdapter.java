package com.example.restaurantserviceutility;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.restaurantserviceutility.MyRecyclerAdapter.Clickable;

import org.w3c.dom.Text;

//this is a special class to help display names and prices, but keep features of the basic adapter I made
public class PriceRecyclerAdapter extends RecyclerView.Adapter<PriceRecyclerAdapter.PriceViewHolder>{
    //this adapter has an extra associated list: the price list
    protected List e;
    protected List prices;
    protected Context context;
    protected Clickable c;
    protected int selectedPosition=-1;

    public PriceRecyclerAdapter(Context context, List e, List prices, Clickable clickable) {
        this.context=context;
        this.e=e;
        this.prices=prices;
        this.c=clickable;
    }


    //my custom viewholder
    public class PriceViewHolder extends RecyclerView.ViewHolder{
        protected TextView name;
        protected TextView price;

        public PriceViewHolder(View v){
           super(v);
           name = (TextView) v.findViewById(R.id.thingName);
           price = (TextView) v.findViewById(R.id.thingPrice);
        }
    }


    @NonNull
    @Override
    public PriceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_price_layout,parent,false);
        PriceViewHolder vh = new PriceViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull PriceViewHolder holder, int position) {
        holder.name.setText(e.get(position).toString());
        holder.price.setText(prices.get(position).toString());
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
        //there are only as many prices as names
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
            e.remove(selectedPosition);
            prices.remove(selectedPosition);
            //notifying the list
            notifyItemRemoved(selectedPosition);
            notifyItemRangeChanged(selectedPosition,e.size());

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
