package com.krttech.mymvpdemo.base;

/**
 * author:Devin
 * time:2018/10/25 10:16
 * desc:
 */
public interface MVPView {
    /**
     * 显示进度条
     */
    void  showLoading();
    /**
     * 隐藏进度条
     */
    void hideLoading();
    /**
     * 当数据请求成功后,调用次接口
     */
    void showData(String data);

    /**
     * 当数据请求失败后调用次接口提示
     * @param meg 失败原因
     */
    void showFailureMessage(String meg);

    /**
     * 当数据异常是调用
     */
    void showErrorMessage();
}
