package com.wings.router;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dalvik.system.DexFile;

/**
 * @author -> Wings
 * @date -> 2020/8/27
 * @email -> ruanyandongai@gmail.com
 * 729368173@qq.com
 * @phone -> 18983790146
 * @blog -> https://ruanyandong.github.io
 * -> https://blog.csdn.net/qq_34681580
 */
public class Router {

    // 需要一个容器，装在所有模块里的activity的class对象 路由表
    private Map<String,Class<? extends Activity>> mMap;

    private Context context;

    private static final Router sRouter = new Router();

    private Router(){
        mMap = new HashMap<>();
    }
    public static Router getInstance() {
        return sRouter;
    }

    public void init(Context context){
        this.context = context;

        List<String> className = getClassName("com.wings.ARouter");
        for (String s:className) {
            try {
                Class<?> cls = Class.forName(s);
                // 这个类是否是IRouter接口的实现类
                if (IRouter.class.isAssignableFrom(cls)){
                    IRouter iRouter = (IRouter) cls.newInstance();
                    iRouter.putActivity();
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    // 通过包名获取这个包下面的所有类名
    private List<String> getClassName(String packageName){
        //创建一个class对象集合
        List<String> classList = new ArrayList<>();
        try {
            // 把当前应有的apk存储路径给dexFile
            DexFile dexFile = new DexFile(context.getPackageCodePath());
            Enumeration<String> entries = dexFile.entries();
            while (entries.hasMoreElements()){
                String className = entries.nextElement();
                if (className.contains(packageName)){
                    classList.add(className);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classList;
    }
    /**
     * 添加activity的类对象
     * @param key
     * @param clazz
     */
    public void addActivity(String key,Class<? extends Activity> clazz){
        if (key != null && clazz != null && !mMap.containsKey(key)){
            mMap.put(key,clazz);
        }
    }

    public void jumpActivity(String key, Bundle bundle){
        Class<? extends Activity> clazz = mMap.get(key);
        if (clazz == null){
            return;
        }
        Intent intent = new Intent(context,clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (bundle != null){
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }


}
