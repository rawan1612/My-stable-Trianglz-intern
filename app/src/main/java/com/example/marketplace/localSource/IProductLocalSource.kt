package com.example.marketplace.localSource

import com.example.marketplace.model.DataModelInterface
import com.example.marketplace.model.ProductCategory

interface IProductLocalSource {
    suspend fun getProducts():List<DataModelInterface.ProductInfo>
    suspend fun getProductsByCategory(selectedCategory :ProductCategory):List<DataModelInterface.ProductInfo>
    suspend fun getProductsDetails(id : Int): DataModelInterface.ProductDetail

}