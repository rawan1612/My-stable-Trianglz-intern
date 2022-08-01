package com.example.marketplace.view.productDetails

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marketplace.databinding.ImageListItemBinding


class ItemImageRecyclerViewAdapter(private val context: Context): RecyclerView.Adapter<ItemImageRecyclerViewAdapter.ViewHolder>() {
    private var productsList = mutableListOf<String>()

    fun setProductsList(newProductList: List<String>) {
        val diffUtil = DiffUtilImages(productsList,newProductList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        productsList.clear()
        productsList.addAll(newProductList)
        diffResult.dispatchUpdatesTo(this )
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemImageRecyclerViewAdapter.ViewHolder {
        return ViewHolder(
           ImageListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: ItemImageRecyclerViewAdapter.ViewHolder, position: Int) {
        val item = productsList[position]
        Glide.with(context)
            .load(item)
            .into(holder.img)
    }

    override fun getItemCount(): Int {
        return productsList.size
    }
    inner class ViewHolder(binding: ImageListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val img : ImageView = binding.productImg

    }
}