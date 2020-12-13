package com.example.helloworld

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helloworld.databinding.ActivityListBinding

class SharedListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rv1.layoutManager = LinearLayoutManager(baseContext)
        binding.rv1.addItemDecoration(
            DividerItemDecoration(
                baseContext,
                DividerItemDecoration.VERTICAL
            )
        )
        val sharedProductViewModel = MyViewModelFactory(this.application, "shared").create(ProductViewModel::class.java)
        //productViewModel.insert(Product("Mleko", 3.99, 2, true))
        binding.rv1.adapter = MyAdapter(sharedProductViewModel, baseContext)
        (binding.rv1.adapter as MyAdapter).setProducts()
        binding.bt1.setOnClickListener {
            val et2 = binding.et2.text.toString()
            var dbValue = 0.00
            if ("" != et2) {
                dbValue = et2.toDouble()
            }
            val et3 = binding.et3.text.toString()
            var intValue : Long = 0
            if ("" != et3) {
                intValue = et3.toLong()
            }
            (binding.rv1.adapter as MyAdapter).addProduct(
                Product(
                    binding.et1.text.toString(),
                    dbValue,
                    intValue,
                    false
                )
            )
        }
        binding.bt2.setOnClickListener {
            (binding.rv1.adapter as MyAdapter).clearProducts()
        }
    }
}