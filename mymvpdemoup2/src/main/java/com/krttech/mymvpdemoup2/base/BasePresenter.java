package com.krttech.mymvpdemoup2.base;

/**
 * author:Devin
 * time:2018/10/25 11:32
 * desc:
 */
public class BasePresenter<V extends BaseView> {
    protected V mvpView;
    public void attachView(V mvpView) {
        this.mvpView = mvpView;
    }
    public void detachView(){
        this.mvpView = null;
    }
    public boolean isViewAttached(){
        return mvpView !=null;
    }
    public V getVIew(){
        return mvpView;
    }
}
