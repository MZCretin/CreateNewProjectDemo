package com.cretin.www.createnewprojectdemo.ui;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.cretin.www.createnewprojectdemo.R;
import com.cretin.www.createnewprojectdemo.base.BackFragmentActivity;
import com.cretin.www.createnewprojectdemo.base.BaseActivity;
import com.cretin.www.createnewprojectdemo.dagger.DaggerUtils;
import com.cretin.www.createnewprojectdemo.fragment.InfoFragment;
import com.cretin.www.createnewprojectdemo.manager.MainActivityManager;
import com.cretin.www.createnewprojectdemo.model.InfoModel;
import com.cretin.www.createnewprojectdemo.model.ResultModel;
import com.cretin.www.createnewprojectdemo.retrofitapi.InfoService;
import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends BaseActivity {
    @Bind(R.id.tv_show)
    TextView tvShow;
    @Bind(R.id.tv_open)
    TextView tvOpen;

    @Override
    protected void initView(View v) {
        hidProgressView();
    }

    @Override
    protected void initData() {
        setMainTitle("首页");
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }


    @OnClick({R.id.tv_show, R.id.tv_open})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_show:
                showProgressView();
                DaggerUtils.getRetrofitServices(InfoService.class).getInfo().enqueue(new ResultCall<ResultModel<InfoModel>>() {
                    @Override
                    protected void onResponse(Response<ResultModel<InfoModel>> response) {
                        tvShow.setText(response.body().getData().getNew_list().get(0).getImg());
                    }

                    @Override
                    protected void onError(Call<ResultModel<InfoModel>> call, Throwable t) {

                    }
                });
                break;
            case R.id.tv_open:
                Intent intent = new Intent(mActivity, MainActivityManager.class);
                intent.putExtra(BackFragmentActivity.TAG_FRAGMENT, InfoFragment.TAG);
                mActivity.startActivity(intent);
                break;
        }
    }

}
