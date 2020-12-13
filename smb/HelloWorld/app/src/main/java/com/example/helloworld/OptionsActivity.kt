package com.example.helloworld

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.settings_activity.*

class OptionsActivity : AppCompatActivity() {

    private lateinit var sp: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        sp = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        tB1.setOnCheckedChangeListener { _, _ ->
            editor = sp.edit()
            editor.putBoolean(applicationContext.getString(R.string.bold), tB1.isChecked)
            editor.apply()
            Log.i(applicationContext.getString(R.string.bold), tB1.isChecked.toString())
        }
        tB2.setOnCheckedChangeListener { _, _ ->
            editor = sp.edit()
            editor.putBoolean(applicationContext.getString(R.string.italics), tB2.isChecked)
            editor.apply()
            Log.i(applicationContext.getString(R.string.italics), tB2.isChecked.toString())
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onStart() {
        super.onStart()
        tB1.isChecked = sp.getBoolean(applicationContext.getString(R.string.bold), false)
        tB2.isChecked = sp.getBoolean(applicationContext.getString(R.string.italics), false)
    }

    override fun onStop() {
        super.onStop()
        editor = sp.edit()
        editor.putBoolean(applicationContext.getString(R.string.bold), tB1.isChecked)
        editor.putBoolean(applicationContext.getString(R.string.italics), tB2.isChecked)
        editor.apply()
    }


}