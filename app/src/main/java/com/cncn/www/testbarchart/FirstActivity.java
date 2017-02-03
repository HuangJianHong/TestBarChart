package com.cncn.www.testbarchart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cncn.hotfix.HotFixTest;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
    }


    public void hotfix(View view) {
        startActivity(new Intent(this, HotFixTest.class));
    }
}
