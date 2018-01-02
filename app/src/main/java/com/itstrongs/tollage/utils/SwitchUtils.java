package com.itstrongs.tollage.utils;

import android.app.Activity;
import android.content.Intent;

import com.itstrongs.tollage.activity.ImageViewActivity;
import com.itstrongs.tollage.activity.PDFViewActivity;
import com.itstrongs.tollage.activity.TextViewActivity;
import com.itstrongs.tollage.activity.WebViewActivity;

/**
 * Created by itstrongs on 2017/6/22.
 */

public class SwitchUtils {

    public static void switchTextActivity(Activity activity, String type) {
        Intent intent = new Intent(activity, TextViewActivity.class);
        intent.putExtra("text_type", type);
        activity.startActivity(intent);
    }

    public static void switchWebActivity(Activity activity, String url) {
        Intent intent = new Intent(activity, WebViewActivity.class);
        intent.putExtra("file_url", url);
        activity.startActivity(intent);
    }

    public static void switchImageActivity(Activity activity, String fileUrl) {
        Intent intent = new Intent(activity, ImageViewActivity.class);
        intent.putExtra("file_url", fileUrl);
        activity.startActivity(intent);
    }

    public static void switchPDFActivity(Activity activity, String fileUrl, String fileName) {
        Intent intent = new Intent(activity, PDFViewActivity.class);
        intent.putExtra("file_url", fileUrl);
        intent.putExtra("file_name", fileName);
        activity.startActivity(intent);
    }
}
