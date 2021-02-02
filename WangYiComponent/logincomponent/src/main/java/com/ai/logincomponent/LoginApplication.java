package com.ai.logincomponent;

import android.app.Application;
import com.ai.componentlib.AppCompat;
import com.ai.componentlib.ServiceFactory;

public class LoginApplication extends android.app.Application implements AppCompat{
    private static Application application;

    public static Application getApplication() {
        return application;
    }

    @Override
    public void initialize(Application app) {
        application = app;
        ServiceFactory.getInstance().setLoginService(new LoginService());
    }
}
