package com.feather.comment.base;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.GlideModule;
import com.feather.comment.util.CommentUtil;

import androidx.annotation.NonNull;

public class ImageCache implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
//        builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565);
        final String cachePath = CommentUtil.downLoadPath() + "cache";
        final int cacheSize = 100 * 1000 * 1000;
        // 设置缓存
        builder.setDiskCache(new DiskLruCacheFactory(cachePath, cacheSize));
    }


    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide,
                                   @NonNull Registry registry) {

    }
}
