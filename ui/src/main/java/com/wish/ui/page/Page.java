package com.wish.ui.page;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.view.View;

import com.wish.common.utils.Ylog;
import com.wish.ui.component.IComponent;

/**
 * Created At 2019/5/25 by ZhiXin.Lin
 * Description : 页面接口
 */
public abstract class Page {

    protected String TAG = getClass().getSimpleName();

    public static final String NAME_KEY = "page_name";

    private IComponent iComponent;

    private LifeState lifeState;

    @LayoutRes
    public abstract int getLayoutId();

    final public void attachComponent(IComponent component) {
        iComponent = component;
    }

    public View onAttach(View view) {
        return view;
    }

    public void onCreate(View view) {
        Ylog.i(TAG, "onCreate");
        PageStack.pushPage(this);
        lifeState = LifeState.CREATED;
    }

    public void onVisible() {
        Ylog.i(TAG, "onVisible");
        lifeState = LifeState.VISIBLE;
    }

    public void onInvisible() {
        Ylog.i(TAG, "onInvisible");
        lifeState = LifeState.INVISIBLE;
    }

    public void onDestroy() {
        Ylog.i(TAG, "onDestroy");
        PageStack.popPage(this);
        lifeState = LifeState.DESTROY;
    }

    public void onNewIntent(Intent intent) {
        Ylog.i(TAG, "onNewIntent");
    }

    public void onRestart() {
        Ylog.i(TAG, "onRestart");
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Ylog.i(TAG, "onActivityResult");
    }

    public boolean onBackPressed() {
        return false;
    }

    public void finish() {
        iComponent.finish();
    }

    protected IComponent getComponent() {
        return iComponent;
    }

    public LifeState getLifeState() {
        return lifeState;
    }
}
