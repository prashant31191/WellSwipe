package com.well.swipe.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.well.swipe.R;
import com.well.swipe.utils.SwipeWindowManager;

/**
 * Created by mingwei on 3/9/16.
 */
public class SwipeLayout extends RelativeLayout implements AngleLayout.OnOffListener {

    //private WindowManager.LayoutParams mParams;

    //private WindowManager mManager;
    SwipeWindowManager mManager;

    private AngleLayout mAngleLayout;

    private LinearLayout mBgLayout;

    private int mWidth;

    private int mHeight;

    public SwipeLayout(Context context) {
        this(context, null);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mAngleLayout = (AngleLayout) findViewById(R.id.anglelayout);
        mAngleLayout.setOnOffListener(this);
        mBgLayout = (LinearLayout) findViewById(R.id.swipe_bg_layout);
        //initManager(0, 0);
        mManager = new SwipeWindowManager(0, 0, getContext());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    public void switchLeft() {
        show();
        mAngleLayout.setPositionLeft();
    }

    public void switchRight() {
        show();
        mAngleLayout.setPositionRight();
    }

    public void show() {
        mManager.show(this);
    }

    public void dismiss() {
        mManager.hide(this);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() != KeyEvent.ACTION_UP) {
            if (mAngleLayout.getEditState() == AngleLayout.STATE_EDIT) {
                mAngleLayout.setEditState(AngleLayout.STATE_NORMAL);
            } else if (mAngleLayout.getEditState() == AngleLayout.STATE_NORMAL) {
                mAngleLayout.off(mAngleLayout.getAngleLayoutScale());
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    /**
     * AngleLayout的大小变化
     *
     * @param scale
     */
    public void setScale(float scale) {
        mAngleLayout.setAngleLayoutScale(scale);
    }

    /**
     * 切换AngleLayout
     */
    public void switchAngleLayout() {
        mAngleLayout.switchAngleLayout();
    }

    public void on() {
        mAngleLayout.on();
    }

    @Override
    public void onOff() {
        dismiss();
    }

    @Override
    public void change(float alpha) {
        setSwipeBackgroundViewAlpha(alpha);
    }

    public void setSwipeBackgroundViewAlpha(float a) {
        mBgLayout.setAlpha(((int) (a * 10) / 10f));
    }

    public AngleLayout getAngleLayout() {
        return mAngleLayout;
    }
}
