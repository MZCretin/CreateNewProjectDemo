package com.cretin.www.createnewprojectdemo.dagger;


import com.cretin.www.createnewprojectdemo.app.BuildConfig;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by calvin on 1/14/16.
 */
public class RetrofitMaker {
    private final OkHttpClient okClient;

    @Inject
    public RetrofitMaker(OkHttpClient okClient) {
        this.okClient = okClient;
    }

    @Singleton
    public Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okClient)
                .build();
    }

}