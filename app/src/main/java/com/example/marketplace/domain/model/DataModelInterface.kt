package com.example.marketplace.domain.model

interface DataModelInterface {
    data class ProductDetail(
        val productInfo : ProductInfo? = null,
        val place : Place? = null,
        val seller : Owner? = null,
        val horseInfo: HorseInfo? = null,
    ):  DataModelInterface

    data class ProductInfo(
        var productId : Int? = 0,
        val country : String? = null,
        val itemName : String? = null,
        val price : Double? = null,
        val currency : String? = null,
        val thumbnail : String? = null,
        val images : List<String>? = listOf(),
        val description : String? = null,
        val isAvailable : Boolean? = false,
        var category : ProductCategory?,
        val expiredDate : String? = null,
    ): DataModelInterface

    data class Place(
        val latitude : Double? = null,
        val longitude : Double? = null,
        val address: String? =null
    ): DataModelInterface

    data class Owner(
        val profileImg : String? = null,
        val name : String? = null,
        val phoneNumber : Long? = null,
        val email : String? = null
    ): DataModelInterface

    data class HorseInfo(
        val name : String? = null,
        val color : String? = null,
        val sexType : String? = null,
        val gender : String? = null,
        val DOB : String? = null ,
        val breed : String? = null,
        val strain : String? = null
    ): DataModelInterface

    data class SimilarProducts(
        var similarProducts : List<ProductInfo>? = null
    ): DataModelInterface
    data class HorseInfoResponse(
        val key:String? = null,
        val value:String? = null

    ) : DataModelInterface
}