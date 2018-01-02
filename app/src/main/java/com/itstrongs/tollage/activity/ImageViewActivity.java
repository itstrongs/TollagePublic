package com.itstrongs.tollage.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.itstrongs.tollage2.R;
import com.itstrongs.utils.HttpUtils;

/**
 * Created by itstrongs on 2017/6/10.
 */

public class ImageViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);


        final ImageView mImgShow = (ImageView) findViewById(R.id.image_view);
        String fileUrl = getIntent().getStringExtra("file_url");
        HttpUtils.doLoadImage(fileUrl, new HttpUtils.ImageCallback() {
            @Override
            public void onSuccess(final Bitmap bitmap) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mImgShow.setImageBitmap(bitmap);
                    }
                });
            }
        });
    }
}
