package com.tianyu.weizixun.utils;

import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.tianyu.weizixun.common.Constants;
import com.tianyu.weizixun.common.WzxApplication;


public class UIModeUtil {
    /**
     * 夜间模式切换
     */
    public static void changeModeUI(AppCompatActivity activity) {
        int currentNightMode = activity.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        //当前Mode
        if (currentNightMode == Configuration.UI_MODE_NIGHT_NO) {
            //日间模式,切成夜间模式
            activity.getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            SharedPreferencesUtil.setParam(activity, Constants.MODE, AppCompatDelegate.MODE_NIGHT_YES);
            WzxApplication.mMode = AppCompatDelegate.MODE_NIGHT_YES;
        } else {
            activity.getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            SharedPreferencesUtil.setParam(activity, Constants.MODE, AppCompatDelegate.MODE_NIGHT_NO);
            WzxApplication.mMode = AppCompatDelegate.MODE_NIGHT_NO;
        }
    }

    /**
     * 设置当前的模式
     *
     * @param activity
     */
    public static void setActModeFromSp(AppCompatActivity activity) {
        int mode = (int) SharedPreferencesUtil.getParam(activity, Constants.MODE, AppCompatDelegate.MODE_NIGHT_NO);
        activity.getDelegate().setLocalNightMode(mode);
    }

    /**
     * 设置当前的模式
     *
     * @param activity
     */
    public static void setActModeUseMode(AppCompatActivity activity, int mode) {
        activity.getDelegate().setLocalNightMode(mode);
    }

    public static void setAppMode(int mode) {
        AppCompatDelegate.setDefaultNightMode(mode);
    }
}
