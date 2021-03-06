package com.cncn.test.constom.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.lang.ref.WeakReference;

/**
 * Created by Mike on 2016/3/2.
 */
public class BitmapUtils {

    //可以用来转换成UI上设置的图片；
    public static Bitmap convertToBitmap(String path, int w, int h) {

        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 设置为ture只获取图片大小
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        // 返回为空
        BitmapFactory.decodeFile(path, opts);
        int width = opts.outWidth;
        int height = opts.outHeight;
        float scaleWidth = 0.f, scaleHeight = 0.f;
        if (width > w || height > h) {
            // 缩放
            scaleWidth = ((float) width) / w;
            scaleHeight = ((float) height) / h;
        }
        opts.inJustDecodeBounds = false;
        float scale = Math.max(scaleWidth, scaleHeight);
        opts.inSampleSize = (int) scale;
        WeakReference weak = new WeakReference(BitmapFactory.decodeFile(path, opts));
        return Bitmap.createScaledBitmap((Bitmap) weak.get(), w, h, true);
    }

}
