package com.example.helloworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helloworld.databinding.ActivityShopListBinding

class ShopListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityShopListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rv1.layoutManager = LinearLayoutManager(baseContext)
        binding.rv1.addItemDecoration(
            DividerItemDecoration(
                baseContext,
                DividerItemDecoration.VERTICAL
            )
        )
        val shopViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ShopViewModel::class.java)
        binding.rv1.adapter = ShopAdapter(shopViewModel)
        (binding.rv1.adapter as ShopAdapter).setShops()
        binding.bt1.setOnClickListener {
            (binding.rv1.adapter as ShopAdapter).addShop(
                Shop(
                    binding.et1.text.toString(),
                    binding.et2.text.toString(),
                    binding.et3.text.toString(),
                    binding.et4.text.toString().toLong()
                )
            )
        }
    }
}