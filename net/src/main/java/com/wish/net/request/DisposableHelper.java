package com.wish.net.request;

import com.wish.common.utils.Ylog;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * author：LinZhiXin
 * date：2019/4/29
 * description：请求管理
 **/
public class DisposableHelper {

    private String TAG = "DisposableHelper";

    private Map<String, Disposable> disposableMap;

    private CompositeDisposable compositeDisposable;

    private static DisposableHelper defaultInstance = new DisposableHelper("Default");

    public static DisposableHelper getDefault() {
        return defaultInstance;
    }

    public DisposableHelper(String tag) {
        TAG = tag;
        disposableMap = new HashMap<>();
    }

    public void add(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    public void add(String tag, Disposable disposable) {
        if (tag == null) {
            return;
        }
        synchronized (this) {
            Disposable lastOne = disposableMap.get(tag);
            if (lastOne == null) {
                disposableMap.put(tag, disposable);
                return;
            }
            if (!lastOne.isDisposed()) {
                lastOne.dispose();
            }
            disposableMap.put(tag, disposable);
        }
    }

    public void dispose(String tag) {
        if (tag == null) {
            return;
        }
        synchronized (this) {
            Disposable disposable = disposableMap.get(tag);
            if (disposable == null) {
                return;
            }
            if (!disposable.isDisposed()) {
                disposable.dispose();
            }
        }
    }

    public void clear() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
            compositeDisposable = null;
        }
        Set<Map.Entry<String, Disposable>> entrySet = disposableMap.entrySet();
        for (Map.Entry<String, Disposable> entry : entrySet) {
            if (entry.getValue().isDisposed()) {
                continue;
            }
            Ylog.i(TAG, "clear -> " + entry.getKey());
            entry.getValue().dispose();
        }
        disposableMap.clear();
    }

}
