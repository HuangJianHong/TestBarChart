package com.cncn.hotfix;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.cncn.www.testbarchart.R;

public class HotFixTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_fix_test);
    }

    public void show(View view) {
        Toast.makeText(this, "This is hotfix activity", Toast.LENGTH_SHORT).show();
    }
}
