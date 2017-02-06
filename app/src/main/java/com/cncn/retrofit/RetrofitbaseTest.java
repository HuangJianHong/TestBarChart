package com.cncn.retrofit;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.cncn.retrofit.data.RetrofitEntity;
import com.cncn.retrofit.data.SubjectInfo;
import com.cncn.www.testbarchart.R;

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

public class RetrofitbaseTest extends AppCompatActivity {

    public static final String BASE_URL="http://www.izaodao.com/Api/";
    private Button startRequest;
    private Retrofit retrofit;
    private TextView tvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofitbase_test);
        startRequest = (Button) findViewById(R.id.startRequest);
        tvData = (TextView) findViewById(R.id.tvMsg);

        initRetrofit();
        initData();
    }

    private void initRetrofit() {
        //手动创建一个OKhttpClient并设置超时时间;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();

    }

    private void initData() {
       final  ProgressDialog progressDialog = new ProgressDialog(this);

        startRequest.setOnClickListener(aVoid ->{
            HttpService apiService = retrofit.create(HttpService.class);
            Observable<RetrofitEntity> allVedioBy = apiService.getAllVedioBy(true);


            allVedioBy.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<RetrofitEntity>() {
                        @Override
                        public void onCompleted() {
                            if (progressDialog !=null && progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (progressDialog != null && progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                        }

                        @Override
                        public void onStart() {
                            super.onStart();
                            progressDialog.show();
                        }

                        @Override
                        public void onNext(RetrofitEntity retrofitEntity) {
                            List<SubjectInfo> data = retrofitEntity.getData();
                            tvData.setText(data.toString()+"");
                        }
                    });
        });
    }
}
