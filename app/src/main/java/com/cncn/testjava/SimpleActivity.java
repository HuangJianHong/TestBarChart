package com.cncn.testjava;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.cncn.www.testbarchart.R;

import java.util.function.Function;

public class SimpleActivity extends Activity {
    //http://www.cnblogs.com/xyczero/p/4002786.html  参考自网页
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_acitvity);

        A a = new A();
        a.aDisplay();


/**
 10-11 16:04:00.788 1119-1119/com.cncn.www.testbarchart I/System.out: Class B1:static blocks1           父类的静态块 [按照代码顺序， 静态块，静态语句]
 10-11 16:04:00.788 1119-1119/com.cncn.www.testbarchart I/System.out: Class B2:static blocks2
 10-11 16:04:00.788 1119-1119/com.cncn.www.testbarchart I/System.out: Class A1:static blocks1           子类的静态块
 10-11 16:04:00.788 1119-1119/com.cncn.www.testbarchart I/System.out: Class A2:static blocks2
 10-11 16:04:00.788 1119-1119/com.cncn.www.testbarchart I/System.out: Class B:common blocksi=3,j=2      父类的成员变量[非静态块]
 10-11 16:04:00.788 1119-1119/com.cncn.www.testbarchart I/System.out: constructor B: i=4,j=3            父类的构造方法

 10-11 16:04:00.788 1119-1119/com.cncn.www.testbarchart I/System.out: Class A:common blocksi=3,j=2      子类的成员变量[即 非静态块]
 10-11 16:04:00.788 1119-1119/com.cncn.www.testbarchart I/System.out: constructor A: i=4,j=3            子类的构造方法【实例创建成功4】

 10-11 16:04:00.788 1119-1119/com.cncn.www.testbarchart I/System.out: Class A:static void aDisplay(): i=5,j=3        //调用方法【方法都是被动调用的，】
 *
 */
    }


    @Override
    protected void onStart() {
        super.onStart();
        //Java 8双冒号::方法引用操作符            //https://majing.io/posts/10000003971188
        MyMath myMath = new MyMath();
        Function<Integer, Double> square1 = myMath::square;
//        Double apply = square1.apply(5);         //apply方法，真正调用该函数
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Log.i("onStart() java8::= ", square1.apply(3)+"");
        }

        Runnable runnable = myMath::square;
        runnable.run();       //需要有run方法，它采取执行
    }
}
