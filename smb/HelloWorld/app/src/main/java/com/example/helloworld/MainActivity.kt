package com.example.helloworld

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var sp: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sp = getPreferences(Context.MODE_PRIVATE)
        val intent1 = Intent(baseContext, ListActivity::class.java)
        bt1.setOnLongClickListener {
            startActivity(intent1)
                    true
        }
    }

    override fun onStart() {
        super.onStart()
        tv1.text = sp.getString("tv1_text", "Nothing")
    }

    override fun onStop() {
        super.onStop()
        editor = sp.edit()
        editor.putString("tv1_text", et1.text.toString())
        editor.apply()
    }

    fun click(view: View) {
        val str = et.text.toString()
        Log.i("SMB", "click")
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("et_text", et.text.toString())
        startActivity(intent)
        tv1.text = et1.text
    }
}