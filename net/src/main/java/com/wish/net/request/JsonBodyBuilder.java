package com.wish.net.request;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * author：LinZhiXin
 * date：2019/3/22
 * description：构造一个Json的RequestBody
 **/
public class JsonBodyBuilder extends JSONObject{


    public static JsonBodyBuilder newBuilder() {
        return new JsonBodyBuilder();
    }

    @Override
    public JsonBodyBuilder put(String name, int value) {
        try {
            super.put(name, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public JsonBodyBuilder put(String name, String value) {
        try {
            super.put(name, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public JsonBodyBuilder put(String name, Object value) {
        try {
            super.put(name, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public String getJson() {
        return toString();
    }

    public RequestBody buildRequestBody(String type) {
        return RequestBody.create(MediaType.parse(type), getJson());
    }

    public RequestBody buildDefaultBody() {
        return buildRequestBody("application/x-www-form-urlencoded; charset=utf-8");
    }

}
