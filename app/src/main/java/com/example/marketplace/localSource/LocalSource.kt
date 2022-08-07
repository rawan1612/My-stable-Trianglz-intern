package com.example.marketplace.localSource

import com.example.marketplace.model.DataModelInterface

interface LocalSource {
    suspend fun getProducts():List<DataModelInterface.Response>
    suspend fun getProductsByCategory(selectedCategory :String):List<DataModelInterface.Response>

}