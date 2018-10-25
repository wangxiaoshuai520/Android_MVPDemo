package com.krttech.mymvpdemo.p;

import com.krttech.mymvpdemo.base.MVPView;
import com.krttech.mymvpdemo.base.MvpCallBack;
import com.krttech.mymvpdemo.modle.MvpModle;

/**
 * author:Devin
 * time:2018/10/25 10:37
 * desc:
 */
public class MvpPresenter {
    private MVPView mvpView;
    public MvpPresenter(MVPView mvpView){
        this.mvpView = mvpView;
    }
    /**
     * 获取网络操作
     */
    public void getData(String params){
        mvpView.showLoading();
        MvpModle.getNetData(params, new MvpCallBack() {
            @Override
            public void onSuccess(String data) {
                mvpView.showData(data);
            }

            @Override
            public void onFailure(String msg) {
                mvpView.showData(msg);
            }

            @Override
            public void onError() {
                mvpView.showErrorMessage();
            }

            @Override
            public void onComplete() {
                mvpView.hideLoading();
            }
        });
    }
}
