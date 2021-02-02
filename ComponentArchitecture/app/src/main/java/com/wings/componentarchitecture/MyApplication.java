package com.wings.componentarchitecture;

import android.app.Application;

import com.wings.basic.BaseApplication;
import com.wings.router.Router;

/**
 * @author -> Wings
 * @date -> 2020/8/27
 * @email -> ruanyandongai@gmail.com
 * 729368173@qq.com
 * @phone -> 18983790146
 * @blog -> https://ruanyandong.github.io
 * -> https://blog.csdn.net/qq_34681580
 */
public class MyApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        // 项目的初始化工作
        Router.getInstance().init(this);
    }
}
