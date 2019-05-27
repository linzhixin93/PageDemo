package com.wish.ui.recycler;


import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created At 2019/5/25 by ZhiXin.Lin
 * Description :
 */
public abstract class Item  {

    private int position;

    private IDataAdapter adapter;

    final void attachAdapter(IDataAdapter adapter) {
        this.adapter = adapter;
    }

    final int getViewType() {
        return getClass().getSimpleName().hashCode();
    }

    final Holder createHolder(ViewGroup viewGroup) {
        return new Holder(
                LayoutInflater.from(viewGroup.getContext()).inflate(getLayoutId(),
                        viewGroup, false));
    }

    public int getPosition() {
        return position;
    }

    final void setPosition(int position) {
        this.position = position;
    }

    @LayoutRes
    public abstract int getLayoutId();

    public abstract void bindHolder(Holder holder);

    public void refreshSelf() {
        if (adapter == null) {
            return;
        }
        adapter.refreshItem(position);
    }

    public void deleteSelf() {
        if (adapter == null) {
            return;
        }
        adapter.deleteItem(position);
    }
}
