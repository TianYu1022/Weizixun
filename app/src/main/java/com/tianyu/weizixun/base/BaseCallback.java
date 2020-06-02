package com.tianyu.weizixun.base;

public interface BaseCallback<T> {
    void onSuccess(T t);

    void onFail(String error);
}
