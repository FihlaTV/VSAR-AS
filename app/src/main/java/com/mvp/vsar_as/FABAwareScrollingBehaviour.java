package com.mvp.vsar_as;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class FABAwareScrollingBehaviour extends AppBarLayout.ScrollingViewBehavior {
    public FABAwareScrollingBehaviour(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    private int mXonTouchDown;
    private int mXonTouchUp;

    public boolean onInterceptTouchEvent(CoordinatorLayout parent, FloatingActionButton child, MotionEvent ev) {


        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            mXonTouchDown = (int) ev.getY();
        } else if (ev.getAction() == MotionEvent.ACTION_UP) {
            mXonTouchUp = (int) ev.getY();
        }
        if(mXonTouchDown > mXonTouchUp)
            child.hide();
        else
            child.show();

        return super.onInterceptTouchEvent(parent, child, ev);
    }
}