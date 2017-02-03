package com.cncn.config;

import android.app.Application;
import android.content.Context;

/**
 * <请描述这个类是干什么的>
 *
 * @author fuxj@cncn.net
 * @data: 16/9/19 下午2:40
 * @version: V1.0
 */
public class App extends Application {

    private static Context sContext;

    public static Context getContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext=this;
        JUtils.initialize(this);
    }
}
