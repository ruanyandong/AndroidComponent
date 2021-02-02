package com.ai.minecomponent;

import android.app.Application;

import com.ai.componentlib.AppCompat;
import com.ai.componentlib.ServiceFactory;

public class MineApplication extends Application implements AppCompat {
    private static Application application;

    public static Application getApplication() {
        return application;
    }

    @Override
    public void initialize(Application app) {
        application = app;
        ServiceFactory.getInstance().setMineService(new MineService());
    }
}
