package com.ncode.muplayer

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.ncode.muplayer.services.MediaPlayerServices

/**
 * Implementation of App Widget functionality.
 */
class MediaPLayerWidget : AppWidgetProvider() {

    var mediaPendingIntent : PendingIntent? = null

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }

       val mediaService = Intent(context, MediaPlayerServices :: class.java)

       if(mediaPendingIntent == null) {
            mediaPendingIntent = PendingIntent.getService(context, 0, mediaService, PendingIntent.FLAG_CANCEL_CURRENT)
       }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
    val widgetText = context.getString(R.string.appwidget_text)
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.media_p_layer_widget)


    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}