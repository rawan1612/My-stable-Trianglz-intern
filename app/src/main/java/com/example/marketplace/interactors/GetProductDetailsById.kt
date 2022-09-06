package com.example.marketplace.interactors

import com.example.marketplace.data.IProductsRepository
import com.example.marketplace.domain.model.DataModelInterface

class GetProductDetailsById (var repo: IProductsRepository) {
    suspend operator fun invoke (id : Int): DataModelInterface.ProductDetail {
        return repo.getProductDetails(id)
    }
}