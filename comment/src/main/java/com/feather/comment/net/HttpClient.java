package com.feather.comment.net;

import android.os.Handler;
import android.text.TextUtils;

import com.feather.comment.util.LogUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class HttpClient {
    public OkHttpClient client;
    private static HttpClient mClient;

    private Handler mHandle = new Handler();

    public HttpClient() {
        client = new OkHttpClient.Builder()
                .followRedirects(true)
                .followSslRedirects(true)
                .sslSocketFactory(TLSSocketFactory.getSSLSocketFactory())
                .hostnameVerifier(TLSSocketFactory.getHostnameVerifier())
                .addInterceptor(new LoggerInterceptor())
                .build();
    }

    public static HttpClient getInstance() {
        if (mClient == null) {
            mClient = new HttpClient();
        }
        return mClient;
    }


    // GET方法
    public void get(String url, Map<String, String> param, final ResultCallback callback) {
        // 拼接请求参数
        if (!param.isEmpty()) {
            StringBuffer buffer = new StringBuffer(url);
            buffer.append('?');
            for (Map.Entry<String, String> entry : param.entrySet()) {
                buffer.append(entry.getKey());
                buffer.append('=');
                buffer.append(entry.getValue());
                buffer.append('&');
            }
            buffer.deleteCharAt(buffer.length() - 1);
            url = buffer.toString();
        }
        Request.Builder builder = new Request.Builder().url(url);
        builder.method("GET", null);
        Request request = builder.build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onErrorMessage(callback, 500, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                if (!TextUtils.isEmpty(res)) {
                    parse(callback, res);
                }
            }
        });
    }

    public void get(String url, final ResultCallback callback) {
        get(url, new HashMap<String, String>(), callback);
    }

    public void post(String url, final ResultCallback callback) {
        post(url, new HashMap<String, String>(), callback);
    }

    // POST 方法
    public void post(String url, Map<String, String> param, final ResultCallback callback) {
        FormBody.Builder formBody = new FormBody.Builder();
        if (!param.isEmpty()) {
            for (Map.Entry<String, String> entry : param.entrySet()) {
                formBody.add(entry.getKey(), entry.getValue());
            }
        }
        RequestBody form = formBody.build();
        Request.Builder builder = new Request.Builder();
        final Request request = builder.post(form)
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onErrorMessage(callback, 500, "网络连接失败，请检查网络");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                LogUtil.showURL(res);
                if (!TextUtils.isEmpty(res)) {
                    parse(callback, res);
                }
            }
        });
    }


    /**
     * 服务器没有响应
     *
     * @param callback
     * @param code
     * @param message
     */
    private void onErrorMessage(final ResultCallback callback, final int code, final String
            message) {
        mHandle.post(new Runnable() {
            @Override
            public void run() {
                LogUtil.showURL(code + "," + message);
                callback.onError(code, message);
                callback.onFinish();
            }
        });
    }


    /**
     * 解析数据
     * 转成主线程回调
     *
     * @param callback
     */
    private void parse(final ResultCallback callback, final String netResponse) {
        mHandle.post(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(netResponse);
                callback.onFinish();
            }
        });
    }


}