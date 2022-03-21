package com.feather.comment.net;

public class NetResponse<T> {
    public int status;
    public String msg;
    public T data;
    public int code;

    @Override
    public String toString() {
        return "NetResponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
