package com.cncn.retrofit2.Api.use;

import com.cncn.retrofit.data.RetrofitEntity;
import com.cncn.retrofit2.Api.api.BaseResultEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by T163 on 2017/2/23.
 */

public interface HttpPostService {

    @POST("AppFiftyToneGraph/videoLink")
    Call<RetrofitEntity> getAllVedio(@Body boolean once_no);

    @POST("AppFiftyToneGraph/videoLink")
    Observable<RetrofitEntity> getAllVedioBy(@Body boolean once_no);

    @GET("AppFiftyToneGraph/videoLink/{once_no}")
    Observable<BaseResultEntity<List<SubjectResulte>>> getAllVedioBys(@Query("once_no") boolean once_no);

}
