package com.cncn.LoopereHandle;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by T163 on 2017/2/24.
 */

public class LoopTest extends AppCompatActivity {

    /*
    *张鸿洋博客 详解 Looper Handler Thread的关系:
    * http://blog.csdn.net/lmj623565791/article/details/38377229
    *
    * 郭霖： 异步消息处理机制；
    * http://blog.csdn.net/guolin_blog/article/details/9991569
    *
    *
    * Android HandlerThread 完全解析
    *http://blog.csdn.net/lmj623565791/article/details/47079737
    *
    *
    * Android的几种多线程方式（AsyncTask,HandlerThread,IntentService,ThreadPool），使用场景以及注意事项
    *http://www.jianshu.com/p/34cffd700f75#
    * (1) AsyncTask
    * (2) HandlerThread
    * (3) IntentService
    * (4) ThreadPool
    * */

    private Handler handlerMain;
    private Handler handlerSon;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        handlerMain = new Handler();


        new  Thread("子线程创建 handler"){
            @Override
            public void run() {
                super.run();
                //new Looper有系统自己创建， Loop方法进行轮询MessageQueue; msg.target.dispatchMessage（）
                Looper.prepare();     // prepare方法只能够调用一次， !=null 时候，会抛出异常 Only one Looper may be created per thread
                handlerSon = new Handler();

                Looper.loop();
            }
        }.start();

        for9plus9();


        HandlerThread handleThread = new HandlerThread("handleThread");



    }


    /**
     * Handler总是依附于 创建时所在的线程；
     */
    class LooperThread extends  Thread{
        public Handler mHandler;
        @Override
        public void run() {
            super.run();
            Looper.prepare();
            mHandler = new Handler(){  //在主线程中可以直接创建Handler对象，而在子线程中需要先调用Looper.prepare()才能创建Handler对象。
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                }
            };
            Looper.loop();
        }
    }


    /**
     * 使用一个For循环，实现9*9乘法表
     */
    public void for9plus9(){
        for (int i = 1, j=1; i < 10; i++) {
            Log.i("9*9",i +"*" +j +"="+i*j +"\t");
            if (i == j){
                i =0;
                j++;
                System.out.println();
            }
        }
    }

}
