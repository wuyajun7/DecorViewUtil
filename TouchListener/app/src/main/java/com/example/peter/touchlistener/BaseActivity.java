package com.example.peter.touchlistener;

import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

public class BaseActivity extends AppCompatActivity {

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            DecorViewUtil.eventListener(this, ev);

            return super.dispatchTouchEvent(ev);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}