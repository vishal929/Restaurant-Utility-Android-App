package com.example.restaurantserviceutility;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

//purpose of this class is to be a priceRecycleradapter, but with multiple selection support
public class MultipleSelectPriceRecyclerAdapter extends PriceRecyclerAdapter {

    private ArrayList<Integer> selectedPositions;
    private multipleSelect select;

    public MultipleSelectPriceRecyclerAdapter(Context context, List e, List prices, MyRecyclerAdapter.Clickable clickable,multipleSelect select) {
        super(context, e, prices, clickable);
    }

    //two callbacks for multiple select add and remove
    public interface multipleSelect{
        public void addPrice(int position);
        public void removePrice(int position);
    }


    @Override
    public void onBindViewHolder(PriceViewHolder holder,int position){
       //need to include multiple select logic
        holder.name.setText(e.get(position).toString());
        holder.price.setText(prices.get(position).toString());
        if(selectedPositions.contains(position)) {
            holder.itemView.setBackgroundColor(Color.LTGRAY);
        }
        else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPositions.contains(position)){
                    //then i should remove it
                    selectedPositions.remove(Integer.valueOf(position));
                    notifyDataSetChanged();
                    //click interface
                    select.removePrice(position);
                } else {
                    //then i should add it
                    selectedPositions.add(position);
                    notifyDataSetChanged();
                    //click interface
                    select.addPrice(position);
                }

            }
        });
    }

    public ArrayList<Integer> getSelectedPositions(){
        return this.selectedPositions;
    }
}
