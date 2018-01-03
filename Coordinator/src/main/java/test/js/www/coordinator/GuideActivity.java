package test.js.www.coordinator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import test.js.www.coordinator.do_.do0.Main0Activity;

public class GuideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
    }

    public void Behavior(View view) {
        startActivity(new Intent(GuideActivity.this, Main0Activity.class));
    }
}
