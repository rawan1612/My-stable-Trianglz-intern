package com.example.marketplace.presentation.productsListView

import androidx.recyclerview.widget.DiffUtil
import com.example.marketplace.domain.model.DataModelInterface

class ProductItemDiffUtil(
) : DiffUtil.ItemCallback<DataModelInterface.ProductInfo>() {
    override fun areItemsTheSame(
        oldItem: DataModelInterface.ProductInfo,
        newItem: DataModelInterface.ProductInfo
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: DataModelInterface.ProductInfo,
        newItem: DataModelInterface.ProductInfo
    ): Boolean {
        return oldItem.productId== newItem.productId
    }


}