package com.example.viewtest;


import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

public class MyButton extends AppCompatButton{
    private static final String TAG = "MyButton";

    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                Log.v(TAG, "onTouchEvent ----> ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.v(TAG, "onTouchEvent ----> ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.v(TAG, "onTouchEvent ----> ACTION_UP");
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.v(TAG, "dispatchTouchEvent");
        return super.dispatchTouchEvent(event); //false;
    }
}
