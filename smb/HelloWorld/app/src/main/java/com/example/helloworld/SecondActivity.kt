package com.example.helloworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.helloworld.R
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.TextView

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val intent = intent
        val tv = findViewById<View>(R.id.textView) as TextView
        val str = intent.getStringExtra("et_text")
        Log.i("SMB", str!!)
        tv.text = str
    }
}