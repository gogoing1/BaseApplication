package com.example.chenq.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chenq.base.BaseActivity;
import com.chenq.library.bean.WifiBean;
import com.chenq.library.utils.WifiUtils;
import com.example.chenq.R;
import com.example.chenq.adapter.WifiAdapter;
import com.example.chenq.widget.dialog.WifiPwdDialog;

import java.lang.ref.WeakReference;
import java.util.List;

import static android.net.wifi.WifiManager.WIFI_STATE_DISABLED;
import static android.net.wifi.WifiManager.WIFI_STATE_DISABLING;
import static android.net.wifi.WifiManager.WIFI_STATE_ENABLED;
import static android.net.wifi.WifiManager.WIFI_STATE_ENABLING;
import static android.net.wifi.WifiManager.WIFI_STATE_UNKNOWN;

/**
 * create by chenqi on 2020/8/7.
 * Email: chenqwork@gmail.com
 * Desc:
 */
public class WifiActivity extends BaseActivity {

    private static final String TAG = "WifiActivity";

    private static final int SCAN_DELAY = 10000;
    private static final int MSG_SCAN = 1;

    private RecyclerView mRecycleView;
    private ProgressBar mLoadingView;
    private Switch mSwitchView;
    private boolean mWifiOpenState;
    private List<WifiBean> wList = null;
    private WifiAdapter mWifiAdapter = null;
    private WifiUtils mWifiUtils = null;
    private ConnectivityManager mConnectivityManager = null;
    private BroadcastReceiver myBroadcastReceiver = null;
    private WifiHandler mWifiHandler = new WifiHandler(this);

    public static void start(Context context) {
        Intent starter = new Intent(context, WifiActivity.class);
        context.startActivity(starter);
    }

    private class WifiHandler extends Handler {
        WeakReference weakReference;

        public WifiHandler(WifiActivity act) {
            weakReference = new WeakReference(act);
        }

        @Override
        public void handleMessage(Message msg) {
            if (weakReference.get() == null) return;
            if (msg.what == MSG_SCAN) {
                if (mWifiUtils != null && mWifiUtils.wifiIsOpened()) {
                    mWifiUtils.startScan();
                    mLoadingView.setVisibility(View.VISIBLE);
                }
                sendEmptyMessageDelayed(MSG_SCAN, SCAN_DELAY);
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_layout);
        mRecycleView = findViewById(R.id.rvSetting);
        mLoadingView = findViewById(R.id.loading_progress);
        mSwitchView = findViewById(R.id.switch_view);
        mSwitchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mWifiOpenState = isChecked;
                mLoadingView.setVisibility(mWifiOpenState ? View.VISIBLE : View.GONE);
                if (!mWifiOpenState) {
                    mWifiUtils.openWifi();
                } else {
                    mWifiUtils.closeWifi();
                }
            }
        });
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);    //两列且垂直方向布局
        mRecycleView.setLayoutManager(layoutManager);
        mWifiAdapter = new WifiAdapter(this, wList);
        mRecycleView.setAdapter(mWifiAdapter);
        mWifiAdapter.setOnItemClickListener(new WifiAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.e("SettingTag", "SettingHolder  item onClick");

                WifiPwdDialog mWifiPwdDialog = new WifiPwdDialog(WifiActivity.this, wList.get(position).getSsid(), 0.5f);
                mWifiPwdDialog.setCancelable(false);
                mWifiPwdDialog.setOnClickButtonListener(new WifiPwdDialog.OnClickButtonListener() {
                    @Override
                    public void onClick(boolean isWifiOK, String ssid) {
                        Log.e("SettingTag", "SettingHolder  iisWifiOK == " + isWifiOK + "  ssid == " + ssid);
                    }
                });
                mWifiPwdDialog.show();
            }
        });

        registerBroadcast();
        initWifi();
    }

    private void initWifi() {
        mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        mWifiUtils = new WifiUtils(this);
        mWifiOpenState = mWifiUtils.wifiIsOpened();
        mSwitchView.setChecked(mWifiOpenState);
        if (mWifiOpenState) {
            mWifiUtils.startScan();
            wList = mWifiUtils.getAllWifiBean();
            mWifiAdapter.refreshWifi(wList);
        } else {
            mWifiAdapter.refreshWifi(null);
        }
        //开启定时任务刷新Wifi列表
        //mWifiHandler.sendEmptyMessage(MSG_SCAN);
    }

    private void registerBroadcast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        myBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.e(TAG, "onReceive intent.getAction() = " + intent.getAction());
                if (intent.getAction() == WifiManager.WIFI_STATE_CHANGED_ACTION) {
                    switch (intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WIFI_STATE_UNKNOWN)) {
                        case WIFI_STATE_DISABLED:
                        case WIFI_STATE_DISABLING:
                            mLoadingView.setVisibility(View.GONE);
                            mWifiAdapter.refreshWifi(null);
                            break;
                        case WIFI_STATE_ENABLING:
                        case WIFI_STATE_ENABLED:
                            mLoadingView.setVisibility(View.VISIBLE);
                            mWifiUtils.startScan();
                            wList = mWifiUtils.getAllWifiBean();
                            Log.e(TAG, "1  wifiList size = " + wList.size());
                            mWifiAdapter.refreshWifi(wList);
                            break;
                        default:
                            break;
                    }

                } else if (intent.getAction() == WifiManager.SCAN_RESULTS_AVAILABLE_ACTION) {
                    wList = mWifiUtils.getAllWifiBean();
                    mWifiAdapter.refreshWifi(wList);
                    mLoadingView.setVisibility(View.GONE);
                    Log.e(TAG, "2 wifiList size = " + wList.size());

                    String ssid = mWifiUtils.getCurWifiSSID(WifiActivity.this).replaceAll("\"", "");

                } else if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                    //NetworkInfo info = mConnectivityManager.getActiveNetworkInfo();
                    //String ssid = mWifiUtils.getCurWifiSSID(WifiActivity.this).replaceAll("\"", "");
                }

            }
        };
        registerReceiver(myBroadcastReceiver, filter);
    }

    @Override
    public void onDestroy() {
        unRegisterBroadcast();
        if (mWifiHandler != null) {
            mWifiHandler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }

    private void unRegisterBroadcast() {
        if (myBroadcastReceiver != null)
            unregisterReceiver(myBroadcastReceiver);
    }

}
