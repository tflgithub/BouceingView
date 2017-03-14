package com.cn.tfl.boucemenu;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

/**
 * Created by Happiness on 2017/3/10.
 */
public class BouncingView extends View {

    private Paint mPaint;
    private Path mPath;
    private int mMaxArcHeight;
    private int mArcHeight;
    private static Status mStatus = Status.NONE;
    private AnimationListener mAnimationListener;


    public enum Status {
        NONE,
        STATUS_UP,
        STATUS_DOWN
    }

    public BouncingView(Context context) {
        super(context);
        init();
    }

    public BouncingView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public BouncingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
        mMaxArcHeight = getResources().getDimensionPixelSize(R.dimen.arc_max_height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBG(canvas);
    }

    public void show() {
        mStatus = Status.STATUS_UP;
        if (mAnimationListener != null) {
            mAnimationListener.onStart();
            this.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mAnimationListener.onContentShow();
                }
            }, 600);
        }
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, mMaxArcHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                mArcHeight = value;

                if (value == mMaxArcHeight) {
                    bouncing();
                }
                invalidate();
            }
        });
        valueAnimator.setDuration(800);
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.start();
    }


    //反弹一下
    public void bouncing() {
        mStatus = Status.STATUS_DOWN;
        ValueAnimator valueAnimator = ValueAnimator.ofInt(mMaxArcHeight, 0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mArcHeight = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setDuration(500);
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.start();
    }

    private void drawBG(Canvas canvas) {
        mPath.reset();
        int currentPointY = 0;
        switch (mStatus) {
            case NONE:
                currentPointY = 0;
                break;
            case STATUS_UP:
                currentPointY = (int) (getHeight() * (1 - (float) mArcHeight / mMaxArcHeight) + mMaxArcHeight);
                break;
            case STATUS_DOWN:
                currentPointY = mMaxArcHeight;
                break;
        }
        mPath.moveTo(0, currentPointY);
        mPath.quadTo(getWidth() / 2, currentPointY - mArcHeight, getWidth(), currentPointY);
        mPath.lineTo(getWidth(), getHeight());
        mPath.lineTo(0, getHeight());
        mPath.lineTo(0, currentPointY);
        canvas.drawPath(mPath, mPaint);
    }


    public void setAnimationListener(AnimationListener animationListener) {
        mAnimationListener = animationListener;
    }

    public interface AnimationListener {

        void onStart();

        void onContentShow();

    }
}
