package projects.gabriel.calhacks;

        //important generic API for bluetooth.
//http://developer.android.com/guide/topics/connectivity/bluetooth.html
import android.bluetooth.*;


/**
 * Created by Landon on 10/10/2015.
 */
public class BlueTooth_and_Chat {

    BluetoothAdapter bta;

    //The server socket is a single socket that people can queue up to.
    BluetoothServerSocket btss;

    //bts is current healthy connections, which should be a finite limit.
    BluetoothSocket bts[];


}
