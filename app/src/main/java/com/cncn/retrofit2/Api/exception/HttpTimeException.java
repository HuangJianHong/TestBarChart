package com.cncn.retrofit2.Api.exception;

/**
 * 自定义错误信息，统一处理返回处理
 * Created by T163 on 2017/2/22.
 */

public class HttpTimeException extends  RuntimeException {
    public static final int NO_DATA = 0X2;

    public HttpTimeException(int  resultCode){
            getApiExceptionMessage(resultCode);
    }

    public HttpTimeException(String detialMessage){
        super(detialMessage);
    }


    /**
     *  转换错误数据
     * @param code
     * @return
     */
    private  static String getApiExceptionMessage(int code){
        String message ="";
        switch (code){
            case  NO_DATA:
                message = "无数据";
                break;
            default:
                message = "error";
                break;
        }
        return  message;
    }



}
