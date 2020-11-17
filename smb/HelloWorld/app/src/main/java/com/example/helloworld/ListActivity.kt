package com.example.helloworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helloworld.databinding.ActivityListBinding

class ListActivity : AppCompatActivity() {

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
        val productViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ProductViewModel::class.java)
        productViewModel.allProducts.observe(this, Observer { products ->
            products?.let {
                (binding.rv1.adapter as MyAdapter).setProducts(it)
            }
        })
        productViewModel.insert(Product("Mleko", 3.99, 2, true))
        productViewModel.insert(Product("Masło", 4.99, 3, false))
        productViewModel.insert(Product("Chleb", 4.49, 1, false))
        productViewModel.insert(Product("Jogurt", 1.99, 10, true))
        productViewModel.insert(Product("Parówki", 6.49, 2, false))
        productViewModel.insert(Product("Ketchup", 5.99, 3, false))
        productViewModel.insert(Product("Herbata", 5.49, 2, false))
        productViewModel.insert(Product("Kawa", 10.99, 1, true))
        binding.rv1.adapter = MyAdapter(productViewModel, baseContext)
        binding.bt1.setOnClickListener {
            val et2 = binding.et2.text.toString()
            var dbValue = 0.00
            if ("" != et2) {
                dbValue = et2.toDouble()
            }
            val et3 = binding.et3.text.toString()
            var intValue = 0
            if ("" != et3) {
                intValue = Integer.parseInt(et3)
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