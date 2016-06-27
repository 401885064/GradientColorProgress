package com.mayi.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.mayi.sample.R;
import com.mayi.utils.ImgUtil;

/**
 * Created by Administrator on 2016/6/23 0023.
 *
 * @Author CaiWF
 * @Email 401885064@qq.com
 * @TODO 进度条渐变色
 */
public class GradientColorProgressView extends View {

    /**
     *分段颜色
     */
    private static int[] SECTION_COLORS = null;
    private static final String TAG = "SpringProgressView";
    /**
     * 进度条最大值
     */
    private float maxCount;
    /**
     * 进度条当前值
     */
    private float currentCount;
    /**
     * 画笔
     */
    private Paint mPaint;

    private int mWidth, mHeight;
    /**
     * 进度条的高度，默认是20px
     */
    private final int seekHeight = 20;
    /**
     * 进度条thumb的外圆圈的直径大小，默认是20px
     */
    private final int seekThumbHeight = 20;
    /**
     * 进度条的高度位置
     */
    private int locationHeight = 0;
    /**
     * 进度条的宽度
     */
    private int locationWidth = 0;
    /**
     * 进度条离左边的间距，为保证thumb不被遮掉
     */
    private int paddingLeft = 20;
    /**
     * 游标图片的高度，默认48
     */
    private int bitmap_height = 48;
    /**
     * 游标图片的宽度，默认120
     */
    private int bitmap_width = 120;

    public GradientColorProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public GradientColorProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public GradientColorProgressView(Context context) {
        super(context);
        initView(context);
    }

