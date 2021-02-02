package com.ai.componentlib;
public class ServiceFactory{

    private static final ServiceFactory instance = new ServiceFactory();
    public static ServiceFactory getInstance() {
        return instance;
    }
    private ServiceFactory(){
    }

    private ILoginService loginService;
    private IMineService mineService;

    public ILoginService getLoginService() {
        return loginService == null?new EmptyLoginService():loginService;
    }

    public void setLoginService(ILoginService loginService) {
        this.loginService = loginService;
    }

    public IMineService getMineService() {
        return mineService == null?new EmptyMineService():mineService;
    }

    public void setMineService(IMineService mineService) {
        this.mineService = mineService;
    }
}
