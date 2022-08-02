package com.example.marketplace.view.productsList

import androidx.recyclerview.widget.DiffUtil
import com.example.marketplace.model.Response

class ProductItemDiffUtil(private val oldList : List<Response>,
                          private val newList : List<Response>
) : DiffUtil.Callback() {

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].productId == newList[newItemPosition].productId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
       return when {
           oldList[oldItemPosition].productId != newList[newItemPosition].productId -> { false }
           oldList[oldItemPosition].price != newList[newItemPosition].price -> { false }
           oldList[oldItemPosition].currency != newList[newItemPosition].currency -> { false }
           else -> true
       }
    }
}