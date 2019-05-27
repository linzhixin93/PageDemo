package com.wish.ui.component;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentManager;


/**
 * Created At 2019/5/25 by ZhiXin.Lin
 * Description : 组件接口，提供各种android资源
 */
public interface IComponent {

    @ColorInt
    int getColor(@ColorRes int id);

    String getString(@StringRes int id);

    String getString(@StringRes int id, Object... formatArgs);

    Drawable getDrawable(@DrawableRes int id);

    int getDimenSize(int dimenRes);

    Context getContext();

    Bundle getExtra();

    FragmentManager getPageFragmentManager();

    Class hostClass();

    void finish();

    LifecycleOwner getLifeOwner();

}
