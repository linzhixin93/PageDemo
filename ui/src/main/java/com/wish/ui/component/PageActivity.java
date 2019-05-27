package com.wish.ui.component;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.wish.ui.page.PageManager;
import com.wish.ui.page.Page;

/**
 * Created At 2019/5/25 by ZhiXin.Lin
 * Description : 页面承载Activity
 */
public class PageActivity extends AppCompatActivity implements IComponent {

    private Page mPage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = PageManager.createPage(getIntent().getStringExtra(Page.NAME_KEY));
        View contentView = LayoutInflater.from(this).inflate(mPage.getLayoutId(), null);
        mPage.attachComponent(this);
        View rootView = mPage.onAttach(contentView);
        setContentView(rootView);
        mPage.onCreate(rootView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPage != null) {
            mPage.onDestroy();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPage != null) {
            mPage.onVisible();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPage != null) {
            mPage.onInvisible();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mPage != null) {
            mPage.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (mPage != null) {
            mPage.onNewIntent(intent);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mPage != null) {
            mPage.onRestart();
        }
    }

    @Override
    public void onBackPressed() {
        if (mPage != null && mPage.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public int getDimenSize(int dimenRes) {
        return getResources().getDimensionPixelSize(dimenRes);
    }

    @Override
    public Context getContext() {
        return this;
    }


    @Override
    public Bundle getExtra() {
        return getIntent().getExtras();
    }

    @Override
    public FragmentManager getPageFragmentManager() {
        return getSupportFragmentManager();
    }

    @Override
    public Class hostClass() {
        return getClass();
    }

    @Override
    public LifecycleOwner getLifeOwner() {
        return this;
    }

}
