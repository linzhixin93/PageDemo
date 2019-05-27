package com.page.demo;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.page.demo.repo.Api;
import com.wish.net.request.ApiRepo;

/**
 * author：LinZhiXin
 * date：2019/5/27
 * description：
 **/
public class App extends Application {

    private static App instance;

    private ApiRepo<Api> apiRepo;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Utils.init(this);
    }

    public static App getApp() {
        return instance;
    }

    public ApiRepo<Api> getApi() {
        if (apiRepo == null) {
            apiRepo = new ApiRepo<>(Api.class);
        }
        return apiRepo;
    }
}