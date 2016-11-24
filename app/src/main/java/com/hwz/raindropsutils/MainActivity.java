package com.hwz.raindropsutils;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.raindrops.util.upLoadImage.IUpLoadImageUtil;
import com.raindrops.util.upLoadImage.UpLoadImage;
import com.yalantis.ucrop.UCrop;

public class MainActivity extends AppCompatActivity {


    private UpLoadImage upLoadImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        upLoadImage = new UpLoadImage(this, new UploadExample());
        upLoadImage.showDialog();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            upLoadImage.setImagePath(resultUri);
            upLoadImage.uploadImage(new IUpLoadImageUtil() {
                @Override
                public void complete(String imageUrl) {
                    Log.e("complete", imageUrl);
                }

                @Override
                public void error() {

                }
            });

        } else {
            upLoadImage.dealPicWithCrop(resultCode, data);
        }
    }
}
