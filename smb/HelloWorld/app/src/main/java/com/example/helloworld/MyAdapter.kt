package com.example.helloworld

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.helloworld.databinding.ListElementBinding

class MyAdapter(val productViewModel: ProductViewModel) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    private var products = emptyList<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ListElementBinding.inflate(inflater)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyAdapter.ViewHolder, position: Int) {
        val currentProduct = products[position]
        holder.binding.rvTv1.setText(currentProduct.name)
        holder.binding.rvTv2.setText(currentProduct.price.toString())
        holder.binding.rvTv3.setText(currentProduct.amount.toString())
        holder.binding.rvCb1.isChecked = currentProduct.isBought
        holder.binding.rvBt1.setOnClickListener {
            currentProduct.name = holder.binding.rvTv1.text.toString()
            currentProduct.price = holder.binding.rvTv2.text.toString().toDouble()
            currentProduct.amount = Integer.parseInt(holder.binding.rvTv3.text.toString())
            currentProduct.isBought = holder.binding.rvCb1.isChecked
            productViewModel.update(currentProduct)
            //notifyDataSetChanged()
        }
        holder.binding.rvBt2.setOnClickListener {
            productViewModel.delete(currentProduct)
        }
    }

    override fun getItemCount(): Int = products.size

    inner class ViewHolder(val binding: ListElementBinding) : RecyclerView.ViewHolder(binding.root)

    fun setProducts(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }

    public fun addProduct(product: Product) {
        productViewModel.insert(product)
        notifyDataSetChanged()
    }

    public fun clearProducts() {
        productViewModel.clear()
        notifyDataSetChanged()
    }
}