package com.example.restaurantserviceutility;

import android.app.AlertDialog;
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
 * Use the {@link OnlyPriceInput#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OnlyPriceInput extends DialogFragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private EditText price;
    private Button confirmButton;
    private Button cancelButton;
    private JustPrice ret;


    //interface for communication with underlying activity
    public interface JustPrice{
        public void onReturnPrice(double price);
    }

    public OnlyPriceInput() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment only_price_input.
     */
    // TODO: Rename and change types and number of parameters
    public static OnlyPriceInput newInstance() {
        OnlyPriceInput fragment = new OnlyPriceInput();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setting up our onClicks
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.solely_price_layout, container, false);
        //setting up our buttons and text
        price = v.findViewById(R.id.justPriceInput);
        confirmButton=v.findViewById(R.id.justPriceConfirm);
        cancelButton= v.findViewById(R.id.justPriceCancel);

        //setting cancel onCLick action
        cancelButton.setOnClickListener(v1 -> dismiss());

        //setting confirm onClick action
        confirmButton.setOnClickListener(v1 -> {
            //we need to check the string
            String n = price.getText().toString().trim();
            if (n.isEmpty()){
                new AlertDialog.Builder(getActivity())
                        .setTitle("Blank Field!")
                        .setMessage("Please Fill in a Price!")
                        .setPositiveButton("OK",(dialog,id)->dismiss())
                        .show();
            } else{
                ret.onReturnPrice(Double.parseDouble(n));
            }
        });

        return v;
    }
}
