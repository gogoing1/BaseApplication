package com.chenq.library.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * create by chenqi on 2020/7/12.
 * Email: chenqwork@gmail.com
 * Desc: SharedPreferences 工具类
 */
public class PreferenceUtils {

    /**
     * hasKey
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean hasKey(Context context, final String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).contains(key);
    }

    /**
     * putString
     *
     * @param context
     * @param key
     * @param value
     */
    public static void putPrefString(Context context, final String key, final String value) {
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(key, value).commit();
    }

    /**
     * getString
     *
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getPrefString(Context context, String key, final String defaultValue) {
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(key, defaultValue);
    }

    /**
     * putBoolean
     *
     * @param context
     * @param key
     * @param value
     */
    public static void putPrefBoolean(Context context, final String key, final boolean value) {
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(key, value).commit();
    }

    /**
     * getBoolean
     *
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static boolean getPrefBoolean(Context context, final String key, final boolean defaultValue) {
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(key, defaultValue);
    }

    /**
     * putInt
     *
     * @param context
     * @param key
     * @param value
     */
    public static void putPrefInt(Context context, final String key, final int value) {
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putInt(key, value).commit();
    }

    /**
     * getInt
     *
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static int getPrefInt(Context context, final String key, final int defaultValue) {
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getInt(key, defaultValue);
    }

    /**
     * putFloat
     *
     * @param context
     * @param key
     * @param value
     */
    public static void putPrefFloat(Context context, final String key, final float value) {
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putFloat(key, value).commit();
    }

    /**
     * getFloat
     *
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static float getPrefFloat(Context context, final String key, final float defaultValue) {
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getFloat(key, defaultValue);
    }

    /**
     * putLong
     *
     * @param context
     * @param key
     * @param value
     */
    public static void setPrefLong(Context context, final String key, final long value) {
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putLong(key, value).commit();
    }

    /**
     * getLong
     *
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static long getPrefLong(Context context, final String key, final long defaultValue) {
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getLong(key, defaultValue);
    }

    /**
     * clean
     *
     * @param context
     * @param p
     */
    public static void clearPreference(Context context, final SharedPreferences p) {
        final SharedPreferences.Editor editor = p.edit();
        editor.clear();
        editor.commit();
    }

}
