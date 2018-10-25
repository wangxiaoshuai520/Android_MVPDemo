package com.krttech.mymvpdemoup2.base;

import android.content.Context;

/**
 * author:Devin
 * time:2018/10/25 11:30
 * desc:
 */
public interface BaseView {
    void showLoading();
    void hideLoading();
    void showToast(String msg);
    void showErr();
    Context getContext();
}
