package com.example.marketplace.model

interface IProductsRepository {
    suspend fun getProducts():List<DataModelInterface.ProductInfo>
    suspend fun getProductsByCategory(selectedCategory :ProductCategory):List<DataModelInterface.ProductInfo>
    suspend fun getProductDetails(id: Int):DataModelInterface.ProductDetail

}