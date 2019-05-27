package com.page.demo.widget;

/**
 * Created At 2019/5/26 by ZhiXin.Lin
 * Description :
 */
public class PageState {


    public static final int STATE_LOADING = 0;
    public static final int STATE_OK = 1;
    public static final int STATE_EMPTY = 2;
    public static final int STATE_ERROR = 3;

    public int state;

    public String tip;

    public static PageState LOADING() {
        PageState pageState = new PageState();
        pageState.state = STATE_LOADING;
        return pageState;
    }

    public static PageState OK() {
        PageState pageState = new PageState();
        pageState.state = STATE_OK;
        return pageState;
    }

    public static PageState EMPTY() {
        PageState pageState = new PageState();
        pageState.state = STATE_EMPTY;
        return pageState;
    }

    public static PageState ERROR() {
        PageState pageState = new PageState();
        pageState.state = STATE_ERROR;
        return pageState;
    }

}
