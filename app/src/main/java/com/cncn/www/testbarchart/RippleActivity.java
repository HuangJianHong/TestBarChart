package com.cncn.www.testbarchart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * Ripple水波纹效果，测试界面;
 */
public class RippleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ripple);
    }

    public void selectorRipple(View view) {
        Toast.makeText(this,"selectorRipple", Toast.LENGTH_SHORT).show();
    }

    public void shapeMask(View view) {
        Toast.makeText(this,"shapeMask", Toast.LENGTH_SHORT).show();
    }

    public void pictureMask(View view) {
        Toast.makeText(this,"pictureMask", Toast.LENGTH_SHORT).show();
    }

    public void colorMask(View view) {
        Toast.makeText(this,"colorMask", Toast.LENGTH_SHORT).show();
    }

    public void noMask(View view) {
        Toast.makeText(this,"noMask", Toast.LENGTH_SHORT).show();
    }
}
