package io.github.mikovali.screen.android;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;

import static io.github.mikovali.screen.android.Constants.NOTIFICATION_MODE_ON;

public class ScreenModeService extends Service {

    private NotificationManager notificationManager;
    private WindowManager windowManager;
    private SharedPreferences preferences;

    private View view;

    private void showNotification() {
        final Intent intent = new Intent(this, Activity.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        final Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.notifications_mode_content_title))
                .setContentText(getString(R.string.notifications_mode_content_text))
                .setContentIntent(pendingIntent)
                .setLocalOnly(true)
                .setOngoing(true)
                .build();
        notificationManager.notify(NOTIFICATION_MODE_ON, notification);
    }

    private void hideNotification() {
        notificationManager.cancel(NOTIFICATION_MODE_ON);
    }

    private void showView() {
        view = new View(this);
        view.setKeepScreenOn(true);

        final WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                0, 0,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        windowManager.addView(view, layoutParams);
    }

    private void hideView() {
        windowManager.removeView(view);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        showNotification();
        showView();
    }

    @Override
    public void onDestroy() {
        hideView();
        hideNotification();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final boolean mode = preferences.getBoolean(getString(R.string.settings_key_mode), false);
        if (!mode) {
            stopSelf();
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
