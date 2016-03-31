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

    // must be same as in the manifest
    public static final String ACTION_APPWIDGET_UPDATE
            = "io.github.mikovali.screen.android.action.APPWIDGET_UPDATE";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_APPWIDGET_UPDATE.equals(intent.getAction())) {
            final int[] appWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
            if (appWidgetIds != null && appWidgetIds.length > 0) {
                onUpdate(context, AppWidgetManager.getInstance(context), appWidgetIds);
            }
        } else {
            super.onReceive(context, intent);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        final boolean mode = preferences.getBoolean(context.getString(R.string.settings_key_mode),
                false);
        final int drawableRes = mode
                ? R.drawable.ic_brightness_high_40dp
                : R.drawable.ic_brightness_auto_40dp;
        final PendingIntent pendingIntent = ScreenModeToggleService.createPendingIntent(context);

        for (final int appWidgetId : appWidgetIds) {
            final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
            views.setImageViewResource(R.id.widgetModeButton, drawableRes);
            views.setOnClickPendingIntent(R.id.widgetModeButton, pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}
