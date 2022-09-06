package com.example.marketplace.data

import com.example.marketplace.domain.model.DataModelInterface
import com.example.marketplace.domain.model.ProductCategory

interface IProductLocalSource {
    suspend fun getProducts():List<DataModelInterface.ProductInfo>
    suspend fun getProductsByCategory(selectedCategory :ProductCategory):List<DataModelInterface.ProductInfo>
    suspend fun getProductsDetails(id : Int): DataModelInterface.ProductDetail

}