package com.cncn.testjava;


/**
 * Created by Administrator on 2017/10/11.
 */

public class A extends B {
    //静态变量
    static int i = 1;

    static {      //静态语句块
        System.out.println("Class A1:static blocks：i= " + i);
    }

    //非静态变量
    int j = 1;

    static {      //静态语句块
        i++;
        System.out.println("Class A2:static blocks：i=" + i);
    }

    //构造函数
    public A() {
        super();
        i++;
        j++;
        System.out.println("constructor A: " + "i=" + i + ",j=" + j);
    }


    {              //非静态语句块
        i++;
        j++;
        System.out.println("Class A:common blocks  " + "i=" + i + ", j=" + j);
    }


    public void aDisplay() {       //非静态方法
        i++;
        System.out.println("Class A:static void aDisplay():  " + "i=" + i + ", j=" + j);
        return;
    }


    public static void aTest() {    //静态方法
        i++;
        System.out.println("Class A:static void aTest():    " + "i=" + i);
        return;
    }

}
