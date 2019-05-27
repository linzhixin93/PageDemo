package com.wish.ui.page;

import com.wish.ui.component.PageActivity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * author：LinZhiXin
 * date：2019/5/13
 * description：
 **/
@Retention(RetentionPolicy.RUNTIME)
public @interface PageInfo {
    Class activity() default PageActivity.class;
}
