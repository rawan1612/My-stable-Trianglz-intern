package com.example.marketplace.view.productDetails

import androidx.recyclerview.widget.DiffUtil

class DiffUtilImages (private val oldList : List<String>,
                      private val newList : List<String>
): DiffUtil.Callback()
{
    override fun getOldListSize(): Int {
        return newList.size
    }

    override fun getNewListSize(): Int {
        return oldList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition] != newList[newItemPosition] -> { false }
            else -> true
        }
    }
}