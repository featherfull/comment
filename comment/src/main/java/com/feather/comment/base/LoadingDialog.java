package com.feather.comment.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feather.comment.R;


public class LoadingDialog {

    public static Dialog createDialog(Context context, String msg) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_dialog, null);
        // 获取整个布局
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        ImageView img = view.findViewById(R.id.img);
        TextView text = view.findViewById(R.id.text);
        LinearLayout dialogView = view.findViewById(R.id.dialog_view);
        img.setBackgroundResource(R.drawable.loading_anim);
        AnimationDrawable AniDraw = (AnimationDrawable) img.getBackground();
        AniDraw.start();
        // 显示文本
        text.setText(msg);
        // 创建自定义样式的Dialog
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);
        // 设置返回键无效
        loadingDialog.setCancelable(true);
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setContentView(dialogView, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        return loadingDialog;
    }
}
