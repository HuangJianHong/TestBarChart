package com.cncn.retrofit2.Api.use;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cncn.retrofit.data.RetrofitEntity;
import com.cncn.retrofit2.Api.api.BaseResultEntity;
import com.cncn.retrofit2.Api.http.HttpManager;
import com.cncn.retrofit2.Api.listener.HttpOnNextListener;
import com.cncn.www.testbarchart.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 正常请求 和 封装请求的对比
 */
public class TestActivity extends RxAppCompatActivity {

    private TextView tvData;
    private HttpOnNextListener simpleOnNextListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        tvData = (TextView) findViewById(R.id.tvData);


        simpleOnNextListener = new HttpOnNextListener<List<SubjectResulte>>() {

            @Override
            public void onNext(List<SubjectResulte> subjectResultes) {
                tvData.setText("网络返回： \n" + subjectResultes.toString());
            }

            @Override
            public void onCacheNext(String string) {
                super.onCacheNext(string);
                Gson gson = new Gson();
                java.lang.reflect.Type type = new TypeToken<BaseResultEntity<List<SubjectResulte>>>() {
                }.getType();

                BaseResultEntity resultEntity = gson.fromJson(string, type);
                tvData.setText("缓存返回: \n" + resultEntity.getData().toString());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                tvData.setText("失败： \n" + e.toString());
            }

            @Override
            public void onCancel() {
                super.onCancel();
                tvData.setText("取消请求");
            }
        };
    }

    public void start(View view) {
        SubjectPostApi subjectPostApi = new SubjectPostApi(simpleOnNextListener, this);
        subjectPostApi.setAll(true);
        HttpManager instance = HttpManager.getInstance();
        instance.doHttpDeal(subjectPostApi);
    }


    /**********************************************************正常不封装使用**********************************/

    /**
     * Retrofit加入rxjava实现http请求
     */
    private void onButton9Click() {
        String BASE_URL="http://www.izaodao.com/Api/";
        //手动创建一个OkHttpClient并设置超时时间
        okhttp3.OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

//        加载框
        final ProgressDialog pd = new ProgressDialog(this);

        HttpPostService apiService = retrofit.create(HttpPostService.class);
        Observable<RetrofitEntity> observable = apiService.getAllVedioBy(true);
        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Subscriber<RetrofitEntity>() {
                            @Override
                            public void onCompleted() {
                                if (pd != null && pd.isShowing()) {
                                    pd.dismiss();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (pd != null && pd.isShowing()) {
                                    pd.dismiss();
                                }
                            }

                            @Override
                            public void onNext(RetrofitEntity retrofitEntity) {
                                tvData.setText("无封装：\n" + retrofitEntity.getData().toString());
                            }

                            @Override
                            public void onStart() {
                                super.onStart();
                                pd.show();
                            }
                        }
                );
    }

}
