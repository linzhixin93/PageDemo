package com.page.demo.model;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.page.demo.App;
import com.page.demo.repo.Api;
import com.page.demo.widget.PageState;
import com.wish.net.request.DisposableHelper;

import java.net.ConnectException;
import java.net.SocketTimeoutException;


/**
 * Created At 2019/5/26 by ZhiXin.Lin
 * Description :
 */
public class BaseViewModel extends ViewModel {

    protected String TAG = getClass().getSimpleName();

    private DisposableHelper disposableHelper;

    private MutableLiveData<PageState> pageStateLiveData;


    protected Api getApi() {
        return App.getApp().getApi().api();
    }

    final public MutableLiveData<PageState> getPageState() {
        if (pageStateLiveData == null) {
            pageStateLiveData = new MutableLiveData<>();
        }
        return pageStateLiveData;
    }

    final protected DisposableHelper getDisposableHelper() {
        if (disposableHelper == null) {
            disposableHelper = new DisposableHelper(TAG);
        }
        return disposableHelper;
    }

    final protected void onPageStateLoading() {
        getPageState().postValue(PageState.LOADING());
    }

    final protected void onPageStateOk() {
        getPageState().postValue(PageState.OK());
    }

    final protected void onPageStateEmpty() {
        getPageState().postValue(PageState.EMPTY());
    }

    final protected void onPageStateError() {
        getPageState().postValue(PageState.ERROR());
    }

    final protected void onPageStateEmpty(String tip) {
        PageState empty = PageState.EMPTY();
        empty.tip = tip;
        getPageState().postValue(empty);
    }

    final protected void onPageStateError(String tip) {
        PageState error = PageState.ERROR();
        error.tip = tip;
        getPageState().postValue(error);
    }

    final protected void onPageStateError(Throwable throwable) {
        PageState error = PageState.ERROR();
        if (throwable instanceof SocketTimeoutException || throwable instanceof ConnectException) {
            error.tip = "网络出错，点击重试";
        } else {
            error.tip = "数据出错，点击重试";
        }
        getPageState().postValue(error);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        getDisposableHelper().clear();
    }
}
