package com.cncn.retrofit2.Api.use;

import com.cncn.retrofit2.Api.api.BaseApi;
import com.cncn.retrofit2.Api.listener.HttpOnNextListener;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by T163 on 2017/2/23.
 */

public class SubjectPostApi extends BaseApi {

    //    接口需要传入的参数 可自定义不同类型
    private boolean all;

    public SubjectPostApi(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity) {
        super(listener, rxAppCompatActivity);
        setShowProgress(true);
        setCancel(true);
        setCache(true);
        setMothed("AppFiftyToneGraph/videoLink");
        setCookieNetWorkTime(60);
        setCookeiNoNetWorkTime(24*60*60);
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpPostService service = retrofit.create(HttpPostService.class);
        return service.getAllVedioBys(isAll());
    }



    public boolean isAll() {
        return all;
    }

    public void setAll(boolean all) {
        this.all = all;
    }
}
