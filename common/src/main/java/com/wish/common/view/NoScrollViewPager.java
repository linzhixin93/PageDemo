package com.wish.common.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * author：LinZhiXin
 * date：2019/3/25
 * description：不左右滑动的ViewPager
 **/
public class NoScrollViewPager extends ViewPager {

    private boolean scrollAble;

    public NoScrollViewPager(@NonNull Context context) {
        super(context);
    }

    public NoScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    public boolean isScrollAble() {
        return scrollAble;
    }

    public void setScrollAble(boolean scrollAble) {
        this.scrollAble = scrollAble;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!scrollAble) {
            return false;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (!scrollAble) {
            return false;
        }
        return super.onInterceptTouchEvent(event);
    }
}
