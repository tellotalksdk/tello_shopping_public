package com.tilismtech.tellotalk_shopping_sdk_app.receiver;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BluetoothReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);


        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            Toast.makeText(context, "Device Found", Toast.LENGTH_SHORT).show();
        }
        else if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
            Toast.makeText(context, "Device Found", Toast.LENGTH_SHORT).show();

            //Device is now connected
        }
        else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
            Toast.makeText(context, "Device Found", Toast.LENGTH_SHORT).show();

            //Done searching
        }
        else if (BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED.equals(action)) {
            Toast.makeText(context, "Device Found", Toast.LENGTH_SHORT).show();

            //Device is about to disconnect
        }
        else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
            Toast.makeText(context, "Device Found", Toast.LENGTH_SHORT).show();

           //Device has disconnected
        }
    }
}
