package com.cretin.www.createnewprojectdemo.dagger.component;


import com.cretin.www.createnewprojectdemo.dagger.RetrofitMaker;
import com.cretin.www.createnewprojectdemo.dagger.module.OkClientModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by calvin on 1/14/16.
 */
@Singleton
@Component(modules=OkClientModule.class)
public interface KidRetrofit {
    RetrofitMaker getRetrofitMaker();
}