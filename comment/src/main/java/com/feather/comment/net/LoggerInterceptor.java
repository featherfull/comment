package com.feather.comment.net;




import com.feather.comment.util.CommentUtil;
import com.feather.comment.util.LogUtil;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LoggerInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        // 拦截请求，获取到该次请求的request
        Request request = chain.request();
        // 执行本次网络请求操作，返回response信息
        Response response = chain.proceed(request);
        StringBuilder sb = new StringBuilder();
        if (request.body() instanceof FormBody) {
            FormBody body = (FormBody) request.body();
            for (int i = 0; i < body.size(); i++) {
                sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + "&");
            }
            if (!CommentUtil.isEmpty(sb.toString())) {
                sb.delete(sb.length() - 1, sb.length());
            }
            LogUtil.showURL(request.url().uri().toString()+"?" + sb.toString());
        }
        // 注意，这样写，等于重新创建Request，获取新的Response，避免在执行以上代码时，
        // 调用了responseBody.string()而不能在返回体中再次调用。
        return response.newBuilder().build();
    }
}
