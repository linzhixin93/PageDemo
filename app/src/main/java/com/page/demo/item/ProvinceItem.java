package com.page.demo.item;

import com.page.demo.R;
import com.page.demo.model.bean.Province;
import com.page.demo.event.EventCode;
import com.wish.common.msg.MessageEvent;
import com.wish.ui.recycler.Holder;
import com.wish.ui.recycler.Item;

/**
 * author：LinZhiXin
 * date：2019/5/27
 * description：
 **/
public class ProvinceItem extends Item {

    private Province province;

    public ProvinceItem(Province province) {
        this.province = province;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_province;
    }

    @Override
    public void bindHolder(Holder holder) {
        holder.setText(R.id.tv_province_name, province.getName())
                .setOnClickListener(R.id.tv_province_name, v -> MessageEvent.newMsg()
                        .setCode(EventCode.PROVINCE_CLICK)
                        .setData(province)
                        .post());
    }
}
