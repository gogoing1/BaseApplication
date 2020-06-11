package com.example.chenq.base.util;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

/**
 * 设备管理类
 */
public class DeviceUtil {

    protected static final String TAG = DeviceUtil.class.getSimpleName();

    private static Context mCtx;
    private static String sDeviceId;

    public static void init(Context ctx) {
        mCtx = ctx;
        //sDeviceId = Reference.getCoCoAppPreferenceInstance(mCtx).getString(Reference.REF_DEVICE_ID, null);
        Log.i(TAG, String.format("init DeviceId：%s", sDeviceId));
    }

    public static String getModel() {
        return Build.MODEL;
    }

    public static String getIMEI() {
        final TelephonyManager tm = (TelephonyManager) mCtx
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm == null ? null : tm.getDeviceId();
    }

    public static boolean isAPI(int code){
        return Build.VERSION.SDK_INT >= code;
    }

    public static int getDeviceWidth() {
        WindowManager wm = (WindowManager) mCtx
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public static int getDeviceHeight() {
        WindowManager wm = (WindowManager) mCtx
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    public static String getDeviceId() {
        if (TextUtils.isEmpty(sDeviceId)) {
            sDeviceId = getIMEI();
            if (TextUtils.isEmpty(sDeviceId)) {
                sDeviceId = getMAC();
            }
            //Reference.getCoCoAppPreferenceInstance(mCtx).saveString(Reference.REF_DEVICE_ID, sDeviceId);
            Log.i(TAG, String.format("getDeviceId：%s", sDeviceId));
        }
        return sDeviceId;
    }

    public static String getMAC() {
        String macStr = "";
        WifiManager wifiManager = (WifiManager) mCtx.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo.getMacAddress() != null) {
            macStr = wifiInfo.getMacAddress();// MAC地址
        }
        Log.i(TAG, String.format("mac_addr：%s", macStr));
        return macStr;
    }

    public static float density(){
        return mCtx.getResources().getDisplayMetrics().density;
    }

    public static int densityInt(){
        return Math.round(mCtx.getResources().getDisplayMetrics().density);
    }

    public static int dip2px(float dipValue) {
        final float scale = mCtx.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int densityDpi(){
        return mCtx.getResources().getDisplayMetrics().densityDpi;
    }

    public static int px2dip(float pxValue) {
        final float scale = mCtx.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static boolean isMeizu(){
        return "Meizu".equals(Build.BRAND);
    }

    public static boolean isMeizuM1Note(){
        return isMeizu() && "m1 note".equals(Build.MODEL);
    }

    public static boolean isMeizuM2Note(){
        return isMeizu() && "m2 note".equals(Build.MODEL);
    }

    private static int sStatusBarHeight = -1;

    public static int getStatusBarHeight(Context context) {
        if(sStatusBarHeight != -1){
            return sStatusBarHeight;
        }
        int result = DeviceUtil.dip2px(25);
        try {
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = context.getResources().getDimensionPixelSize(resourceId);
            }
        } catch (Exception e) {
            Log.e(TAG, "getStatusBarHeight Exception", e);
        }
        sStatusBarHeight = result;
        return sStatusBarHeight;
    }

    public static boolean isCanFitSystemWindows() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && !DeviceUtil.isMeizuM2Note()) {
            return true;
        }
        return false;
    }

}