    /**
     * 初始化数据
     * @param context
     */
    private void initView(Context context) {
        SECTION_COLORS = getResources().getIntArray(R.array.spring_progress_colors);
        if (SECTION_COLORS == null)
            initSpringProgressColors();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        int round = mHeight / 2;
        locationHeight = mHeight - 10;
        locationWidth = mWidth - paddingLeft;


        mPaint.setColor(Color.GRAY);
        RectF rectBg = new RectF(paddingLeft, locationHeight - seekHeight, locationWidth, locationHeight);//背景
        canvas.drawRoundRect(rectBg, round, round, mPaint);//圆角矩形

        float section = currentCount / maxCount;//进度条百分比
        float unit_section = 1f / SECTION_COLORS.length;
        int colors_size = (int) ((section / unit_section) + 1);
        if (colors_size > SECTION_COLORS.length) {
            colors_size = SECTION_COLORS.length;
        }

        RectF rectProgressBg = new RectF(paddingLeft, locationHeight - seekHeight, locationWidth * section, locationHeight);

        int[] colors = new int[colors_size];
        if (section > 0) {

            System.arraycopy(SECTION_COLORS, 0, colors, 0, colors_size);

            if (colors_size < 2) {
                mPaint.setColor(colors[0]);
            } else {
                //colors中的数量必须大于2个
                LinearGradient shader = new LinearGradient(paddingLeft, locationHeight - seekHeight, locationWidth * section, locationHeight, colors, null, Shader.TileMode.REPEAT);
                mPaint.setShader(shader);
            }


        } else {
            mPaint.setColor(Color.TRANSPARENT);
        }

        canvas.drawRoundRect(rectProgressBg, round, round, mPaint);


        if (section > 0) {
            mPaint.setShader(null);
            if (section < 0.1) {
                //画外圆圈
                mPaint.setColor(colors[colors.length - 1]);
                canvas.drawCircle(paddingLeft + locationWidth * section, locationHeight - seekHeight / 2, seekThumbHeight, mPaint);
                //画内圆圈
                mPaint.setColor(Color.WHITE);
                canvas.drawCircle(paddingLeft + locationWidth * section, locationHeight - seekHeight / 2, seekThumbHeight / 2, mPaint);
            } else {
                //画外圆圈
                mPaint.setColor(colors[colors.length - 1]);
                canvas.drawCircle(locationWidth * section, locationHeight - seekHeight / 2, seekThumbHeight, mPaint);
                //画内圆圈
                mPaint.setColor(Color.WHITE);
                canvas.drawCircle(locationWidth * section, locationHeight - seekHeight / 2, seekThumbHeight / 2, mPaint);
            }


            Bitmap bitmap = null;
            if (section > 0.7) {
                float left = locationWidth * section - bitmap_width;
                float top = locationHeight - bitmap_height * 1.8f;
                float right = locationWidth * section;
                float bottom = locationHeight - bitmap_height * 0.8f;
                //画图片
                bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.img_display_number_bj_02);
                canvas.drawBitmap(bitmap, left, top, mPaint);

                drawText(canvas, left, top, right, bottom, currentCount + "");
            } else {
                float left = locationWidth * section;
                float top = locationHeight - bitmap_height * 1.8f;
                float right = locationWidth * section + bitmap_width;
                float bottom = locationHeight - bitmap_height * 0.8f;

                if (section < 0.1) {//最左边
                    left = left + paddingLeft;
                }

                bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.img_display_number_bj_01);
                canvas.drawBitmap(bitmap, left, top, mPaint);

                drawText(canvas, left, top, right, bottom, currentCount + "");
            }
        }
    }

    /**
     * 画文字
     * @param canvas
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @param value
     */
    private void drawText(Canvas canvas, float left, float top, float right, float bottom, String value) {
        Rect targetRect = new Rect((int) left, (int) top, (int) right, (int) bottom);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(3);
        paint.setTextSize(dipToPx(12));
        paint.setColor(Color.TRANSPARENT);
        canvas.drawRect(targetRect, paint);

        paint.setColor(Color.WHITE);
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        int baseline = (targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(value, targetRect.centerX(), baseline, paint);
    }

    private int dipToPx(int dip) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }

    /***
     * 设置最大的进度值
     *
     * @param maxCount
     */
    public void setMaxCount(float maxCount) {
        this.maxCount = maxCount;
    }

    /***
     * 设置当前的进度值
     *
     * @param currentCount
     */
    public void setCurrentCount(float currentCount) {
        this.currentCount = currentCount > maxCount ? maxCount : currentCount;
        invalidate();
    }

    public float getMaxCount() {
        return maxCount;
    }

    public float getCurrentCount() {
        return currentCount;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        bitmap_height = ImgUtil.getBitmapHeight(getResources(), R.mipmap.img_display_number_bj_01);
        bitmap_width = ImgUtil.getBitmapWidth(getResources(), R.mipmap.img_display_number_bj_01);
        if (bitmap_height == 0) {
            bitmap_height = 48;
        }

        if (widthSpecMode == MeasureSpec.EXACTLY || widthSpecMode == MeasureSpec.AT_MOST) {
            mWidth = widthSpecSize;
        } else {
            mWidth = 0;
        }
        if (heightSpecMode == MeasureSpec.AT_MOST || heightSpecMode == MeasureSpec.UNSPECIFIED) {
            mHeight = dipToPx(17) + bitmap_height;
        } else {
            mHeight = heightSpecSize;
        }
//        Logger.e("mHeight :" + mHeight + "  mWidth :" + mWidth + "  heightSpecSize :" + heightSpecSize + " bitmap_height:" + bitmap_height + " bitmap_width :" + bitmap_width);
        setMeasuredDimension(mWidth, mHeight);
    }


    /**
     * 初始化颜色数据值
     */
    private void initSpringProgressColors() {
        SECTION_COLORS = new int[10];
        SECTION_COLORS[0] = getResources().getColor(R.color.spring_progress_color_1);
        SECTION_COLORS[1] = getResources().getColor(R.color.spring_progress_color_2);
        SECTION_COLORS[2] = getResources().getColor(R.color.spring_progress_color_3);
        SECTION_COLORS[3] = getResources().getColor(R.color.spring_progress_color_4);
        SECTION_COLORS[4] = getResources().getColor(R.color.spring_progress_color_5);
        SECTION_COLORS[5] = getResources().getColor(R.color.spring_progress_color_6);
        SECTION_COLORS[6] = getResources().getColor(R.color.spring_progress_color_7);
        SECTION_COLORS[7] = getResources().getColor(R.color.spring_progress_color_8);
        SECTION_COLORS[8] = getResources().getColor(R.color.spring_progress_color_9);
        SECTION_COLORS[9] = getResources().getColor(R.color.spring_progress_color_10);
    }

}
