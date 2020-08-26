package com.example.restaurantserviceutility;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyTextViewPopup#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyTextViewPopup extends DialogFragment {
    private String text;
    private String title;



    public MyTextViewPopup() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MyTextViewPopup newInstance(String text,String title) {
        MyTextViewPopup fragment = new MyTextViewPopup();
        Bundle args = new Bundle();
        args.putString("text",text);
        args.putString("title",title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.text = getArguments().getString("text");
            this.title=getArguments().getString("title");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_my_text_view_popup, container, false);
        TextView title = v.findViewById(R.id.textPopupTitle);
        TextView text = v.findViewById(R.id.viewTextReal);
        Button back = v.findViewById(R.id.viewTextBackButton);
        title.setText(this.title);
        text.setText(this.text);
        back.setOnClickListener((p->{dismiss();}));
        return v;
    }
}
