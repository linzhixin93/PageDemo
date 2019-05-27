package com.wish.net;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * author：LinZhiXin
 * date：2019/3/14
 * description：接口信息注解
 **/
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiInfo {
    String releaseUrl() default "";
    String debugUrl() default "";
}
