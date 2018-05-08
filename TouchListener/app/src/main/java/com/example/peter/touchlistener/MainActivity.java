package com.example.peter.touchlistener;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends BaseActivity {

    private Button btn_view;
    private TextView tv_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_view = (Button) findViewById(R.id.btn_view);
        btn_view.setTag(false);

        tv_view = (TextView) findViewById(R.id.tv_view);
        tv_view.setTag(false);

        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        tv_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}
