package com.feather.comment.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.feather.comment.R;

import org.greenrobot.eventbus.EventBus;

public class LoadingFragment {

    private View loadingView;//正在加载页面
    private View errorView;//错误页面
    private View successView;//成功页面
    private View emptyView;//空页面
    private TextView refreshTv;//刷新
    private TextView tryTv;//重试
    private boolean isLoadingAdd = false;
    private boolean isErrorAdd = false;
    private boolean isSuccessAdd = false;
    private boolean isEmptyAdd = false;
    private RelativeLayout rlAddedView;
    private RelativeLayout.LayoutParams mLayoutParams;
    private Context context;

    public LoadingFragment(Context context) {
        this.context = context;
        initView();
    }

    /**
     * 初始化页面
     */
    private void initView() {
        mLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        mLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        rlAddedView = new RelativeLayout(context);
        rlAddedView.setLayoutParams(mLayoutParams);

        //加载loading页面
        loadingView = View.inflate(context, R.layout.comment_loading, null);
//        ImageView imgLoading=loadingView.findViewById(R.id.img_loading);
//        ((AnimationDrawable)imgLoading.getBackground()).start();
        if (!isLoadingAdd) {
            rlAddedView.addView(loadingView, mLayoutParams);
            isLoadingAdd = true;
        }
        //添加失败的界面
        errorView = View.inflate(context, R.layout.comment_error, null);
        tryTv=errorView.findViewById(R.id.try_tv);
        tryTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post("errorRefresh");
            }
        });
        if (!isErrorAdd) {
            rlAddedView.addView(errorView, mLayoutParams);
            isErrorAdd = true;
        }
        //添加空白的页面
        emptyView = View.inflate(context, R.layout.comment_empty, null);
        refreshTv = emptyView.findViewById(R.id.refresh_tv);
        refreshTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post("emptyRefresh");
            }
        });
        if (!isEmptyAdd) {
            rlAddedView.addView(emptyView, mLayoutParams);
            isEmptyAdd = true;
        }
        //隐藏所有的界面
        hideAll();
    }

    /**
     * 添加一个成功的界面
     */
    public void bindSuccessView(View view) {
        successView = view;
        if (successView != null) {
//            rlAddedView = new RelativeLayout(context);
//            rlAddedView.setLayoutParams(mLayoutParams);
//            ViewGroup parent = (ViewGroup) successView.getParent();
//            parent.addView(rlAddedView);
            if (!isSuccessAdd) {
                ViewGroup parent = (ViewGroup) successView.getParent();
                parent.addView(rlAddedView, mLayoutParams);
                isSuccessAdd = true;
            }
//            successView.setVisibility(View.INVISIBLE);
//            rlAddedView.addView(successView);
        }
    }

    /**
     * 显示成功界面
     */
    public void showSuccessView() {
        hideAll();
        if (successView != null) {
            loadingView.setVisibility(View.GONE);
            emptyView.setVisibility(View.GONE);
            errorView.setVisibility(View.GONE);
            successView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 显示正在加载页面
     */
    public void showLoadingView() {
        hideAll();
        if (successView != null) {
            successView.setVisibility(View.GONE);
        }
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
    }

    /**
     * 显示正在加载失败页面
     */
    public void showErrorView() {
        hideAll();
        loadingView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        if (successView != null) {
            successView.setVisibility(View.GONE);
        }
        errorView.setVisibility(View.VISIBLE);
    }

    /**
     * 显示空页面
     */
    public void showEmptyView() {
        hideAll();
        loadingView.setVisibility(View.GONE);
        if (successView != null) {
            successView.setVisibility(View.GONE);
        }
        errorView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏所有的界面
     */
    public void hideAll() {
        loadingView.setVisibility(View.INVISIBLE);
        errorView.setVisibility(View.INVISIBLE);
        emptyView.setVisibility(View.INVISIBLE);
        if (successView != null) {
            successView.setVisibility(View.INVISIBLE);
        }
    }

}
