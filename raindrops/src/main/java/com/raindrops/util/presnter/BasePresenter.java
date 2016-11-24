package com.raindrops.util.presnter;


import com.raindrops.util.http.HttpSubscriber;
import com.raindrops.util.http.HttpUtil;
import com.raindrops.util.mvpview.MvpView;

import rx.Observable;
import rx.Subscription;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by huangweizhou on 16/7/28.
 */
public abstract class BasePresenter<V extends MvpView, T> implements
        IBasePresenter<V> {

    protected HttpUtil httpUtil = null;

    protected Object httpService = null;

    protected V baseView;
    /**
     * 统一取消网络请求
     */
    private CompositeSubscription compositeSubscription;

    public BasePresenter(V view) {
        this.baseView = view;
        httpUtil = HttpUtil.getInstance();
        httpService = httpUtil.getHttpService();
        compositeSubscription = new CompositeSubscription();
    }

    protected HttpSubscriber getProgressSubscriber(int tag) {
        baseView.showLoadingDialog();
        return new HttpSubscriber<T>(tag) {
            @Override
            public void onSuccess(T t, int tag) {
                BasePresenter.this.onSuccess(t, tag);
            }

            @Override
            public void onError(String msg, int tag) {
                BasePresenter.this.onError(msg, tag);
            }

            @Override
            public void _complete() {
                baseView.dismissLoadingDialog();
            }
        };
    }

    /**
     * 发送网络请求对结果不进行转化
     *
     * @param observable
     * @param tag
     */
    protected void sendHttp(Observable observable, int tag) {
        checkCompositeSubscription();
        compositeSubscription.add(httpUtil.sendHttp(observable, getProgressSubscriber(tag)));
    }

    protected void sendHttp(Observable observable) {
        sendHttp(observable, 0);
    }

    /**
     * 发送网络请求对结果进行转化
     *
     * @param observable
     * @param tag
     */
    protected void sendHttpWithMap(Observable observable, int tag) {
        checkCompositeSubscription();
        compositeSubscription.add(httpUtil.sendHttpWithMap(observable, getProgressSubscriber
                (tag), setMapFunc()));
    }

    /**
     * 设置转化类
     *
     * @return
     */
    public abstract Func1 setMapFunc();


    protected void sendHttpWithMap(Observable observable) {
        sendHttpWithMap(observable, 0);
    }

    public abstract void onSuccess(T t, int tag);

    public abstract void onError(String msg, int tag);


    /**
     * 取消网络请求
     */
    @Override
    public void unsubscribe() {
        if (compositeSubscription != null && compositeSubscription.hasSubscriptions())
            compositeSubscription.unsubscribe();
    }

    /**
     * 手动添加subscription
     *
     * @param subscription
     */
    public void addSubscription(Subscription subscription) {
        checkCompositeSubscription();
        compositeSubscription.add(subscription);
    }


    private void checkCompositeSubscription() {
        if (compositeSubscription == null) {
            compositeSubscription = new CompositeSubscription();
        }
    }

    /**
     * 销毁
     */
    @Override
    public void destory() {
        unsubscribe();
        if (baseView != null)
            baseView = null;
    }
}
