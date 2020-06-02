package com.tianyu.weizixun.common;

import android.app.Application;

public class WzxApplication extends Application {
    private static WzxApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static WzxApplication getApp() {
        return app;
    }
}
