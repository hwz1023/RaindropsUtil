package com.raindrops.util.upLoadImage;

import android.content.Intent;
import android.widget.ImageView;

/**
 * Created by huangweizhou on 16/5/27.
 */
public interface IUpLoadImage {

    void dealPic(int resultCode, Intent data, ImageView imageView, int width, int height);

    String dealPic(int resultCode, Intent data);

    String dealPicWithCrop(int resultCode, Intent data);

    void uploadImage(IUpLoadImageUtil l);

    void showDialog();

    void dismissDialog();

}
