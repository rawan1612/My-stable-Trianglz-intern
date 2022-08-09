package com.example.marketplace.view.productsList

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marketplace.databinding.ImageListItemBinding
import com.example.marketplace.databinding.ItemProductListBinding
import com.example.marketplace.databinding.ItemSimilarProductsBinding
import com.example.marketplace.model.DataModelInterface

class SimilarItemListRecyclerViewAdapter(private val context: Context , private val onClickListener: OnClickListenerProduct) : RecyclerView.Adapter<SimilarItemListRecyclerViewAdapter.ViewHolder>() {
    private var productList = mutableListOf<DataModelInterface.Response>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SimilarItemListRecyclerViewAdapter.ViewHolder {
        return ViewHolder(
            ItemSimilarProductsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    fun setProductList(newProductList: List<DataModelInterface.Response>) {
        val diffUtil = ProductItemDiffUtil(productList,newProductList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        productList.clear()
        productList.addAll(newProductList)
        diffResult.dispatchUpdatesTo(this )
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
        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }
    inner class ViewHolder(binding: ItemSimilarProductsBinding) : RecyclerView.ViewHolder(binding.root) {
        val img : ImageView = binding.similarItemImage
        val itemName : TextView = binding.similarItemName
        val itemPrice : TextView = binding.similarItemPrice
    }
}