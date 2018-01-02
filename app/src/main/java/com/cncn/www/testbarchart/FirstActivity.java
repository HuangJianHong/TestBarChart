package com.cncn.www.testbarchart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cncn.LoopereHandle.LoopTest;
import com.cncn.OnTouch.Main4Activity;
import com.cncn.hotfix.HotFixTest;
import com.cncn.retrofit.RetrofitbaseTest;
import com.cncn.retrofit2.Api.use.TestActivity;
import com.cncn.testjava.SimpleActivity;
import com.cncn.www.testbarchart.coordinatetest.ScrollingActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        ButterKnife.bind(this);
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

    public void handleMessage(View view) {
        startActivity(new Intent(this, LoopTest.class));
    }

    //类的加载顺序测试
    public void loadOrder(View view) {
        startActivity(new Intent(this, SimpleActivity.class));
    }

    @OnClick({R.id.CoordinatorLayoutTest})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.CoordinatorLayoutTest:
                startActivity(new Intent(this, ScrollingActivity.class));
                break;

            default:
                break;

        }

    }


}
