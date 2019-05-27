package com.wish.net.cache;

import java.lang.reflect.Type;

/**
 * Created At 2019/5/24 by ZhiXin.Lin
 * Description : 本地数据源
 */
public interface ILocalRepo<T> {

    void setType(Type type);

    void save(T data);

    T read();
}
