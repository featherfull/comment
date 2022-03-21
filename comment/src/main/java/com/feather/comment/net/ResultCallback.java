package com.feather.comment.net;

import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class ResultCallback {
    /**
     * 请求失败的时候
     *
     * @param code
     * @param message
     */
    public  void onError(int code, String message){

    }

    /**
     *
     * @param response
     */
    public  void onResponse(String response){

    }

    /**
     * 请求完成
     */
    public void onFinish() {
    }

    /**
     * 通过反射想要的返回类型
     *
     * @param subclass
     * @return
     */
    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }
}
