package net.johnsonlau.android.bluetooth3;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

    BluetoothAdapter mBluetoothAdapter;

    public static int REQUEST_ENABLE_BT;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        init();
    }

    private void init() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "no Bluetooth radio", Toast.LENGTH_LONG);
        }

        /*
         * if (!mBluetoothAdapter.isEnabled()) { Intent enableBTIntent = new
         * Intent( BluetoothAdapter.ACTION_REQUEST_ENABLE);
         * startActivityForResult(enableBTIntent, REQUEST_ENABLE_BT); }
         */
    }
}
