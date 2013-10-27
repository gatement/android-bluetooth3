package net.johnsonlau.android.bluetooth3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends ListActivity {

    BluetoothAdapter mBluetoothAdapter;

    public static int REQUEST_ENABLE_BT;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        init();
        showBondedDevices();
    }

    private void init() {
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

        setListAdapter(new SimpleAdapter(this, list, R.layout.device_item,
                new String[] { "name", "mac" },
                new int[] { R.id.name, R.id.mac }));
    }
}
