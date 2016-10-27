package com.example.hpl.weather.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hpl.weather.R;
import com.example.hpl.weather.fragment.WeaFragment;
import com.example.hpl.weather.service.DaemonService;
import com.example.hpl.weather.util.MyUtil;
import com.example.hpl.weather.util.ToastUtil;
import com.example.hpl.weather.util.LogUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    /**
     * Log tag ：MainActivity
     */
    private static final String LOG_TAG = "MainActivity";

    /**
     * 闹钟Tab控件
     */
    private TextView tv_alarm_clock;

    /**
     * 天气Tab控件
     */
    private TextView tv_wea;

    /**
     * 计时Tab控件
     */
    private TextView tv_time;

    /**
     * 更多Tab控件
     */
    private TextView tv_more;

    /**
     * 用于对Fragment进行管理
     */
    private FragmentManager mFm;

    /**
     * Tab未选中文字颜色
     */
    private int mUnSelectColor;

    /**
     * Tab选中时文字颜色
     */
    private int mSelectColor;

    /**
     * 滑动菜单视图
     */
    private ViewPager mViewPager;

    /**
     * Tab页面集合
     */
    private List<Fragment> mFragmentList;

    /**
     * 当前Tab的Index
     */
    private int mCurrentIndex = -1;



    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 配置友盟相关
        //        configureUmeng();
        // 禁止滑动后退
        //        setSwipeBackEnable(false);
        startService(new Intent(this, DaemonService.class));
        setContentView(R.layout.activity_main);
        // 设置主题壁纸
        setThemeWallpaper();

        mFm = getSupportFragmentManager();
        // Tab选中文字颜色
        //        mSelectColor = getResources().getColor(R.color.white);
        // Tab未选中文字颜色
        //        mUnSelectColor = getResources().getColor(R.color.white_trans50);
        // 初始化布局元素
        initViews();
        // 启动程序后选中Tab为闹钟
        //        setTabSelection(0);
    }

    /**
     * 配置友盟设置
     */
    private void configureUmeng() {
        // 使用友盟集成测试模式
        MobclickAgent.setDebugMode(true);
        // 因为以下这些设置是静态的参数，如果在应用中不止一次调用了检测更新的方法，而每次的设置都不一样，
        // 请在每次检测更新的函数之前先恢复默认设置再设置参数，避免在其他地方设置的参数影响到这次更新。
        UmengUpdateAgent.setDefault();
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        UmengUpdateAgent.update(this);

        // 当开发者回复用户反馈后，提醒用户
//        new FeedbackAgent(this).sync();
    }

    /**
     * 设置主题壁纸
     */
    private void setThemeWallpaper() {
        ViewGroup vg = (ViewGroup) findViewById(R.id.llyt_activity_main);
        MyUtil.setBackground(vg, this);
    }



    /**
     * 获取布局元素，并设置事件
     */
    private void initViews() {


        mFragmentList = new ArrayList<>();
        WeaFragment mWeaFragment = new WeaFragment();
        mFragmentList.add(mWeaFragment);


        mViewPager = (ViewPager) findViewById(R.id.fragment_container);
        mViewPager.setAdapter(new MyFragmentPagerAdapter(mFm));
        mViewPager.setOffscreenPageLimit(0);


    }

    /**
     * ViewPager适配器
     */
    class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

    }

    /*@Override
    public void onClick(View v) {
        // 判断选中的Tab
        switch (v.getId()) {
            // 当选中闹钟Tab时
            case R.id.tab_alarm_clock:
                // 切换闹钟视图
                setTabSelection(0);
                break;
            // 当选中天气Tab时
            case tab_wea:
                // 切换天气视图
                setTabSelection(1);
                break;
            // 当选中计时Tab时
            case tab_time:
                // 切换计时视图
                setTabSelection(2);
                break;
            // 当选中更多Tab时
            case tab_more:
                // 切换更多视图
                setTabSelection(3);
                break;
            default:
                break;
        }
    }*/


    /**
     * 清除掉所有的选中状态。
     */
    /*private void clearSelection() {
        // 设置闹钟Tab为未选中状态
        setTextView(R.drawable.ic_alarm_clock_unselect, tv_alarm_clock,
                mUnSelectColor);
        // 设置天气Tab为未选中状态
        setTextView(R.drawable.ic_weather_unselect, tv_wea, mUnSelectColor);
        // 设置计时Tab为未选中状态
        setTextView(R.drawable.ic_time_unselect, tv_time, mUnSelectColor);
        // 设置更多Tab为未选中状态
        setTextView(R.drawable.ic_more_unselect, tv_more, mUnSelectColor);
    }*/


    /*private void setTextView(int iconId, TextView textView, int color) {
        @SuppressWarnings("deprecation") Drawable drawable = getResources().getDrawable(iconId);
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            // 设置图标
            textView.setCompoundDrawables(null, drawable, null, null);
        }
        // 设置文字颜色
        textView.setTextColor(color);
    }*/
    @Override
    protected void onDestroy() {
        LogUtil.d(LOG_TAG, "onDestroy()");
        //        Process.killProcess(Process.myPid());
        super.onDestroy();
    }

    private long mExitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                ToastUtil.showShortToast(this, getString(R.string.press_again_exit));
                mExitTime = System.currentTimeMillis();
            } else {
                MobclickAgent.onKillProcess(this);
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
