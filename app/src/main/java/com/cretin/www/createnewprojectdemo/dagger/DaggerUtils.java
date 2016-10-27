package com.cretin.www.createnewprojectdemo.dagger;

import com.cretin.www.createnewprojectdemo.dagger.component.DaggerKidRetrofit;

/**
 * Created by cretin on 16/10/27.
 */

public class DaggerUtils {
    public static <T> T getRetrofitServices(Class<T> services){
        return DaggerKidRetrofit.create().getRetrofitMaker().getRetrofit().create(services);
    }
}
