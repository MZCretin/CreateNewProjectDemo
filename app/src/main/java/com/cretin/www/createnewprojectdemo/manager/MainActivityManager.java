package com.cretin.www.createnewprojectdemo.manager;

import android.os.Bundle;
import android.view.View;

import com.cretin.www.createnewprojectdemo.R;
import com.cretin.www.createnewprojectdemo.base.BackFragmentActivity;
import com.cretin.www.createnewprojectdemo.base.BaseFragment;
import com.cretin.www.createnewprojectdemo.fragment.InfoFragment;


public class MainActivityManager extends BackFragmentActivity<Bundle> {

    @Override
    protected int getFragmentContentId() {
        return R.id.fragment_container;
    }

    @Override
    protected BaseFragment getFirstFragment() {
        BaseFragment fragment = null;
        if (InfoFragment.TAG.equals(tag_fragment)) {
            fragment = new InfoFragment();
        }
        return fragment;
    }

    @Override
    protected void initView(View view) {

    }
}
