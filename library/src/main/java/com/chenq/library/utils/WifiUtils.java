package com.chenq.library.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.chenq.library.bean.WifiBean;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by chenqi on 2020/8/7.
 * Email: chenqwork@gmail.com
 * Desc:
 */
public class WifiUtils {
    // 定义WifiManager对象
    private WifiManager mWifiManager;
    // 定义WifiInfo对象
    private WifiInfo mWifiInfo;
    // 扫描出的网络连接列表
    private List<ScanResult> mWifiList;
    // 网络连接列表
    private List<WifiConfiguration> mWifiConfiguration;
    // 定义一个WifiLock
    WifiLock mWifiLock;

    // 构造器
    public WifiUtils(Context context) {
        // 取得WifiManager对象
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        // 取得WifiInfo对象
        mWifiInfo = mWifiManager.getConnectionInfo();
    }

    // 打开WIFI
    public boolean openWifi() {

        if (!mWifiManager.isWifiEnabled()) {
            return mWifiManager.setWifiEnabled(true);
        }
        return true;
    }

    // 关闭WIFI
    public boolean closeWifi() {
        if (mWifiManager.isWifiEnabled()) {
            return mWifiManager.setWifiEnabled(false);
        }
        return false;
    }

    public boolean wifiIsOpened() {
        return mWifiManager.isWifiEnabled();
    }

    // 检查当前WIFI状态
    public int checkState() {
        return mWifiManager.getWifiState();
    }

    // 锁定WifiLock
    public void acquireWifiLock() {
        mWifiLock.acquire();
    }

    // 解锁WifiLock
    public void releaseWifiLock() {
        // 判断时候锁定
        if (mWifiLock.isHeld()) {
            mWifiLock.acquire();
        }
    }

    // 创建一个WifiLock
    public void creatWifiLock() {
        mWifiLock = mWifiManager.createWifiLock("Test");
    }

    // 得到配置好的网络
    public List<WifiConfiguration> getConfiguration() {
        return mWifiConfiguration;
    }

    // 指定配置好的网络进行连接
    public void connectConfiguration(int index) {
        // 索引大于配置好的网络索引返回
        if (index > mWifiConfiguration.size()) {
            return;
        }
        // 连接配置好的指定ID的网络
        mWifiManager.enableNetwork(mWifiConfiguration.get(index).networkId, true);
    }

    public void startScan() {
        Log.e("Wifi", "Start scan ...." + mWifiList.size());
        mWifiManager.startScan();
        // 得到扫描结果
        mWifiList = mWifiManager.getScanResults();
        Log.e("Wifi", "wifi list: " + mWifiList.size());
        // 得到配置好的网络连接
        mWifiConfiguration = mWifiManager.getConfiguredNetworks();
    }


    // 得到网络列表
    public List<ScanResult> getWifiList() {
        return mWifiList = mWifiManager.getScanResults();
    }

    public boolean findCustomSSID(String ssid) {
        boolean find = false;
        if (mWifiList != null && mWifiList.size() > 0) {
            for (ScanResult sr : mWifiList) {
                if (sr.SSID != null && ssid.equals(sr.SSID)) {
                    find = true;
                    break;
                }
            }
        }
        return find;
    }

    public String findCustomSSIDFromPrefix(String prefix) {
        String findSSID = null;
        if (mWifiList != null && mWifiList.size() > 0) {
            for (ScanResult sr : mWifiList) {
                if (sr.SSID != null && sr.SSID.startsWith(prefix)) {
                    findSSID = sr.SSID;
                    break;
                }
            }
        }
        return findSSID;
    }

