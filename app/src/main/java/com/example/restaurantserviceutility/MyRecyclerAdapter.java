package com.example.restaurantserviceutility;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.function.Consumer;


/**
 * Class to set up viewholders for my RecyclerViews. I will just be working with strings.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder>{
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder



    //generic list recycler view stuff
    private List e;
    private Context context;
    //this is for functional abstraction
    private Clickable clickable;
    private int selectedPosition=-1;

    public MyRecyclerAdapter(Context context, List e,Clickable clickable ){
        this.e=e;
        this.context=context;
        this.clickable=clickable;
        //highlighting the first item as selected
        if (!e.isEmpty()){
            selectedPosition=0;
            clickable.onItemSelected(0);
        }


    }

    //clickable interface
    public interface Clickable{
        void onItemSelected( int position);
    }

    //viewholder class
    public class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        protected TextView textView;
        public MyViewHolder(View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.recyclerText);


        }




    }


    // Create new views (invoked by the layout manager)
    @Override
    public MyRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_layout, parent, false);

        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(e.get(position).toString());
        if(selectedPosition==position)
            holder.itemView.setBackgroundColor(Color.LTGRAY);
        else
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition=position;
                notifyDataSetChanged();
                clickable.onItemSelected(position);

            }
        });



    }

    // Replace the contents of a view (invoked by the layout manager)
    /*
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText(e.get(position).toString());



    }*/

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return e.size();
    }

    //removal logic
    public boolean remove(){
        if (selectedPosition<0){
            //then list is empty
            return false;
        } else {
            //then we should remove and then notify list changes
            e.remove(selectedPosition);
            //notifying the list
            notifyItemRemoved(selectedPosition);
            notifyItemRangeChanged(selectedPosition,e.size());
            if (e.size()>0){
               clickable.onItemSelected(selectedPosition);
            }
            return true;
        }
    }

    //select logic
    public void select(int pos){
        selectedPosition=pos;
        notifyDataSetChanged();
        clickable.onItemSelected(pos);
    }
}
