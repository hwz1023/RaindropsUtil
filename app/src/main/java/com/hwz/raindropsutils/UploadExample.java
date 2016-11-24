package com.hwz.raindropsutils;

import android.util.Log;

import com.raindrops.util.upLoadImage.IHUploadImage;
import com.raindrops.util.upLoadImage.IUpLoadImageUtil;

/**
 * Created by huangweizhou on 2016/11/24.
 */

public class UploadExample implements IHUploadImage {
    @Override
    public void uploadImage(String imagePath, IUpLoadImageUtil l) {
        Log.e("imagePath", imagePath);
        l.complete(imagePath);
    }
}
