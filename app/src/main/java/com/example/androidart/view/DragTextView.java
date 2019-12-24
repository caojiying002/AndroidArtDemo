package com.example.androidart.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatTextView;

import static android.view.MotionEvent.ACTION_CANCEL;
import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_UP;

import static com.example.androidart.TAGs.TAG_VIEW_MOVEMENT;

public class DragTextView extends AppCompatTextView {

    public DragTextView(Context context) {
        super(context);
    }

    public DragTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DragTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private float mLastRawX, mLastRawY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float rawX = event.getRawX();
        float rawY = event.getRawY();
        switch (event.getActionMasked()) {
            case ACTION_DOWN:
                break;
            case ACTION_MOVE:
                float deltaX = rawX - mLastRawX;
                float deltaY = rawY - mLastRawY;
                this.setTranslationX(getTranslationX() + deltaX);
                this.setTranslationY(getTranslationY() + deltaY);
                break;
            case ACTION_UP:
            case ACTION_CANCEL:
            default:
                break;
        }
        mLastRawX = rawX;
        mLastRawY = rawY;
        return true;    /* handled */
    }
}
