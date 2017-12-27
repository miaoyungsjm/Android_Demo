package com.example.viewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG1 = "MyLinearLayout";
    private static final String TAG2 = "MyButton";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 事件传递机制调用顺序
        MyLinearLayout mLin = (MyLinearLayout) findViewById(R.id.mLin);
        mLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG1, "OnClick Lin");
            }
        });

        MyButton mBtn = (MyButton) findViewById(R.id.mBtn);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG2, "OnClick Btn");
            }
        });

        mBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.v(TAG2, "OnLongClick Btn");
                return false;
            }
        });

        // onTouch > onLongClick > onClick
        mBtn.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        Log.v(TAG2, "onTouch ----> ACTION_DOWN");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.v(TAG2, "onTouch ----> ACTION_MOVE");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.v(TAG2, "onTouch ----> ACTION_UP");
                        break;
                    default:
                        break;
                }
                return false;
                // return true;
                // dispatchTouchEvent(MotionEvent event) return true 消费事件，
                // 不再触发 onTouchEvent(MotionEvent event)
            }
        });


    }
}
