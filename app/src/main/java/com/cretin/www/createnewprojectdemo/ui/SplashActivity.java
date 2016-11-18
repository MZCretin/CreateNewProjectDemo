package com.cretin.www.createnewprojectdemo.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.cretin.www.createnewprojectdemo.R;
import com.cretin.www.createnewprojectdemo.app.LocalStorageKeys;
import com.cretin.www.createnewprojectdemo.base.BaseApplication;
import com.cretin.www.createnewprojectdemo.base.ParentActivity;
import com.cretin.www.createnewprojectdemo.utils.KV;


/**
 * Created by sks on 2016/4/7.
 */
public class SplashActivity extends ParentActivity {
    @Override
    protected void initView(View view) {
        setContentView(R.layout.activity_splash);
        String versionName = getVersionName(this);
        if (!TextUtils.isEmpty(versionName)) {
            ((TextView) findViewById(R.id.tv_version)).setText("V" + versionName + " 版本");
        }
        //第一次启动
        if ( KV.get(LocalStorageKeys.IS_FIRST_START_IN, true) || KV.get(LocalStorageKeys.APP_VERSION_CODE, 1) < getVersionCode(this)) {
            KV.put(LocalStorageKeys.APP_VERSION_CODE, getVersionCode(this));
            BaseApplication.getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 2000);
        } else {
            BaseApplication.getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 1000);
        }
    }

    /**
     * 获得apk版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        PackageInfo packInfo = getPackInfo(context);
        if (packInfo != null) {
            versionCode = packInfo.versionCode;
        }
        return versionCode;
    }

    private String getVersionName(Context context) {
        String versionName = "";
        PackageInfo packInfo = getPackInfo(context);
        if (packInfo != null) {
            versionName = packInfo.versionName;
        }
        return versionName;
    }

    /**
     * 获得apkinfo
     *
     * @param context
     * @return
     */
    public static PackageInfo getPackInfo(Context context) {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(),
                    0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packInfo;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
