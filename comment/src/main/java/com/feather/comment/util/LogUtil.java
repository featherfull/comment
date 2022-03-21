package com.feather.comment.util;

import android.util.Log;



public class LogUtil {
    public static void showURL(String string){
            Log.e("com.feather.comment",string);
    }
    public static void showURL(int string){
            Log.e("com.feather.comment",string+"");
    }
}
