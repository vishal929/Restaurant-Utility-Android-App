package com.example.restaurantserviceutility;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;

import java.io.File;

public class MenuScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);


        //we should check if a menu exists on this device
        //if no menu exists on this device or an updated menu is found on the local network, we will update our menu to match the latest version

        //checking to see if menu exists
        //this is a file which is my serialized menuObj object
        File menuObj = new File(this.getFilesDir(),"menu");

        //checking devices on the network for the most up to date menu serialization
        WifiP2pManager netMan = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        WifiP2pManager.Channel channel = netMan.initialize(this,getMainLooper(),null);



    }
}
