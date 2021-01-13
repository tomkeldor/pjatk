package com.example.helloworld

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.helloworld.databinding.ShopListElementBinding

class ShopAdapter(private val shopViewModel: ShopViewModel) : RecyclerView.Adapter<ShopAdapter.ViewHolder>() {

    private var shops = mutableListOf<Shop>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ShopListElementBinding.inflate(inflater)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopAdapter.ViewHolder, position: Int) {
        val currentShop = shops[position]
        holder.binding.rvTv1.text = currentShop.name
        holder.binding.rvTv2.text = currentShop.desc
        holder.binding.rvTv3.setText(currentShop.coords)
        holder.binding.rvEt1.setText(currentShop.radius)
        holder.binding.rvBt1.setOnClickListener {
            currentShop.name = holder.binding.rvTv1.text.toString()
            currentShop.desc = holder.binding.rvTv2.text.toString()
            currentShop.radius = holder.binding.rvEt1.text.toString()
            shopViewModel.update(currentShop)
        }
        holder.binding.rvBt2.setOnClickListener {
            currentShop.favorite = true
            shopViewModel.update(currentShop)
        }
    }

    override fun getItemCount(): Int = shops.size

    inner class ViewHolder(val binding: ShopListElementBinding) : RecyclerView.ViewHolder(binding.root)

    fun setShops() {
        this.shops = shopViewModel.allShops
        notifyDataSetChanged()
    }

    fun addShop(shop: Shop) {
        shopViewModel.insert(shop)
        notifyDataSetChanged()
    }

}