package com.example.marketplace.presentation.productDetailsView

import androidx.recyclerview.widget.DiffUtil

class ProductImagesDiffUtil : DiffUtil.ItemCallback<String>()
{
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
       return oldItem  == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem:String): Boolean {
       return oldItem == newItem
    }


}