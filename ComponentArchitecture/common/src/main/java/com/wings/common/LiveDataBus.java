package com.wings.common;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author -> Wings
 * @date -> 2020/8/30
 * @email -> ruanyandongai@gmail.com
 * 729368173@qq.com
 * @phone -> 18983790146
 * @blog -> https://ruanyandong.github.io
 * -> https://blog.csdn.net/qq_34681580
 *
 * 管理所有LiveData
 */

public class LiveDataBus {

    private static final LiveDataBus sBus = new LiveDataBus();


    // 存储所有liveData的容器
    private Map<String, BusMutableLiveData<Object>> mMap;


    private LiveDataBus(){
        mMap = new HashMap<>();
    }

    public static LiveDataBus getInstance() {
        return sBus;
    }


    public synchronized <T> BusMutableLiveData<T> with(String key,Class<T> type){
        if (!mMap.containsKey(key)){
            mMap.put(key, new BusMutableLiveData<Object>());
        }
        return (BusMutableLiveData<T>) mMap.get(key);
    }


    public class BusMutableLiveData<T> extends MutableLiveData<T>{
        // 是否是粘性事件  false：需要粘性事件  true：不需要粘性事件
        private boolean isViscosity = false;

        public void observe(@NonNull LifecycleOwner owner, boolean isViscosity, @NonNull Observer<T> observer){
            this.isViscosity = isViscosity;
            observe(owner,observer);
        }

        @Override
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
            super.observe(owner, observer);
            try {
                // 不需要粘性事件
                if (isViscosity){
                    // 通过反射获取到mVersion，mLastVersion，将mVersion的值给mLastVersion
                    hook(observer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }
        }

        /**
         * 拦截onChange方法的执行
         * @param observer
         */
        private void hook(@NonNull Observer<? super T> observer) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
            // 获取到LiveData的类对象
            Class<LiveData> liveDataClass = LiveData.class;
            // 根据类对象获取到mVersion的反射对象
            Field mVersionField = liveDataClass.getDeclaredField("mVersion");

            mVersionField.setAccessible(true);

            Field mObserversFiled = liveDataClass.getDeclaredField("mObservers");

            mObserversFiled.setAccessible(true);
            // 获取到当前对象中这个成员变量的值
            Object mObservers = mObserversFiled.get(this);
            // 获取Observers这个map的get方法
            Method get = mObservers.getClass().getDeclaredMethod("get",Object.class);

            get.setAccessible(true);

            Object invokeEntry = get.invoke(mObservers,observer);

            Object observerWrapper = null;

            if (invokeEntry != null && invokeEntry instanceof Map.Entry){
                observerWrapper = ((Map.Entry)invokeEntry).getValue();
            }
            if (observerWrapper == null){
                throw new NullPointerException("observerWrapper 不能为空");
            }
            Class<?> wrapperClass = observerWrapper.getClass();
            Class<?> aClass =  observerWrapper.getClass().getSuperclass();

            Field mLastVersionField = aClass.getDeclaredField("mLastVersion");

            mLastVersionField.setAccessible(true);
            Object o = mVersionField.get(this);
            mLastVersionField.set(observerWrapper,o);
        }

    }

}
