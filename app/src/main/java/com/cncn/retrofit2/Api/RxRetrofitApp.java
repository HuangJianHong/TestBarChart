package com.cncn.retrofit2.Api;

import android.app.Application;

/**
 * Created by T163 on 2017/2/22.
 */

public class RxRetrofitApp  {
    private static Application application;

    public static void init(Application app){
        setApplication(app);
    }

    public static Application getApplication() {
        return application;
    }

    private static void setApplication(Application application) {
        RxRetrofitApp.application = application;
    }
}
