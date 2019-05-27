package com.wish.net.adapter;

import android.text.TextUtils;

import com.wish.common.utils.Ylog;
import com.wish.net.EncryptApi;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created At 2019/5/24 by ZhiXin.Lin
 * Description : 加密适配器
 */
public abstract class EncryptAdapter {

    private Set<String> encryptPathSet;

    /**
     * 要加密的Api的Class
     * @return
     */
    public abstract Class[] getEncryptApiClass();

    private Set<String> getEncryptPathSet() {
        if (getEncryptApiClass() == null) {
            return null;
        }

        Method[] methods = null;
        EncryptApi encryptApi = null;
        if (encryptPathSet == null) {
            encryptPathSet = new HashSet<>();
            for (Class apiCls : getEncryptApiClass()) {
                methods = apiCls.getMethods();
                for (Method method : methods) {
                    encryptApi = method.getAnnotation(EncryptApi.class);
                    if (encryptApi == null) {
                        continue;
                    }
                    String postUrl = method.getAnnotation(POST.class).value();
                    if (!TextUtils.isEmpty(postUrl)) {
                        encryptPathSet.add(postUrl);
                        continue;
                    }
                    String getUrl = method.getAnnotation(GET.class).value();
                    if (!TextUtils.isEmpty(getUrl)) {
                        encryptPathSet.add(getUrl);
                    }
                }
            }
        }
        return encryptPathSet;
    }

    public abstract String encrypt(String origData);

    public abstract String decrypt(String encryptData);

    public boolean needProceed(Request request) {
        if (getEncryptPathSet() == null || getEncryptPathSet().isEmpty()) {
            return false;
        }
        String url = request.url().toString();
        for (String path : getEncryptPathSet()) {
            if (url.contains(path)) {
                return true;
            }
        }
        return false;
    }

    public Response proceedResponse(Response response) {
        return response;
    }

    public Request proceedRequest(Request request) throws IOException {
        if (request.body() == null) {
            return request;
        }
        if ("GET".equalsIgnoreCase(request.method())) {
            return request;
        }
        if (request.body() instanceof FormBody) {
            return request;
        }
        Buffer buffer = new Buffer();
        request.body().writeTo(buffer);
        String postContent = buffer.readUtf8();
        Ylog.d("HttpLog -> before encrypt : " + postContent);
        String encryptContent = encrypt(postContent);
        RequestBody newBody = RequestBody.create(request.body().contentType(), encryptContent);
        return request.newBuilder().method(request.method(), newBody).build();
    }
}
