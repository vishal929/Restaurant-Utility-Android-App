package com.example.restaurantserviceutility;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
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
 * Use the {@link EnterNamePopup#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EnterNamePopup extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private int mParam1;

    private EditText nameText;
    private Button confirmButton;
    private Button backButton;

    public EnterNamePopup() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param param1 Parameter 1.
     * @return A new instance of fragment EnterNamePopup.
     */
    // TODO: Rename and change types and number of parameters
    public static EnterNamePopup newInstance(int param1 ) {
        EnterNamePopup fragment = new EnterNamePopup();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1,param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.enter_name_popup, container, false);
        nameText = v.findViewById(R.id.enterNameText);
        confirmButton=v.findViewById(R.id.enterNameConfirmButton);
        backButton=v.findViewById(R.id.cancelEnterNameButton);

        //handling confirmation and checking of name
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toConsider = nameText.getText().toString().trim().toLowerCase();
                if (toConsider.isEmpty()){
                    //then this is an invalid name and we should show an alert dialog
                    new AlertDialog.Builder(getActivity())
                            .setTitle("ERROR!")
                            .setMessage("Please fill out a name! (anything not blank)")
                            .setPositiveButton("OK",(dialog,id)->{nameText.setText("");})
                            .show();
                } else {
                    //then this is a valid name and we should bundle up the name and go to the view specified
                    Bundle b = new Bundle();
                    Activity current = getActivity();
                    Intent i;
                    switch (mParam1) {
                        case 0:
                            //then we go to waiter screen
                            i = new Intent(current,WaiterScreen.class);
                            i.putExtra("name",toConsider);
                            startActivity(i);
                            break;
                        case 1:
                            //then we go to console screen
                            i = new Intent(current,ConsoleScreen.class);
                            i.putExtra("name",toConsider);
                            startActivity(i);
                            break;
                        case 2:
                            //then we go to kitchen/bar screen
                            i = new Intent(current,KitchenScreen.class);
                            i.putExtra("name",toConsider);
                            startActivity(i);
                            break;
                    }

                }
            }
        });

        //handling going back from the fragment
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

            }

        });

        return v;
    }


}
