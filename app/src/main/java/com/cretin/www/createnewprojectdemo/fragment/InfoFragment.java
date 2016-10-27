package com.cretin.www.createnewprojectdemo.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.cretin.www.createnewprojectdemo.R;
import com.cretin.www.createnewprojectdemo.base.BaseFragment;
import com.cretin.www.createnewprojectdemo.dagger.RetrofitUtil;
import com.cretin.www.createnewprojectdemo.model.InfoModel;
import com.cretin.www.createnewprojectdemo.model.ResultModel;
import com.cretin.www.createnewprojectdemo.retrofitapi.InfoService;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends BaseFragment {
    public static final String TAG = "InfoFragment";
    @Bind(R.id.tv_show)
    TextView tvShow;

    @Override
    protected void initData() {
        setMainTitle("第二页Fragment");
    }

    @Override
    protected void initView(View contentView, Bundle savedInstanceState) {
        hidProgressView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_info;
    }

    @OnClick(R.id.tv_show)
    public void onClick() {
        showProgressView();
        RetrofitUtil.getRetrofitServices(InfoService.class).getInfo().enqueue(new ResultCall<ResultModel<InfoModel>>() {
            @Override
            protected void onResponse(Response<ResultModel<InfoModel>> response) {
                tvShow.setText(response.body().getData().getNew_list().get(0).getImg());
            }

            @Override
            protected void onError(Call<ResultModel<InfoModel>> call, Throwable t) {

            }
        });
    }
}
