package com.cncn.retrofit2.Api.api;

import com.cncn.retrofit2.Api.exception.HttpTimeException;
import com.cncn.retrofit2.Api.listener.HttpOnNextListener;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.lang.ref.SoftReference;

import retrofit2.Retrofit;
import rx.Observable;
import rx.functions.Func1;

/**
 *  * 请求数据统一封装类:  去除掉 ret msg等信息，只返回T data给用户
 */

public  abstract  class BaseApi<T> implements Func1<BaseResultEntity<T>, T> {


    @Override
    public T call(BaseResultEntity<T>  HttpResultEntity) {
        if (HttpResultEntity.getRet() == 0){
                throw new HttpTimeException(HttpResultEntity.getMsg());
        }
        return HttpResultEntity.getData();
    }

    /**
     * 设置参数
     *
     * @param retrofit
     * @return
     */
    public abstract Observable getObservable(Retrofit retrofit);


    //rx生命周期管理
    private SoftReference<RxAppCompatActivity>  rxAppCompatActivity;         //弱引用
    /*回调*/
    private SoftReference<HttpOnNextListener>  listener;                    //弱引用
    /*是否能取消加载框*/
    private boolean cancel;
    /*是否显示加载框*/
    private boolean showProgress;
    /*是否需要缓存处理*/
    private boolean cache;
    /*基础url*/
    private String baseUrl="http://www.izaodao.com/Api/";

    /*方法-如果需要缓存必须设置这个参数；不需要不用設置*/
    private String mothed;

    /*超时时间-默认6秒*/
    private int connectionTime =6;
    /*有网情况下的本地缓存时间默认60秒*/
    private int cookieNetWorkTime = 60;
    /*无网络的情况下本地缓存时间默认30天*/
    private  int cookeiNoNetWorkTime = 24 *60*60*30;

    public BaseApi(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity){
        setListener(listener);
        setRxAppCompatActivity(rxAppCompatActivity);
        setShowProgress(true);
        setCache(true);
    }


    /*
      * 获取当前rx生命周期
      * @return
      */
    public RxAppCompatActivity getRxAppCompatActivity() {
        return rxAppCompatActivity.get();
    }

    public void setRxAppCompatActivity(RxAppCompatActivity rxAppCompatActivity) {
        this.rxAppCompatActivity = new SoftReference(rxAppCompatActivity);
    }

    public SoftReference<HttpOnNextListener> getListener() {
        return listener;
    }

    public void setListener(HttpOnNextListener listener) {
        this.listener = new SoftReference(listener);
    }

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public boolean isShowProgress() {
        return showProgress;
    }

    public void setShowProgress(boolean showProgress) {
        this.showProgress = showProgress;
    }

    public boolean isCache() {
        return cache;
    }

    public void setCache(boolean cache) {
        this.cache = cache;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getMothed() {
        return mothed;
    }

    public void setMothed(String mothed) {
        this.mothed = mothed;
    }

    public int getConnectionTime() {
        return connectionTime;
    }

    public void setConnectionTime(int connectionTime) {
        this.connectionTime = connectionTime;
    }

    public int getCookieNetWorkTime() {
        return cookieNetWorkTime;
    }

    public void setCookieNetWorkTime(int cookieNetWorkTime) {
        this.cookieNetWorkTime = cookieNetWorkTime;
    }

    public int getCookeiNoNetWorkTime() {
        return cookeiNoNetWorkTime;
    }

    public void setCookeiNoNetWorkTime(int cookeiNoNetWorkTime) {
        this.cookeiNoNetWorkTime = cookeiNoNetWorkTime;
    }
}
