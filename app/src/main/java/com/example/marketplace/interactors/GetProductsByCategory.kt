package com.example.marketplace.interactors

import com.example.marketplace.data.IProductsRepository
import com.example.marketplace.domain.model.DataModelInterface
import com.example.marketplace.domain.model.ProductCategory

class GetProductsByCategory (var repo: IProductsRepository){
    suspend operator fun invoke (selectedCategory : ProductCategory):List<DataModelInterface.ProductInfo>{
        return repo.getProductsByCategory(selectedCategory)
    }
}