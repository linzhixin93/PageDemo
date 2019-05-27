package com.wish.net;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * author：LinZhiXin
 * date：2019/3/27
 * description：Api的注解，标注某些接口，不传某些Header
 **/
@Retention(RetentionPolicy.RUNTIME)
public @interface NoHeader {
    String[] headerKeys();
}
