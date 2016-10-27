package com.example.hpl.weather.util;

import android.util.Log;

import com.example.hpl.weather.BuildConfig;

/**
 * Created by Administrator on 2016/10/24.
 */

public class LogUtil {
    public static void v(String tag, String msg) {
        if (BuildConfig.LOG_DEBUG) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (BuildConfig.LOG_DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (BuildConfig.LOG_DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (BuildConfig.LOG_DEBUG) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (BuildConfig.LOG_DEBUG) {
            Log.e(tag, msg);
        }
    }

}
