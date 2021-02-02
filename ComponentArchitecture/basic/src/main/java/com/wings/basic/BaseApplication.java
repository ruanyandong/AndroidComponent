package com.wings.basic;

import android.app.Application;
/**
 * @author -> Wings
 * @date -> 2020/8/27
 * @email -> ruanyandongai@gmail.com
 * 729368173@qq.com
 * @phone -> 18983790146
 * @blog -> https://ruanyandong.github.io
 * -> https://blog.csdn.net/qq_34681580
 */
public class BaseApplication extends Application {
    protected final static boolean isApp = BuildConfig.is_application;
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
