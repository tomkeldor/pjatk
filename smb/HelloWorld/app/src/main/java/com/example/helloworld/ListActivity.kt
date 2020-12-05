package com.example.helloworld

import android.content.ComponentName
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helloworld.databinding.ActivityListBinding
import kotlinx.android.synthetic.main.settings_activity.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*

class ListActivity : AppCompatActivity() {

    private var id = 0
    private lateinit var binding : ActivityListBinding
    private lateinit var myLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myLayoutManager =  LinearLayoutManager(baseContext)
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
        val broadcast = Intent()
        broadcast.component = ComponentName(
                "com.example.myapp",
                "com.example.myapp.ProductReceiver"
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            broadcast.putExtra("channel", getString(R.string.channel_id))
        }
        broadcast.putExtra("id", id++)
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
            val product = Product(
                    binding.et1.text.toString(),
                    dbValue,
                    intValue,
                    false
            )
            product.id = productViewModel.insert(product)
            val string = Json.encodeToString(product)
            Log.i("data", string)
            broadcast.putExtra("product", string)
            sendBroadcast(broadcast)
            Log.i("br", broadcast.toString())
        }
        binding.bt2.setOnClickListener {
            (binding.rv1.adapter as MyAdapter).clearProducts()
        }

    }

    override fun onResume() {
        super.onResume()

        val productViewModel = ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ProductViewModel::class.java)

        val extras = intent.extras
        val productId = extras?.getLong("productId")
        if (productId != null) {
            val position = (binding.rv1.adapter as MyAdapter).getItemPosition(productViewModel.get(productId))
            myLayoutManager.scrollToPositionWithOffset(position, 0)
        }
        binding.rv1.layoutManager = myLayoutManager
    }
}