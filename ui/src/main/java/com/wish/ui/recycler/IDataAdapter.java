package com.wish.ui.recycler;

import java.util.List;

/**
 * Created At 2019/5/25 by ZhiXin.Lin
 * Description : 列表Adapter接口
 */
public interface IDataAdapter<T> {

    /**
     * 改变全部数据
     */
    void changeAllItem(List<T> dataList);

    /**
     * 追加数据
     */
    void appendItem(List<T> dataList);

    /**
     * 删除数据
     */
    void deleteItem(int position);

    /*************  通知刷新  ***************/
    void refreshAllItem();

    void refreshItem(int position);

    void refreshItems(int start, int size);

    void refreshItemDelete(int position);
    /**************************************/


    /**
     * 查找数据Item
     */
    <R extends Item> R findItem(Class<R> tClass);

    <R extends Item> List<R> findItems(Class<R> tClass);

}
