package com.raindrops.util;

import android.util.TypedValue;

/**
 * 常用单位转换的辅助类
 *
 * @author zhy
 */
public class DensityUtils {
    private DensityUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * dp转px
     */
    public static int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, RaindropsUtil
                .getInstance().getContext().getResources()
                .getDisplayMetrics());
    }

    /**
     * sp转px
     */
    public static int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal,
                RaindropsUtil
                        .getInstance().getContext().getResources()
                        .getDisplayMetrics());
    }

    /**
     * px转dp
     */
    public static float px2dp(float pxVal) {
        final float scale = RaindropsUtil
                .getInstance().getContext().getResources().getDisplayMetrics()
                .density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     */
    public static float px2sp(float pxVal) {
        return (pxVal / RaindropsUtil
                .getInstance().getContext().getResources().getDisplayMetrics()
                .scaledDensity);
    }

}
