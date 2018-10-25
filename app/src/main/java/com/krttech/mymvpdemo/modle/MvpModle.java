package com.krttech.mymvpdemo.modle;

import android.os.Handler;

import com.krttech.mymvpdemo.base.MvpCallBack;


/**
 * author:Devin
 * time:2018/10/25 10:25
 * desc:
 */
public class MvpModle {
    public static void getNetData(final String para, final MvpCallBack callBack) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (para) {
                    case "normal":
                        callBack.onSuccess("根据参数:" + para + "请求数据陈宫");
                        break;

                    case "failure":
                        callBack.onFailure("请求失败:参数有误");
                        break;
                    case "error":
                        callBack.onError();
                        break;
                }
                callBack.onComplete();
            }

        }, 2000);
    }
}
