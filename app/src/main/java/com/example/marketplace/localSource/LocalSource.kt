package com.example.marketplace.localSource

import com.example.marketplace.model.Response

interface LocalSource {
    suspend fun getProducts():List<Response>
}