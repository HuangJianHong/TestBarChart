package com.cncn.retrofit2.Api.http;

import com.cncn.retrofit2.Api.api.BaseApi;
import com.cncn.retrofit2.Api.exception.RetryWhenNetworkException;
import com.cncn.retrofit2.Api.http.cookie.CookieInterceptor;
import com.cncn.retrofit2.Api.subscribers.ProgressSubscriber;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * http交互处理类:
 * Created by T163 on 2017/2/22.
 */

public class HttpManager {
    private volatile static HttpManager INSTANCE;

    //构造方法私有：
    private HttpManager() {
    }

    //单实例模式
    public static HttpManager getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpManager();
                }
            }
        }
        return INSTANCE;
    }


    /**
     * 处理Http请求
     *
     * @param baseapi 封装的请求数据；
     */
    public void doHttpDeal(BaseApi baseapi) {
        //手动创建一个OkHttpClient并设置超时时间缓存等设置
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(baseapi.getConnectionTime(), TimeUnit.SECONDS);
        if (baseapi.isCache()) {
            builder.addInterceptor(new CookieInterceptor(baseapi.isCache(), baseapi.getUrl()));
        }

        /*创建retrofit对象*/
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseapi.getBaseUrl())
                .build();

        /*rx处理*/
        ProgressSubscriber subscriber = new ProgressSubscriber(baseapi);

        Observable observable = baseapi.getObservable(retrofit)
                /*失败后的retry配置*/
                .retryWhen(new RetryWhenNetworkException())
                /*生命周期管理*/
//                .compose(basePar.getRxAppCompatActivity().bindToLifecycle())
                .compose(baseapi.getRxAppCompatActivity().bindUntilEvent(ActivityEvent.DESTROY))
                /*http请求线程*/
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                 /*回调线程*/
                .observeOn(AndroidSchedulers.mainThread())
                /*结果判断*/
                .map(baseapi);
        /*数据回调*/
        observable.subscribe(subscriber);
    }


}
