package com.chenhao.musicplayer.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by chenhao on 2016/11/23.
 */

public class BItmapUtil {
    public static Bitmap getAlphaBitmap(Bitmap mBitmap,int color) {
//          BitmapDrawable mBitmapDrawable = (BitmapDrawable) mContext.getResources().getDrawable(R.drawable.enemy_infantry_ninja);  
//          Bitmap mBitmap = mBitmapDrawable.getBitmap();  

        //BitmapDrawable的getIntrinsicWidth（）方法，Bitmap的getWidth（）方法  //注意这两个方法的区别  
        //Bitmap mAlphaBitmap = Bitmap.createBitmap(mBitmapDrawable.getIntrinsicWidth(), mBitmapDrawable.getIntrinsicHeight(), Config.ARGB_8888);  
        Bitmap mAlphaBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas mCanvas = new Canvas(mAlphaBitmap);
        Paint mPaint = new Paint();

        mPaint.setColor(color);
        //从原位图中提取只包含alpha的位图  
        Bitmap alphaBitmap = mBitmap.extractAlpha();
        //在画布上（mAlphaBitmap）绘制alpha位图  
        mCanvas.drawBitmap(alphaBitmap, 0, 0, mPaint);

        return mAlphaBitmap;

    }
}
