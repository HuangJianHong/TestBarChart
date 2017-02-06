package com.cncn.retrofit.data;

import java.util.List;

/**
 * Created by T163 on 2017/2/6.
 */

public class RetrofitEntity {

    private int ret;
    private String msg;
    private List<SubjectInfo> data;

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

    public List<SubjectInfo> getData() {
        return data;
    }

    public void setData(List<SubjectInfo> data) {
        this.data = data;
    }

}
