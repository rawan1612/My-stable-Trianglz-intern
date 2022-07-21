package com.example.marketplace.model

import android.content.Context
import android.util.Log
import com.example.marketplace.localSource.LocalSource

class Repository private constructor(var localSource: LocalSource,var context: Context):RepositoryInterface{
    companion object{
        private var instance: Repository? = null
        fun getInstance(localSource: LocalSource,
                        context: Context
        ): Repository{
            return instance?: Repository(
                localSource,context)
        }
    }
    override suspend fun getProducts(): List<Response> {
        return localSource.getProducts()
    }
}