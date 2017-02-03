package com.cncn.signed;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.cncn.test.constom.view.PaintViewSignature;
import com.cncn.www.testbarchart.R;

/**
 * 手写签名页面;
 */
public class SignedActivity extends AppCompatActivity {

    private PaintViewSignature mSignature;
    boolean slipSignFlag;    //是否已签名
    private static final int SAVE_SUCCESS = 0;
    public static final String PATH_BITMAP = "pathBitmp";
    public static final String BITMAP_RATIO = "bitmap_ratio";
    public static final String SLIP_SIGN_FLAG = "slipSignFlag";
    private MyHandler myHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signed);
        myHandler = new MyHandler();
        mSignature = (PaintViewSignature) findViewById(R.id.signPaintView);
    }
    public void again(View view) {  //清楚按钮；
        mSignature.removeAllPaint();
    }
    public void save(View view) {     //图片保存按钮;
        System.out.println("TFT_HandWriteSignatureActivity:" + "do....1");
        slipSignFlag = true;

        //开始保存图片
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = Message.obtain();
                message.what = SAVE_SUCCESS;
                String pathBitmap = mSignature.saveBitmap();
                Log.e("pathBitmap: ", pathBitmap);
                message.obj = pathBitmap;
                myHandler.sendMessage(message);
            }
        }).start();
    }


    class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SAVE_SUCCESS:
                    String pathBitmap = (String) msg.obj;
                    Intent intent = new Intent();
                    intent.putExtra(PATH_BITMAP, pathBitmap);
                    intent.putExtra(SLIP_SIGN_FLAG, slipSignFlag);
                    intent.putExtra(BITMAP_RATIO, mSignature.getBitmapRatio());
                    setResult(Activity.RESULT_OK, intent);
                    System.out.println("TFT_HandWriteSignatureActivity:" + "do....2");
                    finish();
                    break;
            }
        }
    }
}
