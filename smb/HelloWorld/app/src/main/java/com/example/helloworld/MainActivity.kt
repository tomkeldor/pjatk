package com.example.helloworld

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent1 = Intent(baseContext, ListActivity::class.java)
        bt1.setOnClickListener {
            startActivity(intent1)
        }
        val intent2 = Intent(baseContext, OptionsActivity::class.java)
        bt2.setOnClickListener {
            startActivity(intent2)
        }
    }
}