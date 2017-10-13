package com.example.jsoup.jsoupdemo.app;

import android.app.Application;

import com.example.jsoup.jsoupdemo.BuildConfig;
import com.example.jsoup.jsoupdemo.base.CrashHandler;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.RxRetrofitApp;
import com.zhy.autolayout.config.AutoLayoutConifg;

/**
 * Created by Administrator on 2017/9/14.
 */

public class MyBaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AutoLayoutConifg.getInstance().useDeviceSize();
        RxRetrofitApp.init(this, BuildConfig.DEBUG);
        CrashHandler.getInstance().init(this);
    }
}
