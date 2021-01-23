package com.example.helloworld

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast


/**
 * Implementation of App Widget functionality.
 */
class MyAppWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        val widgetText = context.getString(R.string.appwidget_text)
        // Construct the RemoteViews object
        val views = RemoteViews(context.packageName, R.layout.my_app_widget)
        views.setTextViewText(R.id.appwidget_text, widgetText)
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, 1, views)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
        Toast.makeText(context, "Pierwszy widget.", Toast.LENGTH_SHORT).show()
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
        Toast.makeText(context, "Ostatni widget.", Toast.LENGTH_SHORT).show()
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if(intent?.action.equals(context?.getString(R.string.action_show)))
        {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val remoteViews = RemoteViews(context!!.packageName, R.layout.my_app_widget)
            val myWidget = ComponentName(context, MyAppWidget::class.java)

            remoteViews.setImageViewResource(R.id.wImageView, R.drawable.img1)
            appWidgetManager.updateAppWidget(myWidget, remoteViews)
        }

        if(intent?.action.equals(context?.getString(R.string.action_prev)))
        {

            var imageId = intent?.getIntExtra("imgId", 1)
            Log.i("imgId", imageId.toString())
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val remoteViews = RemoteViews(context!!.packageName, R.layout.my_app_widget)
            val myWidget = ComponentName(context, MyAppWidget::class.java)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(myWidget)

            if(imageId!! > 1)
            {
                imageId = imageId?.minus(1)
                val newImageid = context.resources.getIdentifier("img$imageId", "drawable", context.packageName)
                remoteViews.setImageViewResource(R.id.wImageView, newImageid)
                appWidgetManager.updateAppWidget(myWidget, remoteViews)
                for (appWidgetId in appWidgetIds) {
                    updateAppWidget(context, appWidgetManager, appWidgetId, imageId!!, remoteViews)
                }
            }
            else
            {
                Toast.makeText(context, "To jest pierwszy obrazek c:", Toast.LENGTH_SHORT).show()
            }
        }

        if(intent?.action.equals(context?.getString(R.string.action_next)))
        {
            var imageId = intent?.getIntExtra("imgId", 1)
            Log.i("imgId", imageId.toString())
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val remoteViews = RemoteViews(context!!.packageName, R.layout.my_app_widget)
            val myWidget = ComponentName(context, MyAppWidget::class.java)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(myWidget)

            if(imageId == 15)
            {
                Toast.makeText(context, "To już ostatni obrazek :c", Toast.LENGTH_SHORT).show()
            }
            else
            {
                imageId = imageId?.plus(1)
                val newImageId = context.resources.getIdentifier("img$imageId", "drawable", context.packageName)
                remoteViews.setImageViewResource(R.id.wImageView, newImageId)
                for (appWidgetId in appWidgetIds) {
                    updateAppWidget(context, appWidgetManager, appWidgetId, imageId!!, remoteViews)
                }
            }
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

    //region ShopList
    val intent4 = Intent(context, ShopListActivity::class.java)
    val pendingIntent4 = PendingIntent.getActivity(
        context,
        requestCode,
        intent4,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    views.setOnClickPendingIntent(R.id.wButtonShop, pendingIntent4)
    //endregion

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}