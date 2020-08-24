package com.example.restaurantserviceutility;

import android.content.ReceiverCallNotAllowedException;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import RestaurantClasses.ServiceTools.MenuItem;
import RestaurantClasses.ServiceTools.OrderItem;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChooseVariantAdditionsPopup#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChooseVariantAdditionsPopup extends DialogFragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private MenuItem item;
    private Button cancelButton;
    private Button confirmButton;
    private RecyclerView variantList;
    private RecyclerView additionsList;
    private TextView itemName;
    private TextView totalPrice;
    private boolean itemConfirmed=false;

    //to help with my onClicks
    private double variantPrice=0;
    private double additionTotal=0;

    public ChooseVariantAdditionsPopup() {
        // Required empty public constructor
    }

    //interface required for context callback on clicking confirm button
    public interface OnItemSelect{
       public void addToOrder(OrderItem c);
    }

   // TODO: Rename and change types and number of parameters
    public static ChooseVariantAdditionsPopup newInstance(MenuItem item) {
        ChooseVariantAdditionsPopup fragment = new ChooseVariantAdditionsPopup();
        Bundle args = new Bundle();
        args.putSerializable("item",item);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            item=(MenuItem) getArguments().getSerializable("item");
        } else {
            item=null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.choose_variant_additions_popup, container, false);
        cancelButton = v.findViewById(R.id.variantAdditionChooseCancelButton);
        confirmButton = v.findViewById(R.id.variantAdditionChooseConfirmButton);
        itemName = v.findViewById(R.id.variantAdditionItemName);
        totalPrice = v.findViewById(R.id.variantAdditionChoosePriceOutput);
        variantList = v.findViewById(R.id.variantSelectList);
        additionsList = v.findViewById(R.id.additionsSelectList);

        //setting text
        itemName.setText(item.getName());

        //setting my onclicks
        //basically on a variant select, i need to adjust the base price
        //on any addition select, i also need to adjust the price

        MyRecyclerAdapter.Clickable voidClick = (p->{});
        MyRecyclerAdapter.Clickable variantPriceClick = (p->{variantPrice=item.getVariantPrices().get(p);
                                                            totalPrice.setText(String.format("%.2f", variantPrice+additionTotal));});

        //additionselect onclicks
        MultipleSelectPriceRecyclerAdapter.multipleSelect mult = new MultipleSelectPriceRecyclerAdapter.multipleSelect() {
            @Override
            public void addPrice(int position) {
               additionTotal+=item.getAdditionPrices().get(position);
                totalPrice.setText(String.format("%.2f", variantPrice+additionTotal));
            }

            @Override
            public void removePrice(int position) {
                additionTotal-=item.getAdditionPrices().get(position);
                totalPrice.setText(String.format("%.2f", variantPrice+additionTotal));
            }
        };

        //setting layout managers
        variantList.setLayoutManager(new LinearLayoutManager(getContext()));
        additionsList.setLayoutManager(new LinearLayoutManager(getContext()));

        //creating adapters needed
        variantList.setAdapter(
                new PriceRecyclerAdapter(getContext(),item.getVariants(),item.getVariantPrices(),voidClick)
        );

        additionsList.setAdapter(
                new MultipleSelectPriceRecyclerAdapter(getContext(),item.getAdditions(),item.getAdditionPrices(),voidClick,mult)
        );


        return v;
    }

    public void confirm(View v){
       //this is just an intent to go back
        dismiss();
        if (itemConfirmed){
            int varPos = ((PriceRecyclerAdapter) variantList.getAdapter()).getSelectedPosition();
            ArrayList<Integer> addsPos = ((MultipleSelectPriceRecyclerAdapter) additionsList.getAdapter()).getSelectedPositions();
            OrderItem toPlace = new OrderItem(item.getName(),item.getVariants().get(varPos),variantPrice);
            for (int i:addsPos){
                toPlace.addAddition(item.getAdditions().get(i),item.getAdditionPrices().get(i));
            }
            ((OnItemSelect) getContext()).addToOrder(toPlace);
        }
    }

    public void cancel(View v){
        dismiss();
    }
}
