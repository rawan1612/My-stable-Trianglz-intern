package com.example.marketplace.localSource

import com.example.marketplace.model.DataModelInterface
import com.example.marketplace.model.FakeData

class Client : LocalSource {
    companion object {
        private var instance: Client? = null
        fun getInstance(): Client {
            return instance ?: Client()
        }
    }

    private var allProducts: List<DataModelInterface.Response> = listOf()
    private var filterdList: List<DataModelInterface.Response> = emptyList()
    override suspend fun getProducts(): List<DataModelInterface.Response> {
        allProducts = FakeData.response
        return allProducts
    }

    override suspend fun getProductsByCategory(selectedCategory: String): List<DataModelInterface.Response> {
        if (selectedCategory == "trading") {
                filterdList = FakeData.horseTradingList
        }
        if (selectedCategory == "usedEqu") {
                filterdList = FakeData.usedEquipmentList
        }
        return filterdList
    }
}