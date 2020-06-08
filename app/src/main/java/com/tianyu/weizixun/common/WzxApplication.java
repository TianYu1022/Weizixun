package com.tianyu.weizixun.common;

import android.app.Application;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

public class WzxApplication extends Application {
    private static WzxApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        initUmeng();
    }

    private void initUmeng() {
        //签名： bcc386862c51d09af80d8a144c847558
        //AppKey： 5eddc808978eea085d11d931

        //QQ
        //APP ID： 1110585490
        //APP KEY： vSbEehIjP1xzkvZ6

        //微博
        //App Key：3223069997
        //App Secret：77a835a718aaf276ed71c754b66f53d7

        //appkey
        UMConfigure.init(this, "5eddc808978eea085d11d931", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");

        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        PlatformConfig.setSinaWeibo("3223069997", "77a835a718aaf276ed71c754b66f53d7","http://sns.whalecloud.com");
        PlatformConfig.setQQZone("1110585490", "vSbEehIjP1xzkvZ6");
    }

    public static WzxApplication getApp() {
        return app;
    }
}
