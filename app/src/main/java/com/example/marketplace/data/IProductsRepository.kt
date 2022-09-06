package com.example.marketplace.data

import com.example.marketplace.domain.model.DataModelInterface
import com.example.marketplace.domain.model.ProductCategory

interface IProductsRepository {
    suspend fun getProducts():List<DataModelInterface.ProductInfo>
    suspend fun getProductsByCategory(selectedCategory : ProductCategory):List<DataModelInterface.ProductInfo>
    suspend fun getProductDetails(id: Int): DataModelInterface.ProductDetail

}