package com.example.marketplace.model

interface RepositoryInterface {
    suspend fun getProducts():List<Response>
    suspend fun getProductsByCategory(selectedCategory :String):List<Response>

}