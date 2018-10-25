package com.krttech.mymvpdemoup2.base;

/**
 * author:Devin
 * time:2018/10/25 11:28
 * desc:
 */
public interface Callback <T>{
    void onSuccess(T data);
    void onFailure(String msg);
    void onError();
    void onComplete();
}
