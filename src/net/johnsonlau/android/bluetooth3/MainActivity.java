package net.johnsonlau.android.bluetooth3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {

    BluetoothAdapter mBluetoothAdapter;

    private ListView mBondedDevicesLv;

    public static int REQUEST_ENABLE_BT;

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // mArrayAdapter .add(device.getName() + "\n" +
                // device.getAddress());
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        init();

        showBondedDevices();

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
    };

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

        mBondedDevicesLv.setAdapter(new SimpleAdapter(this, list,
                R.layout.listitem_device, new String[] { "name", "mac" },
                new int[] { R.id.device_name, R.id.device_mac }));
    }
}
