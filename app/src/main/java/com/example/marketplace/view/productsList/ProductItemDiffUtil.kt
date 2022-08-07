package com.example.marketplace.view.productsList

import androidx.recyclerview.widget.DiffUtil
import com.example.marketplace.model.DataModelInterface

class ProductItemDiffUtil(private val oldList : List<DataModelInterface.Response>,
                          private val newList : List<DataModelInterface.Response>
) : DiffUtil.Callback() {

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].productInfo?.productId == newList[newItemPosition].productInfo?.productId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
       return when {
           oldList[oldItemPosition].productInfo?.productId != newList[newItemPosition].productInfo?.productId -> { false }
           oldList[oldItemPosition].productInfo?.price != newList[newItemPosition].productInfo?.price -> { false }
           oldList[oldItemPosition].productInfo?.currency != newList[newItemPosition].productInfo?.currency -> { false }
           else -> true
       }
    }
}