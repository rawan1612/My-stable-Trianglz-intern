package com.example.marketplace.localSource

import com.example.marketplace.model.DataModelInterface
import com.example.marketplace.model.FakeData
import com.example.marketplace.model.ProductCategory

class IProductLocalSourceImpl  private constructor(): IProductLocalSource  {
    companion object {
        private var instance: IProductLocalSourceImpl? = null
        fun getInstance(): IProductLocalSourceImpl {
            return instance ?: IProductLocalSourceImpl()
        }
    }

    private var allProducts: List<DataModelInterface.ProductInfo> = listOf()
    private var filterdList: List<DataModelInterface.ProductInfo> = emptyList()
    private var productDetails: List<DataModelInterface.ProductDetail> = listOf()
    private val fakeData : FakeData = FakeData()

    override suspend fun getProducts(): List<DataModelInterface.ProductInfo> {
        allProducts = fakeData.responseForList
        return allProducts
    }

    override suspend fun getProductsByCategory(selectedCategory: ProductCategory): List<DataModelInterface.ProductInfo> {
        filterdList = if (selectedCategory.name == ProductCategory.trading.name) {
            fakeData.horseTradingList
        } else{
            fakeData.usedEquipmentList
        }
        return filterdList
    }

    override suspend fun getProductsDetails(id : Int): DataModelInterface.ProductDetail {
        productDetails = fakeData.response
       val filterProductDetails = productDetails.filter { product ->
            product.productInfo?.productId == id
        }

        return filterProductDetails[0]
    }
}