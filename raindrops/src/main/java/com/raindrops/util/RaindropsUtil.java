package com.raindrops.util;

import android.content.Context;

/**
 * Created by huangweizhou on 2016/11/18.
 */

public class RaindropsUtil {

    private Context context;

    private RaindropsUtil() {

    }

    /**
     * 初始化
     *
     * @param context
     */
    public static void init(Context context) {
        getInstance().setContext(context.getApplicationContext());
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * 内部类实现单例模式
     * 延迟加载，减少内存开销
     */
    private static class SingletonHolder {
        private static RaindropsUtil instance = new RaindropsUtil();
    }

    public static RaindropsUtil getInstance() {
        return SingletonHolder.instance;
    }

}
