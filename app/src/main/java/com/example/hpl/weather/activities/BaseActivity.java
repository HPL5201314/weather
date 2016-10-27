package com.example.hpl.weather.activities;

import android.os.Bundle;

import com.example.hpl.weather.LeakCanaryApplication;
import com.example.hpl.weather.util.LogUtil;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by Administrator on 2016/10/24.
 */

public class BaseActivity extends SwipeBackActivity {

    private static final String LOG_TAG = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        requestWindowFeature(Window.FEATURE_NO_TITLE);
        LogUtil.i(LOG_TAG, getClass().getSimpleName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = LeakCanaryApplication.getRefWatcher(this);
        refWatcher.watch(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 友盟session的统计
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
