package com.page.demo.model;

import android.arch.lifecycle.MutableLiveData;

import com.google.gson.reflect.TypeToken;
import com.page.demo.model.bean.Province;
import com.page.demo.repo.LocalRepo;
import com.wish.net.bean.NetBean;
import com.wish.net.request.NetConstant;
import com.wish.net.request.Request;
import com.wish.net.request.RxUtil;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * author：LinZhiXin
 * date：2019/5/27
 * description：
 **/
public class MainViewModel extends BaseViewModel {

    private MutableLiveData<List<Province>> provinceList;

    public MutableLiveData<List<Province>> getProvinceList() {
        if (provinceList == null) {
            provinceList = new MutableLiveData<>();
        }
        return provinceList;
    }

    public void loadProvince() {
        onPageStateLoading();
        Disposable disposable = Request.from(getApi().fetchProvince())
                .strategy(NetConstant.CACHE_ELSE_NET)
                .localRepo(new LocalRepo<>("province", 30))
                .type(new TypeToken<NetBean<List<Province>>>() {}.getType())
                .tag("provinceList")
                .toObservable()
                .compose(RxUtil.applyDefaultSchedulers())
                .subscribe(listNetBean -> {
                    getProvinceList().setValue(listNetBean.getData());
                    onPageStateOk();
                }, throwable -> {
                    onPageStateError(throwable);
                });
        getDisposableHelper().add("province", disposable);
    }

}
