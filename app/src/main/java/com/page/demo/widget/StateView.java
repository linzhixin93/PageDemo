package com.page.demo.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.page.demo.R;


/**
 * Created At 2019/5/26 by ZhiXin.Lin
 * Description : 状态View，显示Loading, 空页面，错误页面
 */
public class StateView extends FrameLayout {

    private View contentView;
    private View stateView;
    private View loadingView;
    private TextView stateTv;

    private OnReloadListener onReloadListener;

    public StateView(@NonNull Context context) {
        super(context);
        stateView = LayoutInflater.from(context).inflate(R.layout.layout_state, null);
        stateTv = stateView.findViewById(R.id.tv_state);
        stateView.setOnClickListener(v -> {
            if (onReloadListener != null) {
                onReloadListener.onReload();
            }
        });
        addView(stateView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        loadingView = LayoutInflater.from(context).inflate(R.layout.layout_loading, null);
        addView(loadingView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        stateView.setVisibility(GONE);
        loadingView.setVisibility(GONE);
    }

    public void setOnReloadListener(OnReloadListener onReloadListener) {
        this.onReloadListener = onReloadListener;
    }

    public void setContentView(View contentView) {
        this.contentView = contentView;
        addView(contentView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        loadingView.bringToFront();
    }


    public void showContentView() {
        stateView.setVisibility(GONE);
        loadingView.setVisibility(GONE);
        contentView.setVisibility(VISIBLE);
    }

    public void showStateView() {
        contentView.setVisibility(GONE);
        loadingView.setVisibility(GONE);
        stateView.setVisibility(VISIBLE);
    }

    public void showStateView(String tip) {
        contentView.setVisibility(GONE);
        loadingView.setVisibility(GONE);
        stateTv.setText(tip);
        stateView.setVisibility(VISIBLE);
    }

    public void showLoading(boolean show) {
        loadingView.setVisibility(show ? VISIBLE : GONE);
    }

    public interface OnReloadListener {
        void onReload();
    }

}
