package com.cncn.retrofit2.Api.api;

/**
 * 回调信息统一的封装类
 */
public class BaseResultEntity<T> {

    private int ret;          //  判断标示： 返回码
    private String msg;        //    提示信息
    private T data;            //显示数据（用户需要关心的数据）

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
