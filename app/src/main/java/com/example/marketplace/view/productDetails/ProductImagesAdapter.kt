package com.example.marketplace.view.productDetails

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marketplace.R
import com.example.marketplace.databinding.ImageListItemBinding


class ProductImagesAdapter(): ListAdapter<String,ProductImagesAdapter.ProductImagesViewHolder>(ProductImagesDiffUtil()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductImagesViewHolder {
        return ProductImagesViewHolder(ImageListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ProductImagesAdapter.ProductImagesViewHolder, position: Int) {
        val item = getItem(position)
        holder.setData(item,holder.itemView.context)
        enhanceDesign(holder.itemView)
    }

    private fun enhanceDesign(view : View){
        if(itemCount > 1){
            view.layoutParams.width = view.context.resources.displayMetrics.heightPixels / 3 + 100
        }
    }
    inner class ProductImagesViewHolder(binding: ImageListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val img : ImageView = binding.productImg
        fun setData(item : String,context:Context){
            Glide.with(context)
                .load(item)
                .placeholder(R.drawable.product_placeholder)
                .dontAnimate()
                .centerCrop()
                .into(img)
        }

    }
}