    // 查看扫描结果
    public StringBuilder lookUpScan() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mWifiList.size(); i++) {
            stringBuilder.append("Index_" + new Integer(i + 1).toString() + ":");
            // 将ScanResult信息转换成一个字符串包
            // 其中把包括：BSSID、SSID、capabilities、frequency、level
            stringBuilder.append((mWifiList.get(i)).toString());
            stringBuilder.append("/n");
        }
        return stringBuilder;
    }

    // 得到MAC地址
    public String getMacAddress() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.getMacAddress();
    }

    // 得到接入点的BSSID
    public String getBSSID() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.getBSSID();
    }

    // 得到IP地址
    public int getIPAddress() {
        return (mWifiInfo == null) ? 0 : mWifiInfo.getIpAddress();
    }

    // 得到连接的ID
    public int getNetworkId() {
        return (mWifiInfo == null) ? 0 : mWifiInfo.getNetworkId();
    }

    // 得到WifiInfo的所有信息包
    public String getWifiInfo() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.toString();
    }

    // 添加一个网络并连接
    public boolean addNetwork(WifiConfiguration wcg) {
        int wcgID = mWifiManager.addNetwork(wcg);
        boolean b = mWifiManager.enableNetwork(wcgID, true);
        return b;
    }

    // 断开指定ID的网络
    public void disconnectWifi(int netId) {
        mWifiManager.disableNetwork(netId);
        mWifiManager.disconnect();
    }

    public boolean setWifi(String SSID, String Password, int Type) {
        WifiConfiguration config = CreateWifiInfo(SSID, Password, Type);
        boolean b = addNetwork(config);
        return b;
    }

    public WifiConfiguration CreateWifiInfo(String SSID, String Password, int Type) {
        WifiConfiguration config = new WifiConfiguration();
        config.SSID = "\"" + SSID + "\"";
        WifiConfiguration tempConfig = this.IsExsits(SSID);
        Log.e("tag", "===WifiConfiguration ssid " + SSID + " Password " + Password + "  type  " + Type + " tempConfig " + tempConfig);
        if (tempConfig != null) {
            mWifiManager.removeNetwork(tempConfig.networkId);
        }
        config.hiddenSSID = false;
        config.status = WifiConfiguration.Status.ENABLED;
        if (Type == 1) // WIFICIPHER_NOPASS
        {
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.preSharedKey = null;
            config.wepKeys[0] = "\"" + "\"";
            config.wepTxKeyIndex = 0;
        }
        if (Type == 2) // WIFICIPHER_WEP
        {
            config.wepKeys[0] = "\"" + Password + "\"";
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        }
        if (Type == 3) // WIFICIPHER_WPA
        {
            config.preSharedKey = "\"" + Password + "\"";
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            // config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            config.status = WifiConfiguration.Status.ENABLED;
        }
        return config;
    }

    private WifiConfiguration IsExsits(String SSID) {
        List<WifiConfiguration> existingConfigs = mWifiManager.getConfiguredNetworks();
        for (WifiConfiguration existingConfig : existingConfigs) {
            if (existingConfig.SSID.equals("\"" + SSID + "\"")) {
                return existingConfig;
            }
        }
        return null;
    }

    public String getCurWifiSSID(Activity activity) {
        String ssid = "";

        if (Build.VERSION.SDK_INT <= 26 || Build.VERSION.SDK_INT >= 28) {
            WifiInfo info = mWifiManager.getConnectionInfo();
            if (info == null || TextUtils.isEmpty(info.getSSID())
                    || info.getSSID().contains("unknown")) {
                int networkId = info.getNetworkId();
                List<WifiConfiguration> netConfList = mWifiManager.getConfiguredNetworks();
                if (netConfList != null && netConfList.size() > 0) {
                    for (WifiConfiguration wifiConf : netConfList) {
                        if (wifiConf.networkId == networkId) {
                            ssid = wifiConf.SSID;
                            break;
                        }
                    }
                }
            } else {
                return info.getSSID();
            }
        } else if (Build.VERSION.SDK_INT == 27) {
            ConnectivityManager connManager = (ConnectivityManager) activity.getApplicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connManager == null) return "";
            NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                if (networkInfo.getExtraInfo() != null) {
                    return networkInfo.getExtraInfo();
                }
            }
        }
        return ssid;
    }

    public List<WifiBean> getAllWifiBean() {
        List<WifiBean> alist = new ArrayList<>();
        Map<String, WifiBean> mMap = new HashMap<>();
        try {
            //startScan();
            mWifiList = mWifiManager.getScanResults();
            if (mWifiList != null && mWifiList.size() > 0) {
                WifiBean wbean = null;
                for (int i = 0; i < mWifiList.size(); i++) {
                    ScanResult scanResult = mWifiList.get(i);
                    if (!scanResult.SSID.isEmpty()) {
                        int level = mWifiManager.calculateSignalLevel(scanResult.level, 4);
                        Log.e("wifiUtils", "wifiUtils.SSID ===" + scanResult.SSID + "    rssi ===" + scanResult.level);
                        String key = scanResult.SSID + " " + scanResult.capabilities;
                        if (!mMap.containsKey(key)) {
                            wbean = new WifiBean(scanResult.SSID, scanResult.BSSID, level);
                            mMap.put(key, wbean);
                        }
                    }
                }
            }

            Collection<WifiBean> valueCollection = mMap.values();
            alist.addAll(valueCollection);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return alist;
    }

    public boolean curNetIsConnected(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return false;
        }
        return networkInfo.isConnected();
    }

    public boolean isMobileConnect(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }

    public String getCurWifiIp() {
        WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
        String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());
        return ipAddress;
    }

    private static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

    public static boolean testPingByHttp(String url) {
        try {
            URL testUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) testUrl.openConnection();
            conn.setReadTimeout(3 * 1000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.connect();
            int responseCode = conn.getResponseCode();
            return responseCode == 204;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
