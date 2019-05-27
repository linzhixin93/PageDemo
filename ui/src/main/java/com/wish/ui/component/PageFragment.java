package com.wish.ui.component;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wish.ui.page.PageManager;
import com.wish.ui.page.Page;


/**
 * Created At 2019/5/24 by ZhiXin.Lin
 * Description :页面承载Fragment
 */
public class PageFragment extends Fragment implements IComponent {

    private Page mPage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mPage = PageManager.createPage(getArguments().getString(Page.NAME_KEY));
        View contentView = inflater.inflate(mPage.getLayoutId(), null);
        mPage.attachComponent(this);
        View rootView = mPage.onAttach(contentView);
        mPage.onCreate(rootView);
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mPage == null) {
            return;
        }
        if (isVisibleToUser) {
            mPage.onVisible();
        } else {
            mPage.onInvisible();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            mPage.onVisible();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint()) {
            mPage.onInvisible();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPage != null) {
            mPage.onDestroy();
            mPage = null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mPage != null) {
            mPage.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public int getColor(int id) {
        return getContext().getResources().getColor(id);
    }

    @Override
    public Drawable getDrawable(int id) {
        return getContext().getResources().getDrawable(id);
    }

    @Override
    public int getDimenSize(int dimenRes) {
        return getResources().getDimensionPixelSize(dimenRes);
    }

    @Override
    public Bundle getExtra() {
        return getArguments();
    }

    @Override
    public FragmentManager getPageFragmentManager() {
        return getFragmentManager();
    }

    @Override
    public Class hostClass() {
        return getClass();
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    @Override
    public LifecycleOwner getLifeOwner() {
        return this;
    }

}
