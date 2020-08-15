package com.example.restaurantserviceutility;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NamePriceInputPopup#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NamePriceInputPopup extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText nameText;
    private EditText priceText;
    private Button cancelButton;
    private Button confirmButton;

    // TODO: Rename and change types of parameters


    public NamePriceInputPopup() {
        // Required empty public constructor
    }

    //interface required for fragment returning data needed
    public interface onConfirm{
        void returnData(String name,double price);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NamePriceInputPopup.
     */
    // TODO: Rename and change types and number of parameters
    public static NamePriceInputPopup newInstance() {
        NamePriceInputPopup fragment = new NamePriceInputPopup();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //dont have any parameters in bundle, so i dont need the below stuff
        /*
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

         */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.name_price_input_popup, container, false);
        nameText = v.findViewById(R.id.nameDoubleInputText);
        priceText = v.findViewById(R.id.priceEnterText);
        cancelButton = v.findViewById(R.id.cancelDoubleButton);
        confirmButton = v.findViewById(R.id.confirmDoubleButton);

        //handling input error cases
        //possible errors:
        //price is negative or blank
        //text is blank

        //handling cancelButton (just a dismiss)
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        //handling confirm button and error checking
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //first check if name is blank
                //for calling method on parent
                onConfirm c = (onConfirm) getContext();

                //checking if blank
                String n = nameText.getText().toString().toLowerCase().trim();
                String nums = priceText.getText().toString().trim();
                if (n.isEmpty()){
                    //then we throw an error and ask the user to please enter a name
                    new AlertDialog.Builder(getActivity())
                            .setTitle("ERROR!")
                            .setMessage("Please fill out a name! (anything not blank)")
                            .setPositiveButton("OK",(dialog,id)->{nameText.setText("");})
                            .show();
                } else if (nums.isEmpty()){
                    new AlertDialog.Builder(getActivity())
                            .setTitle("ERROR!")
                            .setMessage("Please Enter a Price! (non-blank non-negative)")
                            .setPositiveButton("OK",(dialog,id)->{priceText.setText("");})
                            .show();
                } else {
                    //then we have a valid name and non-blank price to feed to the logic
                    //still need to check if price is non-negative
                    double num = Double.parseDouble(nums);
                    if (num<0){
                        new AlertDialog.Builder(getActivity())
                                .setTitle("ERROR!")
                                .setMessage("Non-Negative Prices Only! (non-blank non-negative)")
                                .setPositiveButton("OK",(dialog,id)->{priceText.setText("");})
                                .show();
                    } else {

                        c.returnData(n,num);
                        dismiss();
                    }
                }

            }
        });
        return v;
    }



}
