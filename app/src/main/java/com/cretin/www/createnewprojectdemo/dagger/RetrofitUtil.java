package com.cretin.www.createnewprojectdemo.dagger;

import com.cretin.www.createnewprojectdemo.dagger.component.DaggerKidRetrofit;

import retrofit2.Retrofit;

/**
 * Created by cretin on 16/10/27.
 */

public class RetrofitUtil {
    private static Retrofit retrofit;

    private RetrofitUtil() {

    }

    public static synchronized Retrofit getInstances() {
        if (retrofit == null) {
            retrofit = DaggerKidRetrofit.create().getRetrofitMaker().getRetrofit();
        }
        return retrofit;
    }

    public static <T> T getRetrofitServices(Class<T> services){
        return RetrofitUtil.getInstances().create(services);
    }
}
