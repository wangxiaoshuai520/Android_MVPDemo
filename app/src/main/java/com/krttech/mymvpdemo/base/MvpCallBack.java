package com.krttech.mymvpdemo.base;

/**
 * author:Devin
 * time:2018/10/25 10:20
 * desc:
 */
public interface MvpCallBack {
    /**
     * 数据请求成功
     * @param data 传输请求的数据
     */
    void onSuccess(String data);

    /**
     * 网络api接口请求,虽然成功,但返回message错误信息
     * @param msg 返回的错误信息
     */
    void onFailure(String msg);

    /**
     * 当网络请求时因网络问题或其他问题请求失败
     */
    void onError();

    /**
     * 当前数据结束时,无论是否成功,失败或异常,用此方法通常隐藏网络请求时的加载的空间
     */
    void onComplete();
}
