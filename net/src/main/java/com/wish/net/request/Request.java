package com.wish.net.request;


import com.wish.net.cache.ILocalRepo;

import java.lang.reflect.Type;

import io.reactivex.Observable;

/**
 * author：LinZhiXin
 * date：2019/3/14
 * description：请求包括类，用于配置请求策略，缓存等信息
 **/
public class Request<T> {

    /**
     * 打印tag
     */
    private String tag;

    /**
     * 本地数据源
     */
    private ILocalRepo<T> localRepo;

    /**
     * 网络数据源
     */
    private Observable<T> remoteRepo;

    /**
     * 请求策略，默认只取网络
     */
    private int strategy = NetConstant.ONLY_NET;

    /**
     * 避免Gson在反序列化时候的泛型擦除问题，需要指定type
     */
    private Type type;

    public static <T> Request<T> from(Observable<T> remoteRepo) {
        Request<T> request = new Request<>();
        return request.remoteRepo(remoteRepo);
    }

    public Request<T> localRepo(ILocalRepo<T> localRepo) {
        this.localRepo = localRepo;
        return this;
    }

    private Request<T> remoteRepo(Observable<T> remoteRepo) {
        this.remoteRepo = remoteRepo;
        return this;
    }

    /**
     * 配置请求策略
     * @param strategy
     * @return
     */
    public Request<T> strategy(int strategy) {
        this.strategy = strategy;
        return this;
    }


    /**
     * 配置泛型type
     * @param type
     * @return
     */
    public Request<T> type(Type type) {
        this.type = type;
        if (localRepo != null) {
            localRepo.setType(type);
        }
        return this;
    }

    /**
     * 设置打印的tag
     * @param tag
     * @return
     */
    public Request<T> tag(String tag) {
        this.tag = tag;
        return this;
    }

    String getTag() {
        return tag;
    }

    int getStrategy() {
        return strategy;
    }

    Observable<T> getRemoteRepo() {
        return remoteRepo;
    }

    ILocalRepo<T> getLocalRepo() {
        if (localRepo != null) {
            localRepo.setType(type);
        }
        return localRepo;
    }

    public Observable<T> toObservable() {
        return RxUtil.getObservableByStrategy(this);
    }

    Type getType() {
        return type;
    }
}
