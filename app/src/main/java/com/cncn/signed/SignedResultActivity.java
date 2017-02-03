package com.cncn.signed;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.cncn.test.constom.view.BitmapUtils;
import com.cncn.www.testbarchart.R;

public class SignedResultActivity extends AppCompatActivity {

    private ImageView goSigned;
    public static final int RQUEST_CODE_SIGNED = 2323;
    public static final int READ_SUCCESS = 1;
    private MyHandler  myHandle = new MyHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signed_result);

        goSigned = (ImageView) findViewById(R.id.goSigned);
        goSigned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(SignedResultActivity.this, SignedActivity.class), RQUEST_CODE_SIGNED);
            }
        });
    }


    public void again(View view) {
        startActivity(new Intent(this, SignedActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && data!=null){
            if (requestCode == RQUEST_CODE_SIGNED){
                final String path = data.getStringExtra(SignedActivity.PATH_BITMAP);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = BitmapUtils.convertToBitmap(path, 360, 600);
                        //System.out.println("TFT_SignedOrderActivity_bitmap:" + bitmap);
                        Message message = Message.obtain();
                        message.what = READ_SUCCESS;
                        message.obj = bitmap;
                        myHandle.sendMessage(message);
                        Log.e("read_success", "good");
                    }
                }).start();
            }
        }
    }

    /**
     * 图片旋转90度
     *
     * @param bitmap
     * @return
     */
    public Bitmap toturn(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(90); /*翻转90度*/
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap img = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return img;
    }

    class MyHandler extends  Handler{

        public MyHandler(){
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case READ_SUCCESS:
                    Bitmap msp = (Bitmap) msg.obj;
                    Bitmap bitmap = toturn(msp);
                    //System.out.println("TFT_SignedOrderActivity_bitmap_ui:" + mSignedBitmap);
                    goSigned.setImageBitmap(bitmap);
                    break;

                default:
                    break;
            }
        }
    }
}
