package com.example.helloworld

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.example.helloworld.databinding.ListElementBinding

class MyAdapter(private val productViewModel: ProductViewModel, private val context: Context) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    private var products = mutableListOf<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ListElementBinding.inflate(inflater)

        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        val bold = if (sp.getBoolean(context.getString(R.string.bold), false)) context.getString(R.string.bold) else ""
        val italics = if (sp.getBoolean(context.getString(R.string.italics), false)) context.getString(R.string.italics) else ""

        when (bold + italics) {
            context.getString(R.string.bold) -> overrideFonts(view.rvLl1, Typeface.BOLD)
            context.getString(R.string.italics) -> overrideFonts(view.rvLl1, Typeface.ITALIC)
            "bolditalics" -> overrideFonts(view.rvLl1, Typeface.BOLD_ITALIC)
            else -> {
                overrideFonts(view.rvLl1, Typeface.NORMAL)
            }
        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyAdapter.ViewHolder, position: Int) {
        val currentProduct = products[position]
        holder.binding.rvTv1.setText(currentProduct.name)
        holder.binding.rvTv2.setText(currentProduct.price.toString())
        holder.binding.rvTv3.setText(currentProduct.amount.toString())
        holder.binding.rvCb1.isChecked = currentProduct.bought
        holder.binding.rvBt1.setOnClickListener {
            currentProduct.name = holder.binding.rvTv1.text.toString()
            currentProduct.price = holder.binding.rvTv2.text.toString().toDouble()
            currentProduct.amount = holder.binding.rvTv3.text.toString().toLong()
            currentProduct.bought = holder.binding.rvCb1.isChecked
            productViewModel.update(currentProduct)
        }
        holder.binding.rvBt2.setOnClickListener {
            productViewModel.delete(currentProduct)
            products.remove(currentProduct)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = products.size

    inner class ViewHolder(val binding: ListElementBinding) : RecyclerView.ViewHolder(binding.root)

    fun setProducts() {
        this.products = productViewModel.allProducts
        notifyDataSetChanged()
    }

    fun addProduct(product: Product) {
        productViewModel.insert(product)
        notifyDataSetChanged()
    }

    fun clearProducts() {
        //productViewModel.clear()
        notifyDataSetChanged()
    }

    fun overrideFonts(v : View, style: Int) {
        try {
            if (v is LinearLayout) {
                val vg = v
                for (i in 0 until vg.childCount) {
                    val child = vg.getChildAt(i)
                    overrideFonts(child, style)
                }
            } else if (v is EditText) {
                v.setTypeface(null, style)
            }
        } catch (e: Exception) {
        }
    }
}