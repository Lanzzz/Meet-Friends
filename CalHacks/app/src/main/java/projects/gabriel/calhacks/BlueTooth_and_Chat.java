package projects.gabriel.calhacks;

        //important generic API for bluetooth.
//http://developer.android.com/guide/topics/connectivity/bluetooth.html
import android.Manifest;
import android.bluetooth.*;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.Manifest.permission;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.app.*;
import android.content.IntentFilter;
import android.content.Context;
import android.widget.ArrayAdapter;


import java.util.Set;


/**
 * Created by Landon on 10/10/2015.
 */


public class BlueTooth_and_Chat {

    public static String BLUETOOTH = permission.BLUETOOTH;

    //current users bluetooth object
    BluetoothAdapter myBTooth;

    //bts is a single healthy connection
    BluetoothSocket bts;


    BlueTooth_and_Chat() {
        myBTooth = BluetoothAdapter.getDefaultAdapter();
        if (myBTooth == null) {
            // Device does not support Bluetooth
            return;
        }

        //only accept one connection at a time
        //needs to be interruptable
        Thread t = new Thread( new Runnable( )
        { public void run() { checkIfBlueToothEnabled();}});

        Thread receiveInterrupt = new Thread( new Runnable( )
        { public void run() { bts.connect();}});

        Thread closeInterrupt = new Thread( new Runnable( )
        { public void run() { bts.close();}});

    }

    public void checkIfBlueToothEnabled() {
        int REQUEST_ENABLE_BT;

        //check if bluetooth is on
        //i know this is broken
        if (!myBTooth.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        //if it was enabled, will return int RESULT_OK = -1
        //else RESULT_CANCELLED=0

        //if()

    /*
    * We are done with the initialization of a single bluetooth
    * for the current user.
    *
    * */
    }

    ArrayAdapter mArrayAdapter;

    public void checkPairedDevices() {
        Set<BluetoothDevice> pairedDevices = myBTooth.getBondedDevices();

    // If there are paired devices, show each name
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        }

    }

    //this is not a function
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                // When discovery finds a device

                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    // Get the BluetoothDevice object from the Intent
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    // Add the name and address to an array adapter to show in a ListView
                    mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                }
            }
    };

    // Register the BroadcastReceiver
    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
    Intent intent = registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy

}
