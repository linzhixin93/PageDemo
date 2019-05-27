package com.wish.ui.recycler;

import android.view.ViewGroup;

/**
 * Created At 2019/5/25 by ZhiXin.Lin
 * Description :
 */
public interface ILoadMoreFooter {

    Holder createHolder(ViewGroup viewGroup);

    void onLoading(Holder holder);

    void onHideLoading(Holder holder);

    void onNoMoreData(Holder holder);

}
