package com.cncn.www.testbarchart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cncn.OnTouch.Main4Activity;
import com.cncn.hotfix.HotFixTest;
import com.cncn.retrofit.RetrofitbaseTest;
import com.cncn.retrofit2.Api.use.TestActivity;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
    }


    public void hotfix(View view) {
        startActivity(new Intent(this, HotFixTest.class));
    }

    public void click(View view) {
        startActivity(new Intent(this, Main4Activity.class));
    }

    public void retrofit(View view) {
        startActivity(new Intent(this, RetrofitbaseTest.class));
    }

    public void retrofit2(View view) {
        startActivity(new Intent(this, TestActivity.class));
    }
}
