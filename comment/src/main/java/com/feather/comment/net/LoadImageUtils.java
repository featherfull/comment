package com.feather.comment.net;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.feather.comment.util.CommentUtil;

import androidx.appcompat.app.AppCompatActivity;

public class LoadImageUtils {
    //加载圆形图片
    @SuppressLint("NewApi")
    public static void loadCircleImage(Context context, String path, ImageView image) {
        if (CommentUtil.isEmpty(path) || context == null) {
            return;
        }
        if (context instanceof AppCompatActivity && ((AppCompatActivity) context).isDestroyed()) {
            return;
        }
        Glide.with(context).load(path).apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(image);
    }

    //加载圆形图片
    public static void loadCircleImage(Context context, int path, ImageView image) {
        Glide.with(context).load(path).apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(image);
    }

    //加载椭圆形图片
    public static void loadRoundImage(Context context, int path, ImageView image) {
        Glide.with(context).load(path).apply(RequestOptions.bitmapTransform(new RoundedCorners
                (40))).into(image);
    }

    //加载椭圆形图片
    public static void loadRoundImage(Context context, String path, ImageView image) {
        Glide.with(context).load(path).apply(RequestOptions.bitmapTransform(new RoundedCorners
                (40))).into(image);
    }

    //加载图片
    public static void loadImage(Context context, String path, ImageView image) {
        Glide.with(context).load(path).into(image);
    }

    public static void loadNewImage(Context context, String path, ImageView image) {
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true);
        Glide.with(context).load(path).apply(options) // 不使用磁盘缓存
                .into(image);
    }
}
