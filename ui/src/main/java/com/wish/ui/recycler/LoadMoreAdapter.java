package com.wish.ui.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created At 2019/5/25 by ZhiXin.Lin
 * Description : 加载更多的Adapter
 */
public class LoadMoreAdapter<T extends Item> extends RecyclerView.Adapter<Holder> implements IDataAdapter<T>{

    private static final int TYPE_FOOT = -111;

    private static final int STATE_HIDE_LOADING = 0;

    private static final int STATE_LOADING = 1;

    private static final int STATE_NO_MORE = 2;

    private Adapter<T> innerAdapter;

    private ILoadMoreFooter footer;

    private int state;

    private OnLoadMoreListener onLoadMoreListener;

    public void setFooter(ILoadMoreFooter footer) {
        this.footer = footer;
    }

    public LoadMoreAdapter() {
        innerAdapter = new Adapter<>();
    }

    public void setUpWithRecyclerView(RecyclerView recyclerView, OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
        recyclerView.setAdapter(this);
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                if (state == STATE_LOADING) {
                    return;
                }
                if (LoadMoreAdapter.this.onLoadMoreListener == null) {
                    return;
                }
                state = STATE_LOADING;
                refreshItem(getItemCount() - 1);
                LoadMoreAdapter.this.onLoadMoreListener.onLoadMore();
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_FOOT;
        }
        return innerAdapter.getItemViewType(position);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        if (type == TYPE_FOOT) {
            return footer.createHolder(viewGroup);
        }
        return innerAdapter.onCreateViewHolder(viewGroup, type);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        if (position == getItemCount() - 1) {
            switch (state) {
                case STATE_HIDE_LOADING:
                    footer.onHideLoading(holder);
                    return;
                case STATE_LOADING:
                    footer.onLoading(holder);
                    return;
                case STATE_NO_MORE:
                    footer.onNoMoreData(holder);
                    return;
            }
            return;
        }
        innerAdapter.bindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return innerAdapter == null ? 0 : innerAdapter.getItemCount() + 1;
    }

    @Override
    public void changeAllItem(List<T> dataList) {
        innerAdapter.changeAllItem(dataList);
    }

    @Override
    public void appendItem(List<T> dataList) {
        innerAdapter.appendItem(dataList);
    }

    @Override
    public void deleteItem(int position) {
        innerAdapter.deleteItem(position);
    }

    @Override
    public void refreshAllItem() {
        notifyDataSetChanged();
    }

    @Override
    public void refreshItem(int position) {
        notifyItemChanged(position);
    }

    @Override
    public void refreshItems(int start, int size) {
        notifyItemRangeChanged(start, size);
    }

    @Override
    public void refreshItemDelete(int position) {
        notifyItemRemoved(position);
    }

    @Override
    public <R extends Item> R findItem(Class<R> tClass) {
        return innerAdapter.findItem(tClass);
    }

    @Override
    public <R extends Item> List<R> findItems(Class<R> tClass) {
        return innerAdapter.findItems(tClass);
    }

    /**
     * RecyclerView上滑监听
     */
    private abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

        // 用来标记是否正在向上滑动
        private boolean isSlidingUpward = false;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
            // 当不滑动时
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                // 获取最后一个完全显示的itemPosition
                int lastItemPosition = manager.findLastCompletelyVisibleItemPosition();
                int itemCount = manager.getItemCount();

                // 判断是否滑动到了最后一个item，并且是向上滑动
                if (lastItemPosition == (itemCount - 1) && isSlidingUpward) {
                    // 加载更多
                    onLoadMore();
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            // 大于0表示正在向上滑动，小于等于0表示停止或向下滑动
            isSlidingUpward = dy > 0;
        }

        /**
         * 加载更多回调
         */
        public abstract void onLoadMore();
    }

    /**
     * 加载更多的监听
     */
    public interface OnLoadMoreListener {
        void onLoadMore();
    }
}
