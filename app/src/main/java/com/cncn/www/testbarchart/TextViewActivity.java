package com.cncn.www.testbarchart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class TextViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view);

        TextView tvOne = (TextView) findViewById(R.id.tv_one);
        TextView  tvTwo = (TextView) findViewById(R.id.tv_two);

    }
}
