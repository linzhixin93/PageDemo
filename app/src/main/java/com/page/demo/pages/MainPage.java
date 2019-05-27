package com.page.demo.pages;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.page.demo.R;
import com.page.demo.event.EventCode;
import com.page.demo.item.ProvinceItem;
import com.page.demo.model.MainViewModel;
import com.page.demo.model.bean.Province;
import com.wish.common.msg.MessageEvent;
import com.wish.ui.recycler.Adapter;
import com.wish.ui.recycler.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * author：LinZhiXin
 * date：2019/5/27
 * description：
 **/
public class MainPage extends BasePage {

    private RecyclerView recyclerView;

    private Adapter<Item> adapter;

    @Override
    public int getLayoutId() {
        return R.layout.page_main;
    }

    @Override
    public void onCreate(View view) {
        super.onCreate(view);
        recyclerView = view.findViewById(R.id.rcv_main_page);
        recyclerView.setLayoutManager(new LinearLayoutManager(getComponent().getContext()));
        adapter = new Adapter<>();
        recyclerView.setAdapter(adapter);
        getViewModel(MainViewModel.class).getPageState().observe(getComponent().getLifeOwner(), getPageStateObserver());
        getViewModel(MainViewModel.class).getProvinceList()
                .observe(getComponent().getLifeOwner(), provinces -> {
                    if (provinces == null) {
                        return;
                    }
                    List<Item> itemList = new ArrayList<>();
                    for (Province province : provinces) {
                        itemList.add(new ProvinceItem(province));
                    }
                    adapter.changeAllItem(itemList);
                });
    }

    @Override
    public void onVisible() {
        super.onVisible();
        getViewModel(MainViewModel.class)
                .loadProvince();
    }

    @Override
    public void onGlobalMsg(MessageEvent event) {
        super.onGlobalMsg(event);
        if (EventCode.PROVINCE_CLICK == event.getCode()) {
            Province province = (Province) event.getData();
            ToastUtils.showShort(province.getName());
        }
    }
}
