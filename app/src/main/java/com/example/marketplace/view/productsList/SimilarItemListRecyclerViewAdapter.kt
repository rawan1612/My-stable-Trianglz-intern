package com.example.marketplace.view.productsList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marketplace.databinding.ImageListItemBinding
import com.example.marketplace.databinding.ItemProductListBinding
import com.example.marketplace.model.DataModelInterface

class SimilarItemListRecyclerViewAdapter(private val context: Context) : RecyclerView.Adapter<SimilarItemListRecyclerViewAdapter.ViewHolder>() {
    private var productList = mutableListOf<DataModelInterface.Response>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SimilarItemListRecyclerViewAdapter.ViewHolder {
        return ViewHolder(
            ItemProductListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: SimilarItemListRecyclerViewAdapter.ViewHolder,
        position: Int
    ) {
        val item = productList[position]
        Glide.with(context)
            .load(item.productInfo?.thumbnail)
            .into(holder.img)
        holder.itemName.text = item.productInfo?.itemName
        holder.itemPrice.text = item.productInfo?.currency +" "+item.productInfo?.price

    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
    inner class ViewHolder(binding: ItemProductListBinding) : RecyclerView.ViewHolder(binding.root) {
        val img : ImageView = binding.itemImage
        val itemName : TextView = binding.itemName
        val itemPrice : TextView = binding.itemPrice
    }
}