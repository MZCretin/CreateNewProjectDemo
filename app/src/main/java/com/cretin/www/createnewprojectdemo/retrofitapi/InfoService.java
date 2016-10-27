package com.cretin.www.createnewprojectdemo.retrofitapi;

import com.cretin.www.createnewprojectdemo.model.InfoModel;
import com.cretin.www.createnewprojectdemo.model.ResultModel;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by cretin on 16/10/27.
 */

public interface InfoService {
    /**
     * 获取
     */
    @POST("/weixin/app/active")
    Call<ResultModel<InfoModel>> getInfo();
}
