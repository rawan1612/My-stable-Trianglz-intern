package com.example.marketplace.view.productsList

import com.example.marketplace.model.DataModelInterface
  class OnClickListenerProduct(val clickListener:(response : DataModelInterface.ProductInfo)-> Unit) {
    fun onClick(response : DataModelInterface.ProductInfo) = clickListener(response)
}