package com.example.marketplace.view.productDetails

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marketplace.R
import com.example.marketplace.databinding.ImageListItemBinding


class ProductItemImageRecyclerViewAdapter(private val context: Context): RecyclerView.Adapter<ProductItemImageRecyclerViewAdapter.ViewHolder>() {
    private var productsList = mutableListOf<String>()

    fun setProductsList(newProductList: List<String>) {
        val diffUtil = ProductImagesDiffUtil(productsList,newProductList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        productsList.clear()
        productsList.addAll(newProductList)
        diffResult.dispatchUpdatesTo(this )
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(ImageListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ProductItemImageRecyclerViewAdapter.ViewHolder, position: Int) {
        val item = productsList[position]
        if(productsList.size > 1){
            holder.itemView.layoutParams.width = context.resources.displayMetrics.heightPixels / 3 + 100
        }
        Glide.with(context)
            .load(item)
            .placeholder(R.drawable.product_placeholder)
            .dontAnimate()
           .fitCenter()
            .into(holder.img)
    }

    override fun getItemCount(): Int {
        return productsList.size
    }
    inner class ViewHolder(binding: ImageListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val img : ImageView = binding.productImg
    }
}