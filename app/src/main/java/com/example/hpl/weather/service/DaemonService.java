package com.example.hpl.weather.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.coolerfall.daemon.Daemon;
import com.example.hpl.weather.util.LogUtil;



/**
 * Created by Administrator on 2016/10/25.
 */

public class DaemonService extends Service {

    /**
     * Log tag ：DaemonService
     */
    private static final String LOG_TAG = "DaemonService";

    /**
     * 定时唤醒的时间间隔，5分钟
     */
    private final static int ALARM_INTERVAL = 5 * 60 * 1000;
    private final static int WAKE_REQUEST_CODE = 6666;

    private final static int GRAY_SERVICE_ID = -1001;

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d(LOG_TAG, "onCreate");
        Daemon.run(DaemonService.this, DaemonService.class, Daemon.INTERVAL_ONE_MINUTE);
//        startTimeTask();
//        grayGuard();

        // Notification notification = new Notification();
        // startForeground(-1, notification);
    }

   /* private void startTimeTask() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<weather> list = weatherOperate.getInstance().loadweathers();
                for (weather weather : list) {
                    // 当闹钟为开时刷新开启闹钟
                    if (weather.isOnOff()) {
                        MyUtil.startweather(DaemonService.this, weather);
                    }
                }

                SharedPreferences preferences = getSharedPreferences(
                        WeacConstants.EXTRA_WEAC_SHARE, Activity.MODE_PRIVATE);
                // 倒计时时间
                long countdown = preferences.getLong(WeacConstants.COUNTDOWN_TIME, 0);
                boolean isStop = preferences.getBoolean(WeacConstants.IS_STOP, false);
                if (countdown != 0 && !isStop) {
                    long now = SystemClock.elapsedRealtime();
                    long remainTime = countdown - now;
                    if (remainTime > 0 && (remainTime / 1000 / 60) < 60) {
                        MyUtil.startAlarmTimer(DaemonService.this, remainTime);
                    }
                }
            }
        }).start();
    }*/

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d(LOG_TAG, "onDestroy");
        startService(new Intent(this, DaemonService.class));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.d(LOG_TAG, "onStartCommand");
        flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    /*private void grayGuard() {
        if (Build.VERSION.SDK_INT < 18) {
            startForeground(GRAY_SERVICE_ID, new Notification());//API < 18 ，此方法能有效隐藏Notification上的图标
        } else {
            Intent innerIntent = new Intent(this, DaemonInnerService.class);
            startService(innerIntent);
            startForeground(GRAY_SERVICE_ID, new Notification());
        }

        //发送唤醒广播来促使挂掉的UI进程重新启动起来
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent();
        alarmIntent.setAction(WakeReceiver.GRAY_WAKE_ACTION);
        PendingIntent operation = PendingIntent.getBroadcast(this, WAKE_REQUEST_CODE, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), ALARM_INTERVAL, operation);
    }*/

    /**
     * 给 API >= 18 的平台上用的灰色保活手段
     */
    public static class DaemonInnerService extends Service {

        @Override
        public void onCreate() {
            LogUtil.i(LOG_TAG, "InnerService -> onCreate");
            super.onCreate();
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            LogUtil.i(LOG_TAG, "InnerService -> onStartCommand");
            startForeground(GRAY_SERVICE_ID, new Notification());
            //stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }

        @Override
        public IBinder onBind(Intent intent) {
            throw new UnsupportedOperationException("Not yet implemented");
        }

        @Override
        public void onDestroy() {
            LogUtil.i(LOG_TAG, "InnerService -> onDestroy");
            super.onDestroy();
        }
    }
}
