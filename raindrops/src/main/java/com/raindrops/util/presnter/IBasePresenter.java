package com.raindrops.util.presnter;


import com.raindrops.util.mvpview.MvpView;

/**
 * Created by huangweizhou on 16/7/28.
 */
public interface IBasePresenter<V extends MvpView> {

    void subscribe();

    void unsubscribe();

    void destory();

}
