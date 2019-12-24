package com.example.androidart.intercept;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;

import androidx.annotation.RequiresApi;

import static android.view.MotionEvent.ACTION_CANCEL;
import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_UP;

/**
 * 本例中父控件为{@link HorizontalScrollView2}
 */
public class ListView2 extends ListView {
    private static final String TAG = "ListView2";

    private int mLastX = 0;
    private int mLastY = 0;

    public ListView2(Context context) {
        super(context);
        init();
    }

    public ListView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ListView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ListView2(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getActionMasked()) {
            case ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case ACTION_MOVE:
                float deltaX = x - mLastX;
                float deltaY = y - mLastY;

                Log.d(TAG, "dx:" + deltaX + " dy:" + deltaY);
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case ACTION_UP:
            case ACTION_CANCEL:
            default:

                break;
        }

        mLastX = x;
        mLastY = y;
        return super.dispatchTouchEvent(ev);
    }
}
