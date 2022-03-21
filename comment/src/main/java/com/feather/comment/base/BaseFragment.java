package com.feather.comment.base;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;


import com.feather.comment.util.CommentUtil;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {
    private LoadingFragment loading;
    private Dialog dialog;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
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
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        loading = new LoadingFragment(getActivity());
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
        handler.sendEmptyMessageDelayed(status, 100);
//        switch (status) {
//            case CommentUtil.SUCCESSSTATUS:
//                loading.showSuccessView();
//                break;
//            case CommentUtil.ERRORSTATUS:
//                loading.showErrorView();
//                break;
//            case CommentUtil.EMPTYSTATUS:
//                loading.showEmptyView();
//                break;
//        }
    }

    /**
     * 显示Dialog
     */
    protected void showDialog(String text) {
        if (dialog == null) {
            dialog = LoadingDialog.createDialog(getActivity(), text);
            dialog.show();
        } else {
            dialog.show();
        }
    }

    /**
     * 关闭Dialog
     */
    protected void closeDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

}
