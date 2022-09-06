package com.example.marketplace.presentation.productsListView

import com.example.marketplace.domain.model.DataModelInterface
  class OnClickListenerProduct(val clickListener:(response : DataModelInterface.ProductInfo)-> Unit) {
    fun onClick(response : DataModelInterface.ProductInfo) = clickListener(response)
}