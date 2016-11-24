package com.raindrops.util.http;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by huangweizhou on 16/7/13.
 */
public class HttpUtil {
    /**
     * 超时时间
     */
    private static final int DEFAULT_TIMEOUT = 10;
    /**
     * retrofit
     */
    private Retrofit retrofit;
    /**
     * 接口请求
     */
    private static volatile HttpUtil instance = null;

    /**
     * @param okHttpClient
     * @param url          地址域名
     */
    private HttpUtil(OkHttpClient okHttpClient, String url) {
        //创建一个OkHttpClient并设置超时时间
        if (okHttpClient == null) {
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
            httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            //添加迭代器
            httpClientBuilder.addInterceptor(new LoggerInterceptor());
            okHttpClient = httpClientBuilder.build();
        }
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(url)
                .build();
        //        httpService = retrofit.create(HttpService.class);
    }

    /**
     * @param okHttpClient
     * @param url          地址域名
     */
    public static HttpUtil initHttpUtil(OkHttpClient okHttpClient, String url) {
        if (instance == null) {
            synchronized (HttpUtil.class) {
                if (instance == null) {
                    instance = new HttpUtil(okHttpClient, url);
                }
            }
        }
        return instance;
    }

    public static HttpUtil getInstance() {
        return initHttpUtil(null, "");
    }


    public Retrofit getRetrofit() {
        return retrofit;
    }

    private Object httpService;

    public <T> void setHttpService(Class<T> cls) {
        this.httpService = HttpUtil.getInstance().getRetrofit().create(cls);
    }

    public Object getHttpService() {
        return httpService;
    }

    /**
     * 组装Observable
     *
     * @param observable
     */
    public Observable packageObservable(Observable observable) {
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取网络数据不转化
     *
     * @param observable
     */
    public Subscription sendHttp(Observable observable, HttpSubscriber listener) {
        return packageObservable(observable)
                .subscribe(listener);
    }


    /**
     * 获取网络数据转化
     *
     * @param observable
     */
    public <R, T> Subscription sendHttpWithMap(Observable observable, HttpSubscriber<T>
            listener, Func1<R, T> func) {
        return observable.compose(this.<R, T>applySchedulers(func))
                .subscribe(listener);
    }


    /**
     * Observable 转化
     *
     * @param <T>
     * @return
     */
    <R, T> Observable.Transformer<R, T> applySchedulers(final Func1<R, T> func1) {
        return new Observable.Transformer<R, T>() {
            @Override
            public Observable<T> call(Observable<R> baseHttpResultObservable) {
                return baseHttpResultObservable.map(func1)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    //    /**
    //     * 用来统一处理Http请求到的数据,并将数据解析成对应的Model返回
    //     *
    //     * @param <T> Subscriber真正需要的数据类型
    //     */
    //    private class HttpFunc<T> implements Func1<BaseHttpResult<T>, T> {
    //
    //        @Override
    //        public T call(BaseHttpResult<T> baseHttpResult) {
    //            //获取数据失败则抛出异常 会进入到subscriber的onError中
    //            if (!baseHttpResult.getStatus().equals(StaticCode.HTTP_RESPONSE_SUCCESS))
    //                throw new RuntimeException(baseHttpResult.getStatus());
    //
    //            return baseHttpResult.getResults();
    //        }
    //    }
}

