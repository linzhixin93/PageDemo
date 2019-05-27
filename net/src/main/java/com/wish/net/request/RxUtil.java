package com.wish.net.request;

import com.wish.common.utils.Ylog;
import com.wish.net.bean.NetBean;
import com.wish.net.error.NetException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * author：LinZhiXin
 * date：2019/4/26
 * description：请求工具
 **/
public class RxUtil {

    static <T> Observable<T> getObservableByStrategy(Request<T> wrapper) {
        switch (wrapper.getStrategy()) {
            case NetConstant.CACHE_ELSE_NET:
                return fetchCacheElseNet(wrapper);
            case NetConstant.NET_ELSE_CACHE:
                return fetchNetElseCache(wrapper);
            case NetConstant.ONLY_NET:
                return fetchNet(wrapper);
            case NetConstant.CACHE_BEFORE_NET:
                return fetchCacheBeforeNet(wrapper);
            default:
                return fetchCache(wrapper);
        }
    }


    /**
     * 取缓存
     */
    private static <T> Observable<T> fetchCache(Request<T> wrapper) {
        if (wrapper.getLocalRepo() == null) {
            return Observable.error(NetException.dataError());
        }
        T localData = wrapper.getLocalRepo().read();
        if (localData == null) {
            return Observable.error(NetException.dataError());
        }
        if (localData instanceof NetBean) {
            ((NetBean) localData).setFromCache(true);
        }
        return Observable.just(localData);
    }

    /**
     * 取网络
     */
    private static <T> Observable<T> fetchNet(Request<T> wrapper) {
        return wrapper.getRemoteRepo().compose(applyRootErrorHandler(wrapper));
    }

    /**
     * 先取网络，网络没有再取缓存
     */
    private static <T> Observable<T> fetchNetElseCache(Request<T> wrapper) {
        return Observable.concat(
                fetchNet(wrapper)
                        .compose(applyEmptyInsteadOfError(wrapper))
                        .compose(cacheNetData(wrapper)),
                fetchCache(wrapper))
                .firstElement()
                .toObservable();
    }

    /**
     * 先取缓存，没有缓存再取网络
     */
    private static <T> Observable<T> fetchCacheElseNet(Request<T> wrapper) {
        return Observable.concat(
                fetchCache(wrapper).compose(applyEmptyInsteadOfError(wrapper)),
                fetchNet(wrapper)).compose(cacheNetData(wrapper))
                .firstElement()
                .toObservable();
    }

    /**
     * 先取缓存，再取网络
     *
     * @param wrapper
     * @param <T>
     * @return
     */
    private static <T> Observable<T> fetchCacheBeforeNet(Request<T> wrapper) {
        return Observable.concat(
                fetchCache(wrapper).compose(applyEmptyInsteadOfError(wrapper)),
                fetchNet(wrapper).compose(cacheNetData(wrapper)));
    }

    /**
     * 请求完成，更新缓存
     */
    private static <T> ObservableTransformer<T, T> cacheNetData(final Request<T> wrapper) {
        return upstream -> upstream.doOnNext((Consumer<T>) t -> {
            if (wrapper.getLocalRepo() == null) {
                return;
            }
            wrapper.getLocalRepo().save(t);
        });
    }

    /**
     * empty代替error
     */
    private static <T> ObservableTransformer<T, T> applyEmptyInsteadOfError(final Request<T> wrapper) {
        return upstream -> upstream.onErrorResumeNext((Function<Throwable, ObservableSource<? extends T>>) throwable -> {
            Ylog.e(throwable, " empty error -> " + wrapper.getTag());
            return Observable.empty();
        });
    }


    /**
     * empty代替error
     */
    public static <T> ObservableTransformer<T, T> applyEmptyInsteadOfError() {
        return upstream -> upstream.onErrorResumeNext((Function<Throwable, ObservableSource<? extends T>>) throwable -> {
            Ylog.e(throwable, " empty error");
            return Observable.empty();
        });
    }

    /**
     * 常用的线程调度
     */
    public static <T> ObservableTransformer<T, T> applyDefaultSchedulers() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 顶层错误过滤
     */
    private static <T> ObservableTransformer<T, T> applyRootErrorHandler(final Request<T> wrapper) {
        return upstream -> upstream.doOnError(throwable -> Ylog.e(throwable, wrapper.getTag() + " onError"))
                .map((Function<T, T>) t -> {
                    if (t == null) {
                        throw NetException.dataError();
                    }
                    if (t instanceof NetBean) {
                        if (((NetBean) t).getCode() != 200) {
                            throw new NetException(((NetBean) t).getMessage(), ((NetBean) t).getCode());
                        }
                        if (((NetBean) t).getData() == null) {
                            throw NetException.dataError();
                        }
                    }
                    return t;
                });
    }


}
