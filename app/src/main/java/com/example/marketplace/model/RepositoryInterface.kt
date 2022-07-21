package com.example.marketplace.model

interface RepositoryInterface {
    suspend fun getProducts():List<Response>
}