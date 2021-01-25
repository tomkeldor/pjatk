package com.example.helloworld

import android.content.Context
import android.util.Log
import android.widget.AdapterView
import android.widget.RemoteViews
import android.widget.RemoteViewsService.RemoteViewsFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


class MyRemoteViewFactory(applicationContext: Context) : RemoteViewsFactory {
    private val mContext: Context = applicationContext
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var user: FirebaseUser
    var allShops: MutableList<String> = arrayListOf()

    override fun onCreate() {
        user = mAuth.currentUser!!
        val database = FirebaseDatabase.getInstance()
        val reference : DatabaseReference = database.getReference("users/${user.uid}/shops")
        var name: String

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (shop in snapshot.children) {
                    name = shop.child("name").value as String
                    if (!allShops.contains(name)) {
                        allShops.add(name)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("error", "loadShop:onCancelled", error.toException())
            }
        })
    }
    override fun onDataSetChanged() {
    }

    override fun onDestroy() {
    }

    override fun getCount(): Int {
        return allShops.count()
    }

    override fun getViewAt(position: Int): RemoteViews? {
        if (position == AdapterView.INVALID_POSITION ) {
            return null
        }
        val rv = RemoteViews(mContext.packageName, R.layout.widget_list_item)
        rv.setTextViewText(R.id.widgetItem, allShops[position])

        return rv
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

}