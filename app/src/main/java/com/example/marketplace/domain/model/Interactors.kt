package com.example.marketplace.domain.model

import com.example.marketplace.interactors.GetAllProducts
import com.example.marketplace.interactors.GetProductDetailsById
import com.example.marketplace.interactors.GetProductsByCategory

data class Interactors(
    val getAllProducts: GetAllProducts? = null,
    val getProductsByCategory: GetProductsByCategory?= null,
    val getProductDetailsById: GetProductDetailsById?= null
)
