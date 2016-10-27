package com.cretin.www.createnewprojectdemo.dagger.module;

import android.text.TextUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by calvin on 1/14/16.
 */
@Module
public class OkClientModule {

    @Provides
    @Singleton
    OkHttpClient provideOkClient() {
        // 定义interceptor
        OkHttpClient okClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        String token = "";

                        Request original = chain.request();
                        // TODO: 16/3/3 动态读取版本号和平台信息
                        Request.Builder builder = original.newBuilder();
                        if (!TextUtils.isEmpty(token)) {
                            builder = builder.header("Authorization", "Bearer " + token);
                        }

                        Request request = builder
                                .header("User-Agent", "rollr-app/2.0.0 (Android; Android 9.2.1; Scale/2.00;)")
                                .method(original.method(), original.body())
                                .build();


                        Response response = chain.proceed(request);
                        //如果是401 没有登录 去登录界面 删除本地token 推送服务取消
                        if (response.code() == 401) {
                            return response;
                        }

                        //如果头部含有rollr-msg不为空 而且不为0 发送广播
                        String msg = response.header("rollr-msg", "0");
                        if (!TextUtils.isEmpty(msg) && !msg.equals("0")) {
                        }

                        String responseLoginToken = response.header("Authorization-Token", "");
                        if (!TextUtils.isEmpty(responseLoginToken)) {
                        }

                        //如果广告有更新 更新广告信息 rollr-ad-etag
                        String advertiseMentEtag = response.header("rollr-ad-etag", "");
                        if (!TextUtils.isEmpty(advertiseMentEtag)) {
                        }
                        return response;
                    }
                })
                .build();
        return okClient;
    }
}