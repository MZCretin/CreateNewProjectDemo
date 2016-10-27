package com.cretin.www.createnewprojectdemo.base;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cretin.www.createnewprojectdemo.R;
import com.cretin.www.createnewprojectdemo.utils.ViewUtils;
import com.cretin.www.createnewprojectdemo.view.CustomProgressDialog;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by cretin on 16/10/27.
 */

public abstract class BaseFragment extends Fragment {
    private TextView tvMainTitle;
    private ImageView ivMainBack;
    private ImageView ivMainRight;
    private TextView tvMainRight;

    private RelativeLayout relaLoadContainer;
    private TextView tvLoadingMsg;

    protected ParentActivity mActivity;

    private CustomProgressDialog dialog;
    private BaseActivity.OnTitleAreaCliclkListener onTitleAreaCliclkListener;

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_base_activity, null);
        if (((ParentActivity) getActivity()).isKitkat) {
            view.findViewById(R.id.ll_main_title).setPadding(0, ViewUtils.getStatusBarHeights(), 0, 0);
        }
        initHeadView(view);
        initContentView(view, savedInstanceState);
        initData();
        return view;
    }

    //获取宿主Activity
    protected ParentActivity getHoldingActivity() {
        return mActivity;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (ParentActivity) activity;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private AnimationDrawable animationDrawable;

    private void initContentView(View view, Bundle savedInstanceState) {
        RelativeLayout container = (RelativeLayout) view.findViewById(R.id.main_container);
        relaLoadContainer = (RelativeLayout) view.findViewById(R.id.load_container);
        tvLoadingMsg = (TextView) view.findViewById(R.id.loading_msg);
        ImageView imageView = (ImageView) view
                .findViewById(R.id.loading_image);
        animationDrawable = (AnimationDrawable) imageView
                .getBackground();
        animationDrawable.start();
        View v = getActivity().getLayoutInflater().inflate(getLayoutId(), null);
        ButterKnife.bind(this, v);
        container.addView(v);
        initView(v, savedInstanceState);
    }

    //onResponse子类去实现
    public abstract class ResultCall<T> implements Callback<T> {
        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            hidProgressView();
            onResponse(response);
        }

        protected abstract void onResponse(Response<T> response);

        @Override
        public void onFailure(Call<T> call, Throwable t) {
            showErrorView();
            onError(call, t);
        }

        protected abstract void onError(Call<T> call, Throwable t);
    }

    //隐藏正在加载视图
    public void hidProgressView() {
        if (relaLoadContainer != null)
            relaLoadContainer.setVisibility(View.GONE);
        if (animationDrawable != null)
            animationDrawable.stop();
    }

    //显示正在加载视图
    public void showProgressView() {
        if (relaLoadContainer != null && relaLoadContainer.getVisibility() == View.GONE)
            relaLoadContainer.setVisibility(View.VISIBLE);
        if (animationDrawable != null)
            animationDrawable.start();
    }

    //显示加载错误
    public void showErrorView() {
        if (relaLoadContainer != null && relaLoadContainer.getVisibility() == View.GONE)
            relaLoadContainer.setVisibility(View.VISIBLE);
        if (animationDrawable != null)
            animationDrawable.stop();
        tvLoadingMsg.setText("加载错误");
    }

    //初始化头部视图
    private void initHeadView(View view) {
        tvMainTitle = (TextView) view.findViewById(R.id.tv_title_info);
        ivMainBack = (ImageView) view.findViewById(R.id.iv_back);
        ivMainRight = (ImageView) view.findViewById(R.id.iv_right);
        tvMainRight = (TextView) view.findViewById(R.id.tv_right);

        ivMainBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof BackFragmentActivity) {
                    ((BackFragmentActivity) getActivity()).removeFragment();
                } else {
                    getActivity().finish();
                }
                if (onTitleAreaCliclkListener != null)
                    onTitleAreaCliclkListener.onTitleAreaClickListener(v);
            }
        });
        ivMainRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTitleAreaCliclkListener != null)
                    onTitleAreaCliclkListener.onTitleAreaClickListener(v);
            }
        });
        tvMainRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTitleAreaCliclkListener != null)
                    onTitleAreaCliclkListener.onTitleAreaClickListener(v);
            }
        });
    }

    /**
     * 显示加载对话框
     *
     * @param msg
     */
    public void showDialog(String msg) {
        if (dialog == null) {
            dialog = CustomProgressDialog.createDialog(getActivity());
            if (msg != null && !msg.equals("")) {
                dialog.setMessage(msg);
            }
        }
        dialog.show();
    }

    /**
     * 关闭对话框
     */
    public void stopDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void setOnTitleAreaCliclkListener(BaseActivity.OnTitleAreaCliclkListener onTitleAreaCliclkListener) {
        this.onTitleAreaCliclkListener = onTitleAreaCliclkListener;
    }

    //设置Title
    protected void setMainTitle(String title) {
        if (!TextUtils.isEmpty(title))
            tvMainTitle.setText(title);
    }

    //设置TitleColor
    protected void setMainTitleColor(String titleColor) {
        if (!TextUtils.isEmpty(titleColor))
            setMainTitleColor(Color.parseColor(titleColor));
    }

    //设置TitleColor
    protected void setMainTitleColor(int titleColor) {
        tvMainTitle.setTextColor(titleColor);
    }

    //设置右边TextView颜色
    protected void setMainTitleRightColor(int tvRightColor) {
        tvMainRight.setTextColor(tvRightColor);
    }

    //设置右边TextView颜色
    protected void setMainTitleRightColor(String tvRightColor) {
        if (!TextUtils.isEmpty(tvRightColor))
            setMainTitleRightColor(Color.parseColor(tvRightColor));
    }

    //设置右边TextView大小
    protected void setMainTitleRightSize(int size) {
        tvMainRight.setTextSize(size);
    }

    //设置右边TextView内容
    protected void setMainTitleRightContent(String content) {
        if (!TextUtils.isEmpty(content)) {
            if (tvMainRight.getVisibility() == View.GONE)
                tvMainRight.setVisibility(View.VISIBLE);
            tvMainRight.setText(content);
        }
    }

    //设置左边ImageView资源
    protected void setMainLeftIvRes(int res) {
        if (ivMainBack.getVisibility() == View.GONE)
            ivMainBack.setVisibility(View.VISIBLE);
        ivMainBack.setImageResource(res);
    }

    //设置又边ImageView资源
    protected void setMainRightIvRes(int res) {
        if (ivMainRight.getVisibility() == View.GONE)
            ivMainRight.setVisibility(View.VISIBLE);
        ivMainRight.setImageResource(res);
    }

    interface OnTitleAreaCliclkListener {
        void onTitleAreaClickListener(View view);
    }

    protected abstract void initData();

    protected abstract void initView(View contentView, Bundle savedInstanceState);

    protected abstract int getLayoutId();
}
