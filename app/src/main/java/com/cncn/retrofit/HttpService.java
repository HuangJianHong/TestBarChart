package com.cncn.retrofit;

import com.cncn.retrofit.data.RetrofitEntity;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by T163 on 2017/2/6.
 */

public interface HttpService {

    @POST("AppFiftyToneGraph/videoLink")
    Observable<RetrofitEntity> getAllVedioBy(@Body boolean once_no);

    @POST("AppFiftyToneGraph/videoLink")
    Observable<RetrofitEntity> getAllVedio(@Body boolean noce_no);

}
