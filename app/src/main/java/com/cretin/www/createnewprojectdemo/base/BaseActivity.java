package com.cretin.www.createnewprojectdemo.base;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by cretin on 16/10/27.
 * 如果是需要处理自己的逻辑 则继承这个
 * 如果只是给加载Fragment提供一个容器 则继承ParentActivity
 */

public abstract class BaseActivity extends ParentActivity {
    private CustomProgressDialog dialog;
    private OnTitleAreaCliclkListener onTitleAreaCliclkListener;

    private TextView tvMainTitle;
    private ImageView ivMainBack;
    private ImageView ivMainRight;
    private TextView tvMainRight;

    private RelativeLayout relaLoadContainer;
    private TextView tvLoadingMsg;

    private ImageView ivBack;

    private CompositeSubscription mCompositeSubscription;

    protected void addSubscription(Subscription s) {
        if ( this.mCompositeSubscription == null ) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.layout_base_activity, null);
        setContentView(view);
        initHeadView(view);
        initContentView(view);
        if ( isKitkat ) {
            view.findViewById(R.id.ll_main_title).setPadding(0, ViewUtils.getStatusBarHeights(), 0, 0);
        }
        initData();
        initEvent();
    }

    private AnimationDrawable animationDrawable;

    private void initContentView(View view) {
        FrameLayout container = ( FrameLayout ) view.findViewById(R.id.main_container);
        relaLoadContainer = ( RelativeLayout ) view.findViewById(R.id.load_container);
        tvLoadingMsg = ( TextView ) view.findViewById(R.id.loading_msg);
        ImageView imageView = ( ImageView ) view
                .findViewById(R.id.loading_image);
        animationDrawable = ( AnimationDrawable ) imageView
                .getBackground();
        animationDrawable.start();
        View v = getLayoutInflater().inflate(getContentViewId(), null);
        ButterKnife.bind(this, v);
        container.addView(v,new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        initView(v);
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
        if ( relaLoadContainer != null )
            relaLoadContainer.setVisibility(View.GONE);
        if ( animationDrawable != null )
            animationDrawable.stop();
    }

    //显示正在加载视图
    public void showProgressView() {
        if ( relaLoadContainer != null && relaLoadContainer.getVisibility() == View.GONE )
            relaLoadContainer.setVisibility(View.VISIBLE);
        if ( animationDrawable != null )
            animationDrawable.start();
    }

    //隐藏返回按钮
    public void hidBackIv() {
        if ( ivBack != null && ivBack.getVisibility() == View.VISIBLE )
            ivBack.setVisibility(View.GONE);
    }


    //显示加载错误
    public void showErrorView() {
        if ( relaLoadContainer != null && relaLoadContainer.getVisibility() == View.GONE )
            relaLoadContainer.setVisibility(View.VISIBLE);
        if ( animationDrawable != null )
            animationDrawable.stop();
        tvLoadingMsg.setText("加载错误");
    }

    //初始化头部视图
    private void initHeadView(View view) {
        tvMainTitle = ( TextView ) view.findViewById(R.id.tv_title_info);
        ivMainBack = ( ImageView ) view.findViewById(R.id.iv_back);
        ivMainRight = ( ImageView ) view.findViewById(R.id.iv_right);
        tvMainRight = ( TextView ) view.findViewById(R.id.tv_right);
        ivBack = ( ImageView ) view.findViewById(R.id.iv_back);
        ivMainBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                if ( onTitleAreaCliclkListener != null )
                    onTitleAreaCliclkListener.onTitleAreaClickListener(v);
            }
        });
        ivMainRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( onTitleAreaCliclkListener != null )
                    onTitleAreaCliclkListener.onTitleAreaClickListener(v);
            }
        });
        tvMainRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( onTitleAreaCliclkListener != null )
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
        if ( dialog == null ) {
            dialog = CustomProgressDialog.createDialog(this);
            if ( msg != null && !msg.equals("") ) {
                dialog.setMessage(msg);
            }
        }
        dialog.show();
    }

    /**
     * 关闭对话框
     */
    public void stopDialog() {
        if ( dialog != null && dialog.isShowing() ) {
            dialog.dismiss();
        }
    }

    public void setOnTitleAreaCliclkListener(OnTitleAreaCliclkListener onTitleAreaCliclkListener) {
        this.onTitleAreaCliclkListener = onTitleAreaCliclkListener;
    }

    //设置Title
    protected void setMainTitle(String title) {
        if ( !TextUtils.isEmpty(title) )
            tvMainTitle.setText(title);
    }

    //设置TitleColor
    protected void setMainTitleColor(String titleColor) {
        if ( !TextUtils.isEmpty(titleColor) )
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
        if ( !TextUtils.isEmpty(tvRightColor) )
            setMainTitleRightColor(Color.parseColor(tvRightColor));
    }

    //设置右边TextView大小
    protected void setMainTitleRightSize(int size) {
        tvMainRight.setTextSize(size);
    }

    //设置右边TextView内容
    protected void setMainTitleRightContent(String content) {
        if ( !TextUtils.isEmpty(content) ) {
            if ( tvMainRight.getVisibility() == View.GONE )
                tvMainRight.setVisibility(View.VISIBLE);
            tvMainRight.setText(content);
        }
    }

    //设置左边ImageView资源
    protected void setMainLeftIvRes(int res) {
        if ( ivMainBack.getVisibility() == View.GONE )
            ivMainBack.setVisibility(View.VISIBLE);
        ivMainBack.setImageResource(res);
    }

    //设置又边ImageView资源
    protected void setMainRightIvRes(int res) {
        if ( ivMainRight.getVisibility() == View.GONE )
            ivMainRight.setVisibility(View.VISIBLE);
        ivMainRight.setImageResource(res);
    }

    interface OnTitleAreaCliclkListener {
        void onTitleAreaClickListener(View view);
    }

    protected abstract void initData();

    protected abstract int getContentViewId();

    protected void initEvent() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if ( this.mCompositeSubscription != null ) {
            this.mCompositeSubscription.unsubscribe();
        }

    }
}
