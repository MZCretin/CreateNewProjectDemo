package com.cretin.www.createnewprojectdemo.ui;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.cretin.www.createnewprojectdemo.R;
import com.cretin.www.createnewprojectdemo.app.LocalStorageKeys;
import com.cretin.www.createnewprojectdemo.base.ParentActivity;
import com.orhanobut.hawk.Hawk;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by sks on 2016/4/7.
 */
public class GuideActivity extends ParentActivity implements View.OnClickListener {
    @Bind( R.id.btn_go )
    Button btnGo;
    @Override
    protected void initView(View view) {
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        initData();
    }

    public void initData() {
        Hawk.put(LocalStorageKeys.IS_FIRST_START_IN, false);
    }

    @OnClick( R.id.btn_go )
    public void onClick(View view) {
        switch ( view.getId() ) {
            case R.id.btn_go:
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}