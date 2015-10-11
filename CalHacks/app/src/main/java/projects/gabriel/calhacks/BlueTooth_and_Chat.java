package projects.gabriel.calhacks;

        //important generic API for bluetooth.
//http://developer.android.com/guide/topics/connectivity/bluetooth.html

import android.bluetooth.*;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.ParcelFileDescriptor;
import android.widget.ArrayAdapter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;
import android.os.Handler;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

/**
 * Created by Landon on 10/10/2015.
 */

public class BlueTooth_and_Chat {
    static int MESSAGE_READ;
    public static String NAME = "meet_friends";
    //universal identifier
    public static UUID MY_UUID = UUID.randomUUID();
    //public static String BLUETOOTH = permission.BLUETOOTH.toString();
    //current users bluetooth object
    public static BluetoothAdapter myBTooth;
    //bts is a single healthy connection
    public BluetoothSocket bts;
    //a remote bluetooth device
    BluetoothDevice yourBTD;
    BlueTooth_and_Chat() {

        ParcelFileDescriptor pfd;
        myBTooth = BluetoothAdapter.getDefaultAdapter();
        if (myBTooth == null) {
            // Device does not support Bluetooth
            return;
        }
        //only accept one connection at a time
        //needs to be interruptable
        new Thread( new Runnable( )
        { public void run() { checkIfBlueToothEnabled();}});

        //Thread receiveInterrupt =
                new Thread( new Runnable( )
        { public void run() { new ConnectThread(yourBTD);}});

        //Thread closeInterrupt =
                new Thread( new Runnable( )
        { public void run() { new AcceptThread();}});

    }
    public void checkIfBlueToothEnabled() {
        int REQUEST_ENABLE_BT =1;
        //check if bluetooth is on
        if (!myBTooth.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

            //accessing incorrect method
            //should access ACTIVITY class
            startActivityForResult(     //need action to ask for permission
                    new Intent(Intent.ACTION_PICK , new Uri("content://contacts")),

                            enableBtIntent,
                                        REQUEST_ENABLE_BT);

            //if it was enabled, will return int RESULT_OK = -1
            //else RESULT_CANCELLED=0
        }
    /*
    * We are done with the initialization of a single bluetooth
    * for the current user.
    *
    * */
    }

    ArrayAdapter mArrayAdapter;

    public void checkPairedDevices() {
        Set<BluetoothDevice> pairedDevices =myBTooth .getBondedDevices();

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
    Intent newIn = registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy

    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            // Use a temporary object that is later assigned to mmSocket,
            // because mmSocket is final
            BluetoothSocket tmp = null;
            mmDevice = device;

            // Get a BluetoothSocket to connect with the given BluetoothDevice
            try {
                // MY_UUID is the app's UUID string, also used by the server code
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) { }
            mmSocket = tmp;
        }

        public void run() {
            // Cancel discovery because it will slow down the connection
            myBTooth.cancelDiscovery();

            try {
                // Connect the device through the socket. This will block
                // until it succeeds or throws an exception
                mmSocket.connect();
            } catch (IOException connectException) {
                // Unable to connect; close the socket and get out
                try {
                    mmSocket.close();
                } catch (IOException closeException) { }
                return;
            }

            // Do work to manage the connection (in a separate thread)
            manageConnectedSocket(mmSocket);
        }

        /** Will cancel an in-progress connection, and close the socket */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }

    private class AcceptThread extends Thread {
        private BluetoothServerSocket mmServerSocket;

        public AcceptThread() {
            // Use a temporary object that is later assigned to mmServerSocket,
            // because mmServerSocket is final
            BluetoothServerSocket tmp = null;
            try {
                // MY_UUID is the app's UUID string, also used by the client code
                tmp = myBTooth.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
            } catch (IOException e) {
            }
            mmServerSocket = tmp;
        }

        public void run() {
            BluetoothSocket socket = null;

            // Keep listening until exception occurs or a socket is returned
            while (true) {
                try {
                    socket = mmServerSocket.accept();
                } catch (IOException e) {
                    break;
                }
                // If a connection was accepted
                if (socket != null) {
                    // Do work to manage the connection (in a separate thread)
                    manageConnectedSocket(socket);
                    try {
                        mmServerSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }

        /**
         * Will cancel the listening socket, and cause the thread to finish
         */
        public void cancel() {
            try {
                mmServerSocket.close();
            } catch (IOException e) {
            }
        }


    }
        private class ConnectedThread extends Thread {
            private final BluetoothSocket mmSocket;
            private final InputStream mmInStream;
            private final OutputStream mmOutStream;

            public ConnectedThread(BluetoothSocket socket) {
                mmSocket = socket;
                InputStream tmpIn = null;
                OutputStream tmpOut = null;

                // Get the input and output streams, using temp objects because
                // member streams are final
                try {
                    tmpIn = socket.getInputStream();

                    tmpOut = socket.getOutputStream();
                } catch (IOException e) { }

                mmInStream = tmpIn;
                mmOutStream = tmpOut;
                MESSAGE_READ = tmpIn.hashCode();
            }

            public void run() {
                byte[] buffer = new byte[1024];  // buffer store for the stream
                int bytes; // bytes returned from read()

                Handler mHandler= new Handler();

                // Keep listening to the InputStream until an exception occurs
                while (true) {
                    try {
                        // Read from the InputStream
                        bytes = mmInStream.read(buffer);
                        // Send the obtained bytes to the UI activity
                        mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer)
                                .sendToTarget();
                    } catch (IOException e) {
                        break;
                    }
                }
            }

            /* Call this from the main activity to send data to the remote device */
            public void write(byte[] bytes) {
                try {
                    mmOutStream.write(bytes);
                } catch (IOException e) { }
            }

            /* Call this from the main activity to shutdown the connection */
            public void cancel() {
                try {
                    mmSocket.close();
                } catch (IOException e) { }
            }
        }
    void manageConnectedSocket(BluetoothSocket sock) {
        ConnectedThread ct = new ConnectedThread(sock);
    }

}
