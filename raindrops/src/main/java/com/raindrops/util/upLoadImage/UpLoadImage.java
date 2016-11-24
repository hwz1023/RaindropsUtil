package com.raindrops.util.upLoadImage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;

import com.yalantis.ucrop.UCrop;

import java.io.File;

/**
 * 图片上传类
 * Created by huangweizhou on 16/5/27.
 */
public class UpLoadImage implements IUpLoadImage {
    private Context mContext;
    private SelectImageDialog imageDialog;
    private IHUploadImage uploadImageUtil;


    public UpLoadImage(Activity context, IHUploadImage uploadImageUtil) {
        this.mContext = context;
        imageDialog = new SelectImageDialog(context);
        this.uploadImageUtil = uploadImageUtil;
    }

    public IHUploadImage getUploadImageUtil() {
        return uploadImageUtil;
    }

    public void setUploadImageUtil(IHUploadImage uploadImageUtil) {
        this.uploadImageUtil = uploadImageUtil;
    }

    /**
     * 处理相机回调并显示
     *
     * @param resultCode
     * @param data
     * @param imageView
     */
    @Override
    public void dealPic(int resultCode, Intent data, ImageView imageView, int width, int height) {
        dealPic(resultCode, data);
        imageView.setTag(imageDialog.getImagePath());
        BitmapWorkerTask task = new BitmapWorkerTask(imageView);
        task.execute(imageDialog.getImagePath(), Integer.toString(width), Integer.toString(height));
    }

    /**
     * 处理相机并且裁剪
     *
     * @param resultCode
     * @param data
     * @return
     */
    @Override
    public String dealPicWithCrop(int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return null;
        if (data != null && data.getData() != null) {
            String mImagePath = ImageUtils.getImageAbsolutePath(mContext, data.getData());
            if (mImagePath != null)
                imageDialog.setImagePath(mImagePath);
        }
        File file = new File(imageDialog.getImagePath());
        UCrop.of(Uri.fromFile(file), Uri.fromFile(new File(mContext.getCacheDir(), "headImg")))
                .withAspectRatio(1, 1)
                .withMaxResultSize(300, 300)
                .start((Activity) mContext);
        return imageDialog.getImagePath();
    }

    @Override
    public String dealPic(int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return null;
        if (data != null && data.getData() != null) {
            String mImagePath = ImageUtils.getImageAbsolutePath(mContext, data.getData());
            if (mImagePath != null)
                imageDialog.setImagePath(mImagePath);
        }
        return imageDialog.getImagePath();
    }

    /**
     * 上传图片
     *
     * @param l
     */
    @Override
    public void uploadImage(IUpLoadImageUtil l) {
        uploadImageUtil.uploadImage(imageDialog.getImagePath(), l);
    }

    /**
     * 显示相机 图片选择 Dialog
     */
    @Override
    public void showDialog() {
        imageDialog.show();
    }

    /**
     * 隐藏相机 图片选择 Dialog
     */
    @Override
    public void dismissDialog() {
        imageDialog.dismiss();
    }

    public void setImagePath(Uri uri) {
        imageDialog.setImagePath(ImageUtils.getImageAbsolutePath(mContext, uri));
    }
}
