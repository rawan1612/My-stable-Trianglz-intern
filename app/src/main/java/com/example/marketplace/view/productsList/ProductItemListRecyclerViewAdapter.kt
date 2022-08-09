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
import com.example.marketplace.databinding.ItemProductListBinding
import com.example.marketplace.model.DataModelInterface

class ProductItemListRecyclerViewAdapter(private val context: Context, private val onClickListener: OnClickListenerProduct) : RecyclerView.Adapter<ProductItemListRecyclerViewAdapter.ViewHolder>() {
    private var productList = mutableListOf<DataModelInterface.Response>()
    fun setProductList(newProductList: List<DataModelInterface.Response>) {
        val diffUtil = ProductItemDiffUtil(productList,newProductList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        productList.clear()
        productList.addAll(newProductList)
        diffResult.dispatchUpdatesTo(this )
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemProductListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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

    override fun getItemCount(): Int = productList.size

    inner class ViewHolder(binding: ItemProductListBinding) : RecyclerView.ViewHolder(binding.root) {
        val itemName: TextView = binding.itemName
        val itemPrice: TextView = binding.itemPrice
        val img : ImageView = binding.itemImage

    }

}