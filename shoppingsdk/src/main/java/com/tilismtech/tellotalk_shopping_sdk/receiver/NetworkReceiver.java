package com.tilismtech.tellotalk_shopping_sdk.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import com.tilismtech.tellotalk_shopping_sdk.utils.LoadingDialog;
import com.tilismtech.tellotalk_shopping_sdk.utils.NoInternetDetection;
import com.tilismtech.tellotalk_shopping_sdk.utils.Utility;

public class NetworkReceiver extends BroadcastReceiver {
    NoInternetDetection loadingDialog;

    public NetworkReceiver(NoInternetDetection loadingDialog) {
    this.loadingDialog = loadingDialog;
    }

    @Override
    public void onReceive(Context context, Intent intent) {


        if(Utility.isNetworkAvailable(context)){
            //Toast.makeText(context, "Connected...", Toast.LENGTH_SHORT).show();
            loadingDialog.dismissDialog();
        }else{
           // Toast.makeText(context, "Not Connected...", Toast.LENGTH_SHORT).show();
            loadingDialog.showDialog();
        }
    }
}
