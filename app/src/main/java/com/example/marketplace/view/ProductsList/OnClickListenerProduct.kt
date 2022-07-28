package com.example.marketplace.view.ProductsList

import com.example.marketplace.model.Response

class OnClickListenerProduct(val clickListener:(response : Response)-> Unit) {
    fun onClick(response : Response) = clickListener(response)
}