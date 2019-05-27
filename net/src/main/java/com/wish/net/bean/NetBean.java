package com.wish.net.bean;

/**
 * Copyright (c) 2019, 四川绿源集科技有限公司 All rights reserved.
 * author：LinZhiXin
 * date：2019/3/14
 * description：基本网络Bean
 **/
public class NetBean<T> {

    private int code;

    private String message;

    private T data;

    private boolean fromCache;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isFromCache() {
        return fromCache;
    }

    public void setFromCache(boolean fromCache) {
        this.fromCache = fromCache;
    }
}
