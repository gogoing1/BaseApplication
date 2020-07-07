package com.example.chenq.util;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 日志工具类
 * 将输出日志等级大于 {@link Log.VERBOSE} 的日志写入sd卡
 * 输出路径 /sdcard/Log.html
 */
final public class LogUtils {
    public static final boolean COLLECT_LOG_TO_SDCARD = false;
    private final static int sLogLevel = Log.VERBOSE;//Log.VERBOSE~Log.ASSERT
    private final static String MYLOG_PATH_SDCARD_DIR = "/sdcard/";
    private final static String MYLOGFILEName = "Log.html";

    public static int v(String tag, String msg) {
        return println(Log.VERBOSE, tag, msg);
    }

    public static int v(String tag, String msg, Throwable tr) {
        return println(Log.VERBOSE, tag, msg + '\n' + getStackTraceString(tr));
    }

    public static int d(String tag, String msg) {
        return println(Log.DEBUG, tag, msg);
    }

    public static int d(String tag, String msg, Throwable tr) {
        return println(Log.DEBUG, tag, msg + '\n' + getStackTraceString(tr));
    }

    public static int i(String tag, String msg) {
        return println(Log.INFO, tag, msg);
    }

    public static int i(String tag, String msg, Throwable tr) {
        return println(Log.INFO, tag, msg + '\n' + getStackTraceString(tr));
    }

    public static int w(String tag, String msg) {
        return println(Log.WARN, tag, msg);
    }

    public static int w(String tag, String msg, Throwable tr) {
        return println(Log.WARN, tag, msg + '\n' + getStackTraceString(tr));
    }

    public static int w(String tag, Throwable tr) {
        return println(Log.WARN, tag, getStackTraceString(tr));
    }

    public static int e(String tag, String msg) {
        //return println(Log.ERROR, tag, msg);
        return println(Log.ASSERT, tag, msg);
    }

    public static int e(String tag, String msg, Throwable tr) {
        return println(Log.ERROR, tag, msg + '\n' + getStackTraceString(tr));
    }

    public static int a(String tag, String msg) {
        return println(Log.ASSERT, tag, msg);
    }

    public static int a(String tag, String msg, Throwable tr) {
        return println(Log.ASSERT, tag, msg + '\n' + getStackTraceString(tr));
    }

    public static int wtf(String tag, String msg) {
        return Log.wtf(tag, msg, null);
    }

    public static int wtf(String tag, Throwable tr) {
        return Log.wtf(tag, tr.getMessage(), tr);
    }

    public static int wtf(String tag, String msg, Throwable tr) {
        return Log.wtf(tag, msg, tr);
    }

    public static String getStackTraceString(Throwable tr) {
        return Log.getStackTraceString(tr);
    }

    public static int println(int priority, String tag, String msg) {
        if (priority >= sLogLevel) {
            if (COLLECT_LOG_TO_SDCARD && priority == Log.ASSERT) {
                writeLogtoFile(tag, msg);
            }
            return Log.println(priority, tag, msg);
        } else {
            return 0;
        }
    }

    private static void writeLogtoFile(String tag, String text) {
        File file = new File(MYLOG_PATH_SDCARD_DIR, MYLOGFILEName);
        String needWriteMessage = tag + "  ：：  " + text;
        try {
            FileWriter filerWriter = new FileWriter(file, true);
            BufferedWriter bufWriter = new BufferedWriter(filerWriter);
            bufWriter.write(needWriteMessage);
            bufWriter.newLine();
            bufWriter.close();
            filerWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printHex(byte[] buf, int len) {
        String str = "";
        for (int i = 0; i < len; i++) {
            String b = Integer.toHexString(buf[i] & 0xFF);
            if (b.length() == 1) {
                b = "0" + b;
            }
            str += ":" + b;
        }
        e("buf", str + "\n");
    }
}
