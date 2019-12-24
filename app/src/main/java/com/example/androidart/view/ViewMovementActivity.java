package com.example.androidart.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.androidart.BaseAppCompatActivity;
import com.example.androidart.R;

import static com.example.androidart.TAGs.TAG_VIEW_MOVEMENT;

public class ViewMovementActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private Button mButton1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_movement);
        initView();
    }

    private void initView() {
        mButton1 = findViewById(R.id.button1);
        mButton1.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        if (v == mButton1) {
            Log.d(TAG_VIEW_MOVEMENT, "button1.left=" + mButton1.getLeft());
            Log.d(TAG_VIEW_MOVEMENT, "button1.x=" + mButton1.getX());

            // 方法1. 通过设置translationX/translationY实现位移
            ObjectAnimator.ofFloat(v, "translationX", 100).setDuration(1000).start();

            /*
            // 方法2. 通过设置LayoutParams实现位移，也可以修改宽度等（动画效果需要额外控制）
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            params.leftMargin += 100;
            v.setLayoutParams(params);
            //v.requestLayout();
             */

            /*
            // 方法3. 通过ValueAnimator实现位移
            ValueAnimator animator = ValueAnimator.ofInt(0, 1).setDuration(1000);   // 这个例子中只用到了fraction，范围[0.0f, 1.0f]，所以这里写ofInt(0, 10)或者ofFloat(0, 1)之类都没关系
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float fraction = animation.getAnimatedFraction();
                    Log.v(TAG_VIEW_MOVEMENT, "fraction=" + fraction + ", value = " + animation.getAnimatedValue());
                    v.setTranslationX(fraction * 100);
                }
            });
            animator.start();
             */
        }
    }
}
