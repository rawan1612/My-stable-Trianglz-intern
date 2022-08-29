package com.example.marketplace.view.productsList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marketplace.R
import com.example.marketplace.databinding.ItemProductListBinding
import com.example.marketplace.model.DataModelInterface

class ProductsListAdapter(private val context: Context, private val onClickListener: OnClickListenerProduct) : ListAdapter<DataModelInterface.ProductInfo, ProductsListAdapter.ProductsListViewHolder>(ProductItemDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsListViewHolder {
        return ProductsListViewHolder(
            ItemProductListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductsListViewHolder, position: Int) {
        val item = getItem(position)
        holder.setData(item)
    }


    inner class ProductsListViewHolder(binding: ItemProductListBinding) : RecyclerView.ViewHolder(binding.root) {
        val itemName: TextView = binding.itemName
        val itemPrice: TextView = binding.itemPrice
        val img : ImageView = binding.itemImage
        fun setData(item : DataModelInterface.ProductInfo){
            Glide.with(context)
                .load(item.thumbnail)
                .placeholder(R.drawable.product_placeholder)
                .into(img)

            itemName.text = item.itemName
            itemPrice.text = item.currency +" "+item.price
            itemView.setOnClickListener {
                onClickListener.onClick(item)
            }
        }

    }

}