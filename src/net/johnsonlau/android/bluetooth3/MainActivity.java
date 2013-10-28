package net.johnsonlau.android.bluetooth3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {

    BluetoothAdapter mBluetoothAdapter;

    private ListView mBondedDevicesLv;

    public static int REQUEST_ENABLE_BT;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        init();
        showBondedDevices();
    }

    private void init() {
        mBondedDevicesLv = (ListView) findViewById(R.id.bonded_devices);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "no Bluetooth radio", Toast.LENGTH_LONG);
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBTIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBTIntent, REQUEST_ENABLE_BT);
        }
    }

    private void showBondedDevices() {
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter
                .getBondedDevices();

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for (BluetoothDevice device : pairedDevices) {
            Map<String, String> dev = new HashMap<String, String>();
            dev.put("name", device.getName());
            dev.put("mac", device.getAddress());
            list.add(dev);
        }

        mBondedDevicesLv.setAdapter( new SimpleAdapter(this, list, R.layout.listitem_bonded_device,
                new String[] { "name", "mac" },
                new int[] { R.id.bonded_device_name, R.id.bonded_device_mac }));
    }
}
