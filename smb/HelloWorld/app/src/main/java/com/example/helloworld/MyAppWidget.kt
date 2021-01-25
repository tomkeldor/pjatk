package com.example.helloworld

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.RemoteViews
import android.widget.Toast


class MyAppWidget : AppWidgetProvider() {
    override fun onUpdate(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetIds: IntArray
    ) {
        val views = RemoteViews(context.packageName, R.layout.my_app_widget)
        views.setTextViewText(R.id.appwidget_text, context.getString(R.string.appwidget_text))
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, 1, views)
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        when (intent?.action) {
            context?.getString(R.string.action_show) -> {
                val appWidgetManager = AppWidgetManager.getInstance(context)
                val remoteViews = RemoteViews(context!!.packageName, R.layout.my_app_widget)
                val myWidget = ComponentName(context, MyAppWidget::class.java)
                remoteViews.setViewVisibility(R.id.widgetListView, View.GONE)
                remoteViews.setViewVisibility(R.id.wButtonPrev, View.VISIBLE)
                remoteViews.setViewVisibility(R.id.wButtonNext, View.VISIBLE)
                remoteViews.setImageViewResource(R.id.wImageView, R.drawable.img1)
                appWidgetManager.updateAppWidget(myWidget, remoteViews)
            }
            context?.getString(R.string.action_prev) -> {
                var imageId = intent?.getIntExtra("imgId", 1)
                val appWidgetManager = AppWidgetManager.getInstance(context)
                val remoteViews = RemoteViews(context!!.packageName, R.layout.my_app_widget)
                val myWidget = ComponentName(context, MyAppWidget::class.java)
                val appWidgetIds = appWidgetManager.getAppWidgetIds(myWidget)

                if (imageId!! > 1) {
                    imageId = imageId.minus(1)
                    val newImageId = context.resources.getIdentifier("img$imageId", "drawable", context.packageName)
                    remoteViews.setImageViewResource(R.id.wImageView, newImageId)
                    appWidgetManager.updateAppWidget(myWidget, remoteViews)
                    for (appWidgetId in appWidgetIds) {
                        updateAppWidget(context, appWidgetManager, appWidgetId, imageId, remoteViews)
                    }
                } else {
                    Toast.makeText(context, "To jest pierwszy obrazek c:", Toast.LENGTH_SHORT).show()
                }
            }
            context?.getString(R.string.action_next) -> {
                var imageId = intent?.getIntExtra("imgId", 1)
                val appWidgetManager = AppWidgetManager.getInstance(context)
                val remoteViews = RemoteViews(context!!.packageName, R.layout.my_app_widget)
                val myWidget = ComponentName(context, MyAppWidget::class.java)
                val appWidgetIds = appWidgetManager.getAppWidgetIds(myWidget)

                if (imageId == 15) {
                    Toast.makeText(context, "To już ostatni obrazek :c", Toast.LENGTH_SHORT).show()
                } else {
                    imageId = imageId?.plus(1)
                    val newImageId = context.resources.getIdentifier("img$imageId", "drawable", context.packageName)
                    remoteViews.setImageViewResource(R.id.wImageView, newImageId)
                    for (appWidgetId in appWidgetIds) {
                        updateAppWidget(context, appWidgetManager, appWidgetId, imageId!!, remoteViews)
                    }
                }
            }
            context?.getString(R.string.action_shop) -> {
                val appWidgetManager = AppWidgetManager.getInstance(context)
                val remoteViews = RemoteViews(context!!.packageName, R.layout.my_app_widget)
                val myWidget = ComponentName(context, MyAppWidget::class.java)
                val listViewIntent = Intent(context, MyRemoteViewService::class.java)
                remoteViews.setImageViewResource(R.id.wImageView, R.drawable.empty)
                remoteViews.setViewVisibility(R.id.wButtonPrev, View.GONE)
                remoteViews.setViewVisibility(R.id.wButtonNext, View.GONE)
                remoteViews.setViewVisibility(R.id.widgetListView, View.VISIBLE)
                remoteViews.setRemoteAdapter(R.id.widgetListView, listViewIntent)
                appWidgetManager.updateAppWidget(myWidget, remoteViews)
            }
        }
        if (intent?.action.equals(context?.getString(R.string.action_back)) ||
                intent?.action.equals(context?.getString(R.string.action_play)) ||
                intent?.action.equals(context?.getString(R.string.action_pause)) ||
                intent?.action.equals(context?.getString(R.string.action_stop)) ||
                intent?.action.equals(context?.getString(R.string.action_skip))) {
            val broadcastIntent = Intent(context?.getString(R.string.broadcast_audio_player))
            broadcastIntent.putExtra("button", intent?.action)
            context?.sendBroadcast(broadcastIntent)
        }
    }
}

internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int, imgId: Int, views: RemoteViews) {
    var requestCode = 0
    requestCode++

    //region WWW
    val intent1 = Intent(Intent.ACTION_VIEW)
    intent1.data = Uri.parse("https://www.pja.edu.pl")
    val pendingIntent1 = PendingIntent.getActivity(
            context,
            requestCode,
            intent1,
            PendingIntent.FLAG_UPDATE_CURRENT
    )
    views.setOnClickPendingIntent(R.id.wButtonWeb, pendingIntent1)
    //endregion

    //region ImageView
    val intent2 = Intent(context.getString(R.string.action_show))
    intent2.component = ComponentName(context, MyAppWidget::class.java)
    val pendingIntent2 = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent2,
            PendingIntent.FLAG_UPDATE_CURRENT
    )
    views.setOnClickPendingIntent(R.id.wButtonImg, pendingIntent2)

    val intent2a = Intent(context.getString(R.string.action_prev))
    intent2a.component = ComponentName(context, MyAppWidget::class.java)
    intent2a.putExtra("imgId", imgId)
    val pendingIntent2a = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent2a,
            PendingIntent.FLAG_UPDATE_CURRENT
    )
    views.setOnClickPendingIntent(R.id.wButtonPrev, pendingIntent2a)

    val intent2b = Intent(context.getString(R.string.action_next))
    intent2b.component = ComponentName(context, MyAppWidget::class.java)
    intent2b.putExtra("imgId", imgId)
    val pendingIntent2b = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent2b,
            PendingIntent.FLAG_UPDATE_CURRENT
    )
    views.setOnClickPendingIntent(R.id.wButtonNext, pendingIntent2b)
    //endregion

    //region MediaPlayer
    val intent3a = Intent(context.getString(R.string.action_back))
    intent3a.component = ComponentName(context, MyAppWidget::class.java)
    val pendingIntent3a = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent3a,
            PendingIntent.FLAG_UPDATE_CURRENT
    )
    views.setOnClickPendingIntent(R.id.wButtonBack, pendingIntent3a)

    val intent3b = Intent(context.getString(R.string.action_play))
    intent3b.component = ComponentName(context, MyAppWidget::class.java)
    val pendingIntent3b = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent3b,
            PendingIntent.FLAG_UPDATE_CURRENT
    )
    views.setOnClickPendingIntent(R.id.wButtonPlay, pendingIntent3b)

    val intent3c = Intent(context.getString(R.string.action_pause))
    intent3c.component = ComponentName(context, MyAppWidget::class.java)
    val pendingIntent3c = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent3c,
            PendingIntent.FLAG_UPDATE_CURRENT
    )
    views.setOnClickPendingIntent(R.id.wButtonPause, pendingIntent3c)

    val intent3d = Intent(context.getString(R.string.action_stop))
    intent3d.component = ComponentName(context, MyAppWidget::class.java)
    val pendingIntent3d = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent3d,
            PendingIntent.FLAG_UPDATE_CURRENT
    )
    views.setOnClickPendingIntent(R.id.wButtonStop, pendingIntent3d)

    val intent3e = Intent(context.getString(R.string.action_skip))
    intent3e.component = ComponentName(context, MyAppWidget::class.java)
    val pendingIntent3e = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent3e,
            PendingIntent.FLAG_UPDATE_CURRENT
    )
    views.setOnClickPendingIntent(R.id.wButtonSkip, pendingIntent3e)
    //endregion

    //region ShopList
    val intent4 = Intent(context.getString(R.string.action_shop))
    intent4.component = ComponentName(context, MyAppWidget::class.java)
    val pendingIntent4 = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent4,
            PendingIntent.FLAG_UPDATE_CURRENT
    )
    views.setOnClickPendingIntent(R.id.wButtonShop, pendingIntent4)
    //endregion

    appWidgetManager.updateAppWidget(appWidgetId, views)
}