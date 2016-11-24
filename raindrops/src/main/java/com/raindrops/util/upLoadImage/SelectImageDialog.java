package com.raindrops.util.upLoadImage;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.raindrops.util.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by huangweizhou on 16/5/19.
 */
public class SelectImageDialog extends Dialog implements View.OnClickListener {
    /**
     * 图片路径
     */
    private String mImagePath;
    private Activity activity;
    /**
     * 拍照
     */
    public static final int SELECT_IMAGE_BY_CAMERA = 10;
    /**
     * 相册
     */
    public static final int SELECT_IMAGE_BY_ALBUM = 11;

    public SelectImageDialog(Activity context) {
        this(context, R.style.raindropsActionSheetDialogStyle);
    }

    public SelectImageDialog(Activity context, int theme) {
        super(context, theme);
        activity = context;
        setContentView(R.layout.layout_upload_photo_dialog);
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        findViewById(R.id.cancel).setOnClickListener(this);
        findViewById(R.id.album).setOnClickListener(this);
        findViewById(R.id.camera).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.cancel) {
            dismiss();

        } else if (i == R.id.album) {
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            activity.startActivityForResult(intent, SELECT_IMAGE_BY_ALBUM);
        } else if (i == R.id.camera) {
            String SDState = Environment.getExternalStorageState();
            if (SDState.equals(Environment.MEDIA_MOUNTED)) {
                /**
                 * 通过指定图片存储路径，解决部分机型onActivityResult回调 data返回为null的情况
                 */
                //获取与应用相关联的路径
                String imageFilePath = activity.getExternalFilesDir(Environment
                        .DIRECTORY_PICTURES).getAbsolutePath();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
                //根据当前时间生成图片的名称
                String timestamp = "/" + formatter.format(new Date()) + ".jpg";
                File imageFile = new File(imageFilePath, timestamp);// 通过路径创建保存文件
                mImagePath = imageFile.getAbsolutePath();
                Uri imageFileUri = Uri.fromFile(imageFile);// 获取文件的Uri
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);// 告诉相机拍摄完毕输出图片到指定的Uri
                activity.startActivityForResult(intent, SELECT_IMAGE_BY_CAMERA);
            } else {
                Toast.makeText(activity, "内存卡不存在！", Toast.LENGTH_LONG).show();
            }
        } else {
        }
    }


    public String getImagePath() {
        return mImagePath;
    }

    public void setImagePath(String mImagePath) {
        this.mImagePath = mImagePath;
    }
}
