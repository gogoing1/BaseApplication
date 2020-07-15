package com.chenq.library.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * create by chenqi on 2020/7/15.
 * Email: chenqwork@gmail.com
 * Desc:
 */
public class BitmapUtils {

    /**
     * 合成图片
     *
     * @param left 绘制第二张位图左侧的位置
     * @param top  绘制第二张位图顶部的位置
     */
    private static Bitmap mergeBitmap(Bitmap firstBitmap, Bitmap secondBitmap, float left, float top) {
        if (firstBitmap == null || secondBitmap == null) {
            throw new NullPointerException("Bitmap can not be null");
        }
        int ceilLeft = (int) Math.ceil(left);
        int width = 0;
        if (firstBitmap.getWidth() >= ceilLeft) {
            width = firstBitmap.getWidth() + secondBitmap.getWidth() + ceilLeft;
        } else if (firstBitmap.getWidth() > ceilLeft) {
            width = ceilLeft + secondBitmap.getWidth();
        }
        int ceilTop = (int) Math.ceil(top);
        int height = 0;
        if (firstBitmap.getWidth() >= ceilTop) {
            height = firstBitmap.getWidth() + secondBitmap.getWidth() + ceilTop;
        } else if (firstBitmap.getWidth() > ceilTop) {
            height = ceilTop + secondBitmap.getWidth();
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(firstBitmap, new Matrix(), null);
        canvas.drawBitmap(secondBitmap, left, top, null);
        return bitmap;
    }

    /**
     * bitmap转为base64
     * @param bitmap 图片Bitmap
     * @return base64字符串
     */
    public static String bitmapToBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * base64转为bitmap
     * @param base64Data base64字符串
     * @return 图片Bitmap
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

}
