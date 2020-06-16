package com.tianyu.weizixun.common;

import android.app.ActivityManager;
import android.app.Application;
import android.content.pm.PackageManager;

import androidx.multidex.MultiDex;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import java.util.Iterator;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

public class WzxApplication extends Application {
    private static WzxApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        //报错 Cannot fit requested classes in a single dex file (# methods: 66822 > 65536)
        MultiDex.install(this);
        initUmeng();
        initIm();
        initMap();
        initJPush();
    }

    private void initJPush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    /**
     * 初始化BaiDuMap
     */
    private void initMap() {
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }

    /**
     * 初始化 环信 IMSDK
     * 要求在 application 的oncreate方法中做初始化，初始化的时候需要传入设置好的 options。
     */
    private void initIm() {
        //AppKey: 1127200609181700#weizixundemo
        //Orgname: 1127200609181700
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        // 是否自动将消息附件上传到环信服务器，默认为True是使用环信服务器上传下载，如果设为 false，需要开发者自己处理附件消息的上传和下载
        options.setAutoTransferMessageAttachments(true);
        // 是否自动下载附件类消息的缩略图等，默认为 true 这里和上边这个参数相关联
        options.setAutoDownloadThumbnail(true);
        /**
         * 如果 APP 中有第三方的服务启动
         * 在初始化 SDK（EMClient.getInstance().init(applicationContext, options)）
         * 方法的前面添加以下相关代码
         * 使用 EaseUI 库的就不用理会这个。
         */
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

        if (processAppName == null || !processAppName.equalsIgnoreCase(this.getPackageName())) {
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
        //....
        //初始化
        EMClient.getInstance().init(this, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
    }

    /**
     * 如何获取processAppName请参考以下方法。
     *
     * @param pID
     * @return
     */
    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
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
        PlatformConfig.setSinaWeibo("3223069997", "77a835a718aaf276ed71c754b66f53d7", "http://sns.whalecloud.com");
        PlatformConfig.setQQZone("1110585490", "vSbEehIjP1xzkvZ6");

        //设置组件的log开关
        //参数boolean默认为false，如果需要查看log设置为true
        UMConfigure.setLogEnabled(true);
    }

    public static WzxApplication getApp() {
        return app;
    }
}
