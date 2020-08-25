package com.example.restaurantserviceutility;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyTextInputDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyTextInputDialog extends DialogFragment {

    private String dialogTitle;


    //interface for returning input from the dialog
    public interface ReturnText{
        public void onReturnText(String s);
    }

    public MyTextInputDialog() {
        // Required empty public constructor
    }


    public static MyTextInputDialog newInstance(String dialogTitle) {
        MyTextInputDialog fragment = new MyTextInputDialog();
        Bundle args = new Bundle();
        args.putString("title",dialogTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dialogTitle = getArguments().getString("title");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_text_input_dialog, container, false);
        TextView title = v.findViewById(R.id.textInputTitle);
        title.setText(dialogTitle);
        return v;
    }

    public void cancel(View v){
        dismiss();
    }

    public void confirm(View v){
        dismiss();
        ((ReturnText) getContext()).onReturnText(
                ((TextView)v.findViewById(R.id.textInput))
                .getText()
                .toString()
        );
    }

}

