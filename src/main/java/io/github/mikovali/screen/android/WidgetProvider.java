package io.github.mikovali.screen.android;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;
import android.widget.RemoteViews;

public class WidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        final boolean mode = preferences.getBoolean(context.getString(R.string.settings_key_mode),
                false);
        final int drawableRes = mode
                ? R.drawable.ic_brightness_high_40dp
                : R.drawable.ic_brightness_auto_40dp;
        final PendingIntent pendingIntent = PendingIntent.getService(context, 0,
                new Intent(context, ScreenModeToggleService.class), 0);

        for (final int appWidgetId : appWidgetIds) {
            final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
            views.setImageViewResource(R.id.widgetModeButton, drawableRes);
            views.setOnClickPendingIntent(R.id.widgetModeButton, pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}
