package com.example.restaurantserviceutility;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;

//this class handles all networking logic (menu editing, order submitting, complaints, etc.)
public class NetworkingLogic {
    //embedded class for the receiver
    public class myReceiver extends BroadcastReceiver{

        //manager for p2p connections
        private WifiP2pManager manager;
        //channel for connections
        private WifiP2pManager.Channel channel;
        //activity for which my p2p service is alive
        private Activity activity;

        public myReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel, Activity activity){
            this.manager=manager;
            this.channel=channel;
            this.activity=activity;
        }

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }


}
