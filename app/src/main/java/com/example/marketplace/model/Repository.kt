package com.example.marketplace.model

import android.content.Context
import com.example.marketplace.localSource.LocalSource

class Repository private constructor(var localSource: LocalSource):RepositoryInterface{
    companion object{
        private var instance: Repository? = null
        fun getInstance(localSource: LocalSource
        ): Repository{
            return instance?: Repository(
                localSource)
        }
    }
    override suspend fun getProducts(): List<Response> {
        return localSource.getProducts()
    }
    override suspend fun getProductsByCategory(selectedCategory :String):List<Response>{
        return localSource.getProductsByCategory(selectedCategory)
    }
}