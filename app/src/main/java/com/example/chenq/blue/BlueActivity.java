package com.example.chenq.blue;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.chenq.R;
import com.example.chenq.util.BlueUtils;

public class BlueActivity extends Activity {
    boolean isExit;
    private Editor editor;
    private ListView listView;
    private LeScanCallback mLeScanCallback;
    private SwipeRefreshLayout swagLayout;
    private BluetoothAdapter mBluetoothAdapter;
    private SharedPreferences sharedPreferences;
    private BleDeviceListAdapter mBleDeviceListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blue_main_layout);
        getActionBar().setTitle(R.string.app_title);
        sharedPreferences = getPreferences(0);
        editor = sharedPreferences.edit();
        init();
        getBleAdapter();
        getScanResualt();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mBluetoothAdapter.startLeScan(mLeScanCallback);
            }
        }).start();
    }

    private void init() {
        listView = findViewById(R.id.lv_deviceList);
        listView.setEmptyView(findViewById(R.id.pb_empty));
        swagLayout = findViewById(R.id.swagLayout);
        swagLayout.setVisibility(View.VISIBLE);
        swagLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                mBleDeviceListAdapter.clear();
                mBluetoothAdapter.startLeScan(mLeScanCallback);
                swagLayout.setRefreshing(false);
            }
        });
        mBleDeviceListAdapter = new BleDeviceListAdapter(this);
        listView.setAdapter(mBleDeviceListAdapter);
        setListItemListener();
    }

    private void getBleAdapter() {
        final BluetoothManager bluetoothManager = (BluetoothManager) this
                .getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
    }

    private void getScanResualt() {
        mLeScanCallback = new LeScanCallback() {
            @Override
            public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
                BlueActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        mBleDeviceListAdapter.addDevice(device, rssi, BlueUtils.bytesToHex(scanRecord));
                        mBleDeviceListAdapter.notifyDataSetChanged();
                        invalidateOptionsMenu();
                    }
                });
            }
        };
    }

    private void setListItemListener() {
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BluetoothDevice device = mBleDeviceListAdapter.getDevice(position);
                final Intent intent = new Intent(BlueActivity.this, DeviceConnect.class);
                intent.putExtra(DeviceConnect.EXTRAS_DEVICE_NAME, device.getName());
                intent.putExtra(DeviceConnect.EXTRAS_DEVICE_ADDRESS, device.getAddress());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBluetoothAdapter.stopLeScan(mLeScanCallback);
        mBleDeviceListAdapter.clear();
        mBluetoothAdapter.cancelDiscovery();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        menu.getItem(0).setTitle("共" + mBleDeviceListAdapter.getCount() + "个");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_stop:
                mBluetoothAdapter.stopLeScan(mLeScanCallback);
                break;
            case R.id.menu_autoconnect:
                if (sharedPreferences.getBoolean("AutoConnect", true)) {
                    editor.putBoolean("AutoConnect", false);
                    editor.commit();
                    Toast.makeText(this, "取消自动连接", Toast.LENGTH_SHORT).show();
                } else {
                    editor.putBoolean("AutoConnect", true);
                    editor.commit();
                    Toast.makeText(this, "已设置为断开后自动连接", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isExit) {
                isExit = true;
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                new Timer().schedule(new TimerTask() {
                    public void run() {
                        isExit = false;
                    }
                }, 2000);
            } else {
                onDestroy();
                finish();
                System.exit(0);
            }
        }
        return false;
    }
}