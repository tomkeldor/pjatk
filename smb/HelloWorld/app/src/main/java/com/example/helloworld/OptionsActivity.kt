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
        sp = PreferenceManager.getDefaultSharedPreferences(baseContext)
        tB1.setOnCheckedChangeListener { _, _ ->
            editor = sp.edit()
            editor.putBoolean("bold", tB1.isChecked)
            editor.apply()
            Log.i("bold", tB1.isChecked.toString())
        }
        tB2.setOnCheckedChangeListener { _, _ ->
            editor = sp.edit()
            editor.putBoolean("italics", tB2.isChecked)
            editor.apply()
            Log.i("italics", tB2.isChecked.toString())
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onStart() {
        super.onStart()
        tB1.isChecked = sp.getBoolean("bold", false)
        tB2.isChecked = sp.getBoolean("italics", false)
        Log.i("ustawienia","wczytane")
    }

    override fun onStop() {
        super.onStop()
        editor = sp.edit()
        editor.putBoolean("bold", tB1.isChecked)
        editor.putBoolean("italics", tB2.isChecked)
        editor.apply()
        Log.i("ustawienia","zapisane")
    }


}