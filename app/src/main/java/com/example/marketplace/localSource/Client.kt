package com.example.marketplace.localSource

import android.app.Activity
import android.util.Log
import com.example.marketplace.R
import com.example.marketplace.model.FakeData
import com.example.marketplace.model.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Client : LocalSource {
    companion object {
        private var instance: Client? = null
        fun getInstance(): Client {
            return instance ?: Client()
        }
    }

    private var allProducts: List<Response> = listOf()
    private var filterdList: List<Response> = emptyList()
    override suspend fun getProducts(): List<Response> {
        allProducts = FakeData.response
        return allProducts
    }

    override suspend fun getProductsByCategory(selectedCategory: String): List<Response> {
        if (selectedCategory == "trading") {
                filterdList = FakeData.horseTradingList
        }
        if (selectedCategory == "usedEqu") {
                filterdList = FakeData.usedEquipmentList
        }
        return filterdList
    }
}