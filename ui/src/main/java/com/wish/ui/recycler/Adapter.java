package com.wish.ui.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created At 2019/5/25 by ZhiXin.Lin
 * Description :
 */
public class Adapter<T extends Item> extends RecyclerView.Adapter<Holder> implements IDataAdapter<T> {

    private List<T> itemList;

    private IDataAdapter<T> proxyAdapter;

    public Adapter(List<T> itemList) {
        this.itemList = itemList;
    }

    public Adapter() {
    }

    void setProxyAdapter(IDataAdapter<T> adapter) {
        proxyAdapter = adapter;
    }

    @Override
    public int getItemViewType(int position) {
        itemList.get(position).attachAdapter(this);
        return itemList.get(position).getViewType();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        T item = null;
        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i).getViewType() == type) {
                item = itemList.get(i);
                break;
            }
        }
        if (item == null) {
            throw new RuntimeException("no holder find view type == " + type);
        }
        return item.createHolder(viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        itemList.get(position).setPosition(position);
        itemList.get(position).bindHolder(holder);
    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    @Override
    public void changeAllItem(List<T> dataList) {
        if (this.itemList != null) {
            this.itemList.clear();
        }
        this.itemList = dataList;
        refreshAllItem();
    }


    @Override
    public void appendItem(List<T> dataList) {
        if (this.itemList == null) {
            this.itemList = new ArrayList<>();
        }
        int start = this.itemList.size();
        this.itemList.addAll(dataList);
        refreshItems(start, dataList.size());
    }

    @Override
    public void deleteItem(int position) {
        if (proxyAdapter != null) {
            proxyAdapter.deleteItem(position);
            return;
        }
        itemList.remove(position);
        refreshItemDelete(position);
    }

    @Override
    public void refreshAllItem() {
        if (proxyAdapter != null) {
            proxyAdapter.refreshAllItem();
            return;
        }
        notifyDataSetChanged();
    }

    @Override
    public void refreshItem(int position) {
        if (proxyAdapter != null) {
            proxyAdapter.refreshItem(position);
            return;
        }
        notifyItemChanged(position);
    }

    @Override
    public void refreshItems(int start, int size) {
        if (proxyAdapter != null) {
            proxyAdapter.refreshItems(start, size);
            return;
        }
        notifyItemRangeChanged(start, size);
    }

    @Override
    public void refreshItemDelete(int position) {
        if (proxyAdapter != null) {
            proxyAdapter.refreshItemDelete(position);
            return;
        }
        notifyItemRemoved(position);
    }

    @Override
    public <R extends Item> R findItem(Class<R> tClass) {
        if (itemList == null) {
            return null;
        }
        for (T t : itemList) {
            if (t.getClass() == tClass) {
                return (R) t;
            }
        }
        return null;
    }

    @Override
    public <R extends Item> List<R> findItems(Class<R> tClass) {
        if (itemList == null) {
            return null;
        }
        List<R> rList = null;
        for (T t : itemList) {
            if (t.getClass() == tClass) {
                if (rList == null) {
                    rList = new ArrayList<>();
                }
                rList.add((R) t);
            }
        }
        return rList;
    }
}
