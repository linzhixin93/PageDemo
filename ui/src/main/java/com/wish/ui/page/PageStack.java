package com.wish.ui.page;

import java.util.ArrayList;
import java.util.List;

/**
 * Created At 2019/5/25 by ZhiXin.Lin
 * Description :
 */
public class PageStack {

    private static List<Page> pageList = new ArrayList<>();

    public static void pushPage(Page page) {
        pageList.add(page);
    }

    public static void popPage(Page page) {
        if (pageList.isEmpty()) {
            return;
        }
        pageList.remove(page);
    }

    public static void finishAllPage() {
        for (Page page : pageList) {
            page.finish();
        }
    }

    public static <T extends Page> T findPage(Class<T> tClass) {
        if (pageList.isEmpty()) {
            return null;
        }
        for (int i = pageList.size() - 1; i >= 0; i--) {
            if (pageList.get(i).getClass() == tClass) {
                return (T) pageList.get(i);
            }
        }
        return null;
    }

    public static <T extends Page> List<T> findPages(Class<T> tClass) {
        if (pageList.isEmpty()) {
            return null;
        }
        List<T> tList = new ArrayList<>();
        for (int i = pageList.size() - 1; i >= 0; i--) {
            if (pageList.get(i).getClass() == tClass) {
                tList.add((T) pageList.get(i));
            }
        }
        return tList.isEmpty() ? null : tList;
    }

}
