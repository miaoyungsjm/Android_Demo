package com.example.viewtest;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;


public class MyLinearLayout extends LinearLayout{
    private static final String TAG = "MyLinearLayout";

    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                Log.v(TAG, "onTouchEvent;----> ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.v(TAG, "onTouchEvent;----> ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.v(TAG, "onTouchEvent;----> ACTION_UP");
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.v(TAG, "dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.v(TAG, "onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);     // true
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        Log.v(TAG, "requestDisallowInterceptTouchEvent");
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }
}
