package com.wish.ui.page;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.wish.ui.component.PageActivity;
import com.wish.ui.component.PageFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created At 2019/5/24 by ZhiXin.Lin
 * Description : 页面管理类
 */
public class PageManager {

    private static  Map<String, PageBuilder> builderMap = new HashMap<>();

    public synchronized  static <T extends Page> void openPage(Context context, Class<T> tClass) {
        PageInfo pageInfo = tClass.getAnnotation(PageInfo.class);
        if (getPageBuilder(tClass.getName()) == null) {
            register(tClass.getName(), new CommonPageBuilder<>(tClass));
        }
        Intent intent = new Intent(context, pageInfo == null ? PageActivity.class : pageInfo.activity());
        Bundle bundle = new Bundle();
        bundle.putString(Page.NAME_KEY, tClass.getName());
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public synchronized static <T extends Page> Fragment getFragment(Class<T> tClass) {
        if (getPageBuilder(tClass.getName()) == null) {
            register(tClass.getName(), new CommonPageBuilder<>(tClass));
        }
        PageFragment lazyFragment = new PageFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Page.NAME_KEY, tClass.getName());
        lazyFragment.setArguments(bundle);
        return lazyFragment;
    }

    public static <T extends Page> T createPage(String name) {
        PageBuilder<T> pageBuilder = getPageBuilder(name);
        if (pageBuilder == null) {
            try {
                pageBuilder = new CommonPageBuilder<T>((Class<T>) Class.forName(name));
                register(name, pageBuilder);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return pageBuilder == null ? null : pageBuilder.buildPage();
    }

    private synchronized static <T extends Page> void register(String name, PageBuilder<T> builder) {
        builderMap.put(name, builder);
    }

    private synchronized static <T extends Page> PageBuilder<T> getPageBuilder(String name) {
        return builderMap.get(name);
    }

    private static class CommonPageBuilder<T extends Page> extends PageBuilder<T> {

        private Class<T> pageCls;

        CommonPageBuilder(Class<T> pageCls) {
            this.pageCls = pageCls;
        }

        @Override
        public T buildPage() {
            try {
                return pageCls.newInstance();
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

}
