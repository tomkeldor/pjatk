package com.example.helloworld

import android.content.Intent
import android.widget.RemoteViewsService


class MyRemoteViewService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return MyRemoteViewFactory(this.applicationContext)
    }
}