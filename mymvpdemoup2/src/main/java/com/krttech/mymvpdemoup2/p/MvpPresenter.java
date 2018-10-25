package com.krttech.mymvpdemoup2.p;


import com.krttech.mymvpdemoup2.base.BasePresenter;
import com.krttech.mymvpdemoup2.base.MVPView;
import com.krttech.mymvpdemoup2.base.MvpCallBack;
import com.krttech.mymvpdemoup2.modle.MvpModle;

/**
 * author:Devin
 * time:2018/10/25 10:37
 * desc:
 */
public class MvpPresenter extends BasePresenter<MVPView>{



    /**
     * 获取网络操作
     */
    public void getData(String params) {
        mvpView.showLoading();
        MvpModle.getNetData(params, new MvpCallBack() {
            @Override
            public void onSuccess(String data) {
                if (isViewAttached())
                    mvpView.showData(data);
            }

            @Override
            public void onFailure(String msg) {
                if (isViewAttached())
                    mvpView.showData(msg);
            }

            @Override
            public void onError() {
                if (isViewAttached())
                    mvpView.showErr();
            }

            @Override
            public void onComplete() {
                if (isViewAttached())
                    mvpView.hideLoading();
            }
        });
    }


}
