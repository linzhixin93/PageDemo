package com.page.demo.repo;

import com.blankj.utilcode.util.CacheDiskUtils;
import com.google.gson.Gson;
import com.wish.net.cache.ILocalRepo;

import java.lang.reflect.Type;

/**
 * author：LinZhiXin
 * date：2019/5/27
 * description：
 **/
public class LocalRepo<T> implements ILocalRepo<T> {

    private String key;
    private int time = -1;
    private Type type;

    public void setType(Type type) {
        this.type = type;
    }

    public LocalRepo(String key) {
        this.key = key;
    }

    public LocalRepo(String key, int time) {
        this.key = key;
        this.time = time;
    }

    @Override
    public void save(T data) {
        CacheDiskUtils.getInstance().put(key, new Gson().toJson(data), time);
    }

    @Override
    public T read() {
        String cacheString = CacheDiskUtils.getInstance().getString(key);
        if (cacheString == null) {
            return null;
        }
        T data = null;
        try {
            data = new Gson().fromJson(cacheString, type);
        } catch (Exception e) {

        }
        return data;
    }
}
