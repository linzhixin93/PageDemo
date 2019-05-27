package com.page.demo.pages;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;

import com.page.demo.App;
import com.page.demo.widget.PageState;
import com.page.demo.widget.StateView;
import com.wish.common.msg.IMessageHandler;
import com.wish.common.msg.MessageEvent;
import com.wish.common.msg.MessageObserver;
import com.wish.common.msg.MessageUtils;
import com.wish.ui.page.LifeState;
import com.wish.ui.page.Page;

/**
 * Created At 2019/5/26 by ZhiXin.Lin
 * Description :
 */
public abstract class BasePage extends Page implements IMessageHandler {

    private StateView mStateView;
    private MessageObserver messageObserver;

    @Override
    public View onAttach(View view) {
        view = super.onAttach(view);
        mStateView = new StateView(getComponent().getContext());
        mStateView.setLayoutParams(
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
        mStateView.setContentView(view);
        mStateView.setOnReloadListener(getReloadListener());
        messageObserver = new MessageObserver(this);
        return mStateView;
    }

    @Override
    public void onCreate(View view) {
        MessageUtils.register(messageObserver);
    }

    public void showContentView() {
        mStateView.showContentView();
    }

    public void showStateView() {
        mStateView.showStateView();
    }

    public void showStateView(String tip) {
        mStateView.showStateView(tip);
    }

    public void showLoading(boolean show) {
        mStateView.showLoading(show);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (messageObserver != null) {
            MessageUtils.unregister(messageObserver);
            messageObserver = null;
        }
    }

    @Override
    public void onGlobalMsg(MessageEvent event) {

    }

    @Override
    public void onVisibleMsg(MessageEvent event) {

    }

    @Override
    public void onUniqueMsg(MessageEvent event) {

    }

    @Override
    public boolean isVisible() {
        return getLifeState() == LifeState.VISIBLE;
    }

    @Override
    public String getTag() {
        return TAG;
    }

    protected StateView.OnReloadListener getReloadListener() {
        return null;
    }

    protected Observer<PageState> getPageStateObserver() {
        return pageState -> {
            if (pageState == null) {
                return;
            }
            if (pageState.state == PageState.STATE_LOADING) {
                showLoading(true);
            } else if (pageState.state == PageState.STATE_OK) {
                showLoading(false);
                showContentView();
            } else if (pageState.state == PageState.STATE_EMPTY || pageState.state == PageState.STATE_ERROR) {
                if (pageState.tip == null) {
                    showStateView();
                } else {
                    showStateView(pageState.tip);
                }
            }
        };
    }

    protected <T extends ViewModel> T getViewModel(Class<T> tClass) {
        if (getComponent() == null) {
            return ViewModelProvider.AndroidViewModelFactory.getInstance(App.getApp()).create(tClass);
        }
        if (getComponent() instanceof FragmentActivity) {
            return ViewModelProviders.of((FragmentActivity) getComponent()).get(tClass);
        } else if (getComponent() instanceof Fragment) {
            return ViewModelProviders.of((Fragment) getComponent()).get(tClass);
        }
        return ViewModelProvider.AndroidViewModelFactory.getInstance(App.getApp()).create(tClass);
    }
}
