package com.example.marketplace.model

interface RepositoryInterface {
    suspend fun getProducts():List<DataModelInterface.Response>
    suspend fun getProductsByCategory(selectedCategory :String):List<DataModelInterface.Response>

}