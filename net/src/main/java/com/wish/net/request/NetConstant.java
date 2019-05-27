package com.wish.net.request;

import java.util.HashMap;
import java.util.Map;

/**
 * author：LinZhiXin
 * date：2019/3/14
 * description：网络常量类
 **/
public class NetConstant {

    /**
     * 只拿网络
     */
    public static final int ONLY_NET = 0;

    /**
     * 只拿缓存
     */
    public static final int ONLY_CACHE = 1;

    /**
     * 优先拿缓存，缓存没有，再跑网络
     */
    public static final int CACHE_ELSE_NET = 2;

    /**
     * 优先拿网络，网络失败，再拿缓存
     */
    public static final int NET_ELSE_CACHE = 3;

    /**
     * 先拿缓存，拿缓存之后，再拿网络
     */
    public static final int CACHE_BEFORE_NET = 4;



}
