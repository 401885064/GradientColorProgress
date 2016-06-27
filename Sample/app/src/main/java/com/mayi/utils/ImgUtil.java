package com.mayi.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by mayi on 16/6/24.
 * 图片工具类
 */
public class ImgUtil {

    /**
     * 获取图片缩小的图片
     *
     * @param src
     * @param max
     * @return
     */
    public static Bitmap scaleBitmap(String src, int max) {
        // 获取图片的高和宽
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 这一个设置使 BitmapFactory.decodeFile获得的图片是空的,但是会将图片信息写到options中
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(src, options);
        // 计算比例 为了提高精度,本来是要640 这里缩为64
        max = max / 10;
        int be = options.outWidth / max;
        if (be % 10 != 0)
            be += 10;
        be = be / 10;
        if (be <= 0)
            be = 1;
        options.inSampleSize = be;
        // 设置可以获取数据
        options.inJustDecodeBounds = false;
        // 获取图片
        return BitmapFactory.decodeFile(src, options);
    }


    /**
     * 获取图片的高度
     *int
     * @param res
     * @param resId
     * @return
     */
    public static  int getBitmapHeight(Resources res, int resId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res,resId, options);
        return options.outHeight;
    }

    /**
     * 获取图片的宽度
     *int
     * @param res
     * @param resId
     * @return
     */
    public static  int getBitmapWidth(Resources res, int resId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res,resId, options);
        return options.outWidth;
    }
}
