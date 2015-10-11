package projects.gabriel.calhacks;

        //important generic API for bluetooth.
//http://developer.android.com/guide/topics/connectivity/bluetooth.html
import android.bluetooth.*;
import android.Manifest.permission;
import android.content.Intent;

import android.app.*;

/**
 * Created by Landon on 10/10/2015.
 */


public class BlueTooth_and_Chat {


    BluetoothAdapter myBTooth =BluetoothAdapter.getDefaultAdapter();

    if (myBTooth == null)
    {
        // Device does not support Bluetooth
        return;
    }

    int REQUEST_ENABLE_BT;

    //check if bluetooth is on
            //i know this is broken
    if (!myBTooth.isEnabled())
    {
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


    //The server socket is a single socket that people can queue up to.
    BluetoothServerSocket btss;

    //bts is current healthy connections, which should be a finite limit.
    BluetoothSocket bts[];

    //when ready for a connection call this:
    BluetoothAdapter.listenUsingRfcommWithServiceRecord()

    //call this to listen for
    accept()
        //

    //a wireless interface specification for Blue
    //tooth-based communication between devices
    BluetoothProfile a;

}
