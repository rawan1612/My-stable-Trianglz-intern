package com.example.marketplace.model

import com.example.marketplace.localSource.IProductLocalSource

class IProductsRepositoryImpl private constructor(var localSource: IProductLocalSource):IProductsRepository{
    companion object{
        private var instance: IProductsRepositoryImpl? = null
        fun getInstance(localSource: IProductLocalSource
        ): IProductsRepositoryImpl{
            return instance?: IProductsRepositoryImpl(
                localSource)
        }
    }
    override suspend fun getProducts(): List<DataModelInterface.ProductInfo> {
        return localSource.getProducts()
    }
    override suspend fun getProductsByCategory(selectedCategory :ProductCategory):List<DataModelInterface.ProductInfo>{
        return localSource.getProductsByCategory(selectedCategory)
    }

    override suspend fun getProductDetails(id : Int): DataModelInterface.ProductDetail {
        return localSource.getProductsDetails(id)
    }

}