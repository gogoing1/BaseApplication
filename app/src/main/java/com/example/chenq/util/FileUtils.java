package com.example.chenq.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * create by chenqi on 2020/6/11
 * Email: chenqwork@gmail.com
 * Desc: 文件操作工具类
 * 需要获取phone的文件读写权限
 * {@link android.permission.READ_EXTERNAL_STORAGE}
 * {@link android.permission.WRITE_EXTERNAL_STORAGE}
 */
public class FileUtils {

    private static final String TAG = "FileUtils";

    private static FileUtils instance;
    private static final int SUCCESS = 1;
    private static final int FAILED = 0;
    private volatile boolean isSuccess;

    /**
     * 音乐文件路径
     */
    public static final String MUSIC_PATH = "/sdcard/Galaxy/cache/music";

    private Context mContext;
    private String errorStr;
    private FileOperateCallback callback;

    /**
     * 文件操作回调
     */
    public interface FileOperateCallback {
        void onSuccess();

        void onFailed(String error);
    }

    public void setCallback(FileOperateCallback callback) {
        this.callback = callback;
    }

    public FileUtils(Context context) {
        this.mContext = context;
    }

    public static FileUtils getInstance(Context context) {
        if (instance == null)
            instance = new FileUtils(context);
        return instance;
    }

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    if (callback != null)
                        callback.onSuccess();
                    break;
                case FAILED:
                    if (callback != null)
                        callback.onFailed((String) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 将assets下面的文件夹拷贝到目标路径下
     *
     * @param srcPath
     * @param dstPath
     * @return
     */
    public FileUtils copyAssetsToOtherPath(final String srcPath, final String dstPath) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                copyAssetsToDst(mContext, srcPath, dstPath);
                Message msg = mHandler.obtainMessage(isSuccess ? SUCCESS : FAILED, isSuccess ? null : errorStr);
                mHandler.sendMessage(msg);
            }
        }).start();
        return this;
    }

    /**
     * 文件拷贝
     *
     * @param context
     * @param srcPath
     * @param dstPath
     */
    private void copyAssetsToDst(Context context, String srcPath, String dstPath) {
        try {
            String fileNames[] = context.getAssets().list(srcPath);
            if (fileNames.length > 0) {
                File file = new File(dstPath);
                if (!file.exists())
                    file.mkdirs();
                for (String fileName : fileNames) {
                    if (!srcPath.equals("")) { // assets 文件夹下的目录
                        copyAssetsToDst(context, srcPath + File.separator + fileName,
                                dstPath + File.separator + fileName);
                    } else { // assets 文件夹
                        copyAssetsToDst(context, fileName, dstPath + File.separator + fileName);
                    }
                }
            } else {
                File outFile = new File(dstPath);
                InputStream is = context.getAssets().open(srcPath);
                FileOutputStream fos = new FileOutputStream(outFile);
                byte[] buffer = new byte[1024];
                int byteCount;
                while ((byteCount = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, byteCount);
                }
                fos.flush();
                is.close();
                fos.close();
            }
            isSuccess = true;
        } catch (Exception e) {
            Log.e(TAG, "写入失败：" + e.getMessage());
            e.printStackTrace();
            errorStr = e.getMessage();
            isSuccess = false;
        }
    }


}
