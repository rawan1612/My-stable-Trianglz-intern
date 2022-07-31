package com.example.marketplace.view

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marketplace.R
import com.example.marketplace.databinding.FragmentItemBinding
import com.example.marketplace.model.Response

class MyItemRecyclerViewAdapter(private val context: Context) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {
    private var productList = mutableListOf<Response>()
    fun setProductList(newProductList: List<Response>) {
        val diffUtil = MyDiffUtil(productList,newProductList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        productList.clear()
        productList.addAll(newProductList)
        diffResult.dispatchUpdatesTo(this )
    }
    fun updateAfterFilter(filteredList: List<Response>){
        productList = filteredList.toMutableList()
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = productList[position]
        Glide.with(context)
            .load(item.thumbnail)
            .into(holder.img)
        holder.idView.text = item.itemName
        holder.contentView.text = context.getString(R.string.currencyAndPrice,item.currency,item.price)
    }

    override fun getItemCount(): Int = productList.size

    inner class ViewHolder(binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content
        val img : ImageView = binding.itemImage

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}