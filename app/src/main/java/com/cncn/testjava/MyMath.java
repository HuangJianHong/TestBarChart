package com.cncn.testjava;

import android.util.Log;

/**
 * Created by  Hjh on 2018/2/10.
 * desc：
 */

public class MyMath {

    /**
     * 有参数
     *
     * @param num 参数
     * @return  返回值
     */
    public double square(int num) {
        return Math.pow(num, 2);
    }


    /**
     * 无参数
     * @return 返回9
     */
    public double square(){
        Log.i("java8:: ", "无参数的方法");
        return Math.pow(3,3);
    }
}
