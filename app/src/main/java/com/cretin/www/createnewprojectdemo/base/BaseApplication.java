package com.cretin.www.createnewprojectdemo.base;

import android.app.Application;
import android.os.Handler;

import com.facebook.stetho.Stetho;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.hawk.LogLevel;

/**
 * Created by cretin on 16/10/27.
 */

public class BaseApplication extends Application{
    private static BaseApplication application;
    private static int mainTid;
    private static Handler handler;

    @Override
//  在主线程运行的
    public void onCreate() {
        super.onCreate();
        application = this;
        mainTid = android.os.Process.myTid();
        handler = new Handler();

        Stetho.initializeWithDefaults(this);

        //初始化Hawk
        initHawk();
    }

    //手动配置Hawk
    private void initHawk() {
        Hawk.init(this)
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.NO_ENCRYPTION)
                .setStorage(HawkBuilder.newSqliteStorage(this))
                .setLogLevel(LogLevel.FULL)
                .build();
    }

    public static BaseApplication getApp() {
        return application;
    }

    public static int getMainTid() {
        return mainTid;
    }

    public static Handler getHandler() {
        return handler;
    }
}
