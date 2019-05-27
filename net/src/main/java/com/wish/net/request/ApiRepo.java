package com.wish.net.request;

import android.util.Log;

import com.wish.common.utils.Ylog;
import com.wish.net.ApiInfo;
import com.wish.net.BuildConfig;
import com.wish.net.adapter.ParamAdapter;
import com.wish.net.adapter.EncryptAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created At 2019/5/24 by ZhiXin.Lin
 * Description :
 */
public class ApiRepo<T> {

    private static final long DEFAULT_TIME_OUT_SEC = 20;

    private OkHttpClient mOkHttpClient;

    private List<EncryptAdapter> mEncryptAdapters;

    private ParamAdapter mParamAdapter;

    private Class<T> mTClass;

    private String mBaseUrl;

    public ApiRepo(Class<T> tClass) {
        this.mTClass = tClass;
    }

    public T api() {
        if (mBaseUrl == null) {
            ApiInfo apiInfo = mTClass.getAnnotation(ApiInfo.class);
            if (apiInfo == null) {
                throw new RuntimeException("no api info");
            }
            mBaseUrl = BuildConfig.DEBUG ? apiInfo.debugUrl() : apiInfo.releaseUrl();
        }
        return getRetrofit().create(mTClass);
    }

    private Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient())
                .baseUrl(mBaseUrl)
                .build();
    }

    private OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(
                    message -> Ylog.longLog("HttpLog -> ", message));
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            mOkHttpClient = new OkHttpClient
                    .Builder()
                    .connectTimeout(DEFAULT_TIME_OUT_SEC, TimeUnit.SECONDS)
                    .readTimeout(DEFAULT_TIME_OUT_SEC, TimeUnit.SECONDS)
                    .writeTimeout( DEFAULT_TIME_OUT_SEC, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .addInterceptor(chain -> {
                        okhttp3.Request request = chain.request();
                        if (mParamAdapter != null) {
                            request = mParamAdapter.proceedRequest(request);
                        }
                        if (mEncryptAdapters == null || mEncryptAdapters.isEmpty()) {
                            return chain.proceed(request);
                        }
                        EncryptAdapter targetAdapter = null;
                        for (EncryptAdapter adapter : mEncryptAdapters) {
                            if (adapter.needProceed(request)) {
                                targetAdapter = adapter;
                                break;
                            }
                        }
                        if (targetAdapter == null) {
                            return chain.proceed(request);
                        }
                        okhttp3.Request newRequest = targetAdapter.proceedRequest(request);
                        Response response = chain.proceed(newRequest);
                        return targetAdapter.proceedResponse(response);
                    })
                    .addNetworkInterceptor(logInterceptor)
                    .build();
        }
        return mOkHttpClient;
    }

    public void setEncryptAdapter(EncryptAdapter encryptAdapter) {
        if (mEncryptAdapters == null) {
            mEncryptAdapters = new ArrayList<>();
        }
        mEncryptAdapters.add(encryptAdapter);
    }

    public void setParamAdapter(ParamAdapter adapter) {
        mParamAdapter = adapter;
    }



}
