package com.cncn.testjava;

/**
 * Created by Administrator on 2017/10/11.
 * 类的加载顺序测试
 */

public class B {
    static int i = 1;  //静态变量


    static {   //静态语句块
        System.out.println("Class B1:static blocks:   i= " + i);
    }

    //非静态变量
    int j = 1;

    static {       //静态语句块
        i++;
        System.out.println("Class B2:static blocks:   i= " + i);
    }

    //构造函数
    public B() {
        i++;
        j++;
        System.out.println("constructor B:   " + "i=" + i + ", j=" + j);
    }


    {           //非静态语句块
        i++;
        j++;
        System.out.println("Class B:common blocks  " + "i=" + i + ", j=" + j);
    }


    //非静态方法
    public void bDisplay() {
        i++;
        System.out.println("Class B:static void bDisplay():  " + "i=" + i + ",j=" + j);
        return;
    }

    public static void bTest() {   //静态方法
        i++;
        System.out.println("Class B:static void bTest():  " + "i=" + i);
        return;
    }
}
