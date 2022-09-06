package com.example.marketplace.interactors

import android.util.Log
import com.example.marketplace.data.IProductsRepository
import com.example.marketplace.domain.model.DataModelInterface

class GetAllProducts (var repo: IProductsRepository){
    suspend operator fun invoke(): List<DataModelInterface.ProductInfo> {
        return repo.getProducts()
    }
}