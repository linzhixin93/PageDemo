package com.page.demo.repo;

import com.page.demo.model.bean.Province;
import com.wish.net.ApiInfo;
import com.wish.net.bean.NetBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * author：LinZhiXin
 * date：2019/5/27
 * description：
 **/
@ApiInfo(debugUrl = "http://119.23.207.217/api/", releaseUrl = "http://119.23.207.217/api/")
public interface Api {

    @GET("province.json")
    Observable<NetBean<List<Province>>> fetchProvince();

}
