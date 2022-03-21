package com.feather.comment.base;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;


import com.feather.comment.util.CommentUtil;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class BaseActivity extends AppCompatActivity {
    private LoadingFragment loading;
    private Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this, 0x55000000);
        }
        if (CommentUtil.isPad(this)) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        init();
    }

    private void init() {
        loading = new LoadingFragment(this);
    }

    /**
     * 显示Dialog
     */
    public void showDialog() {
        if (dialog == null) {
            dialog = LoadingDialog.createDialog(this, "正在加载");
            dialog.show();
        } else {
            dialog.show();
        }
    }

    /**
     * 关闭Dialog
     */
    public void closeDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    /**
     * 正在加载
     */
    public void onLoading(View view) {
        if (view != null) {
            loading.bindSuccessView(view);
        }
        loading.showLoadingView();
    }

    /**
     * 加载结果
     */
    public void onComplete(int status) {
        switch (status) {
            case CommentUtil.SUCCESSSTATUS:
                loading.showSuccessView();
                break;
            case CommentUtil.ERRORSTATUS:
                loading.showErrorView();
                break;
            case CommentUtil.EMPTYSTATUS:
                loading.showEmptyView();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        CommentUtil.hideInput(this);
    }
}
