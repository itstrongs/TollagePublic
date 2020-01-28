package com.itstrongs.utils;

import android.util.Log;

/**
 * Created by itstrongs on 2017/6/22.
 */

public class LogUtils {

    public static String TAG = "itstrongs_";

    public static void d(String msg) {
        Log.d(TAG, TAG + ":" + msg);
    }

    public static void w(String msg) {
        Log.w(TAG, TAG + "_" + msg);
    }

    public static void e(String msg) {
        Log.e(TAG, TAG + "_" + msg);
    }
}
