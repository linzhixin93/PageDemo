package com.wish.net.adapter;

import com.wish.net.NoHeader;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Request;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created At 2019/5/24 by ZhiXin.Lin
 * Description : 参数适配器
 */
public abstract class ParamAdapter {

    private Map<String, String[]> dropHeaderMap;

    public abstract Class[] getApiClass();

    public abstract Map<String, String> getCommonParam();

    public abstract Map<String, String> getCommonHeaders();

    public Request proceedRequest(Request request) {
        Map<String, String> commonHeader = getCommonHeaders();

        //添加公共头
        if (commonHeader != null && !commonHeader.isEmpty()) {
            if (dropHeaderMap == null) {
                initDropHeaderMap();
            }
            dropHeader(request.url().toString(), commonHeader);
            request = addHeaders(request, commonHeader);
        }

        if (getCommonParam() == null || getCommonParam().isEmpty()) {
            return request;
        }

        if ("GET".equals(request.method())) {
            return buildGetRequest(request);
        }
        return buildPostRequest(request);
    }

    /**
     * 通过url判断是否要丢弃某个Header
     * @param url 请求的url
     * @param commonHeader 原来的HeaderMap
     */
    public void dropHeader(String url, Map<String, String> commonHeader) {
        if (dropHeaderMap.isEmpty()) {
            return;
        }
        Set<Map.Entry<String, String[]>> entrySet = dropHeaderMap.entrySet();

        for (Map.Entry<String, String[]> entry : entrySet) {
            if (!url.contains(entry.getKey())) {
                continue;
            }
            for (String removeHeader : entry.getValue()) {
                commonHeader.remove(removeHeader);
            }
        }
    }

    /**
     * 初始化要丢弃的Header的Map
     */
    public void initDropHeaderMap() {
        NoHeader noHeader = null;
        Method[] methods = null;
        dropHeaderMap = new HashMap<>();
        for (Class apiCls : getApiClass()) {
            methods = apiCls.getMethods();
            for (Method method : methods) {
                noHeader = method.getAnnotation(NoHeader.class);
                if (noHeader == null || noHeader.headerKeys() == null
                        || noHeader.headerKeys().length == 0) {
                    continue;
                }
                POST post = method.getAnnotation(POST.class);
                if (post != null) {
                    dropHeaderMap.put(post.value(), noHeader.headerKeys());
                    return;
                }

                GET get = method.getAnnotation(GET.class);
                if (get != null) {
                    dropHeaderMap.put(get.value(), noHeader.headerKeys());
                }
            }
        }
    }

    public Request addHeaders(Request request, Map<String, String> commonHeader) {
        Headers.Builder headerBuilder = request.headers().newBuilder();
        Set<Map.Entry<String, String>> entrySet = commonHeader.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            headerBuilder.add(entry.getKey(), entry.getValue());
        }
        return request.newBuilder().headers(headerBuilder.build()).build();
    }

    public Request buildPostRequest(Request request) {
        // TODO: 2019/3/19 暂时只处理表单提交
        if (request.body() == null || !(request.body() instanceof FormBody)) {
            return request;
        }
        Set<Map.Entry<String, String>> entrySet = getCommonParam().entrySet();
        FormBody.Builder builder = new FormBody.Builder();
        if (request.body() instanceof FormBody) {
            FormBody formBody = (FormBody) request.body();
            for (int i = 0; i < formBody.size(); i++) {
                builder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
            }
        }
        for (Map.Entry<String, String> entry : entrySet) {
            builder.add(entry.getKey(), entry.getValue());
        }
        FormBody newFormBody = builder.build();
        return request.newBuilder().post(newFormBody).build();
    }

    public Request buildGetRequest(Request request) {
        Set<Map.Entry<String, String>> entrySet = getCommonParam().entrySet();

        HttpUrl.Builder urlBuilder = request.url().newBuilder();
        for (int i = 0; i < request.url().querySize(); i++) {
            urlBuilder.addQueryParameter(request.url().queryParameterName(i), request.url().queryParameterValue(i));
        }
        for (Map.Entry<String, String> entry : entrySet) {
            urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
        }
        return request.newBuilder().url(urlBuilder.build()).build();
    }
}
