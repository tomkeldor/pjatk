package com.example.helloworld

import android.app.Activity
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import com.example.helloworld.databinding.ActivityMainBinding
import com.example.helloworld.databinding.SecondaryActivityBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(LayoutInflater) //View Binding
        setContentView(binding.root)
//        setContentView(R.layout.activity_main)
//        val tv = findViewById<TextView>(R.id.tv1)
//        tv.text = getString(R.string.witaj)
//        tv.setTextColor(Color.GREEN)
//        tv1.text = getString(R.string.witaj) //Kotlin synthetic properties
//        tv1.setTextColor(Color.GREEN)
        binding.tv1.text = getString(R.string.witaj)
        binding.tv1.setTextColor(Color.GREEN)
    }
}