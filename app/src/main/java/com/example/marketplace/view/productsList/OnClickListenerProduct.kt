package com.example.marketplace.view.productsList

import com.example.marketplace.model.DataModelInterface

class OnClickListenerProduct(val clickListener:(response : DataModelInterface.Response)-> Unit) {
    fun onClick(response : DataModelInterface.Response) = clickListener(response)
}