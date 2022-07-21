package com.example.marketplace.localSource

import android.util.Log
import com.example.marketplace.model.FakeData
import com.example.marketplace.model.Response

class Client : LocalSource {
    companion object {
        private var instance: Client? = null
        fun getInstance(): Client {
            return instance ?: Client()
        }
    }
    var allProducts : List<Response> = listOf()
    override suspend fun getProducts(): List<Response> {
        allProducts = FakeData.response
        return allProducts
    }
}