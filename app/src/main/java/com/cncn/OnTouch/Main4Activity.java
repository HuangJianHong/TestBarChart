package com.cncn.OnTouch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.cncn.www.testbarchart.R;

import static android.R.attr.action;

public class Main4Activity extends AppCompatActivity {

    public static final String TAG ="Main4Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        Button btn = (Button) findViewById(R.id.btn);

//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e(TAG,  "onClick");
//            }
//        });

        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();   //ACTION_DOWN 0; Action_up 1;and so on;
                Log.e(TAG, "onTouch execute , action" + action);
                return false;
            }
        });

        initView();
    }

    private void initView() {
        ImageView iv = (ImageView) findViewById(R.id.iv);
//        iv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e(TAG,  "iv  "+"onClick");
//            }
//        });

        iv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e(TAG, "onTouch execute , action" + action);
                return false;
            }
        });

    }
}
















