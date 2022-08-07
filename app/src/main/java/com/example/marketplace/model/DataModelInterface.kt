package com.example.marketplace.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

interface DataModelInterface {
    @Parcelize
    data class Response(
        val productInfo : ProductInfo? = null,
        val place : Place? = null,
        val seller : Owner? = null,
        val horseInfo: HorseInfo? = null,
    ): Parcelable , DataModelInterface

    @Parcelize
    data class ProductInfo(
        val productId : Double = 0.0,
        val country : String = "",
        val itemName : String = "",
        val price : Double = 0.0,
        val currency : String = "",
        val thumbnail : String = "",
        val images : List<String> = listOf(),
        val description : String = "",
        val isAvailable : Boolean = false,
        val category : String,
        val expiredDate : Date,
    ): Parcelable , DataModelInterface

    @Parcelize
    data class Date(
        val day : Int,
        val month: Int,
        val year: Int
    ): Parcelable , DataModelInterface

    @Parcelize
    data class Place(
        val latitude : Double = 0.0,
        val longitude : Double = 0.0,
        val address: String =""
    ): Parcelable , DataModelInterface

    @Parcelize
    data class Owner(
        val profileImg : String? = null,
        val name : String = "",
        val phoneNumber : Long = 0,
        val email : String = ""
    ): Parcelable , DataModelInterface

    @Parcelize
    data class HorseInfo(
        val hName : String ="",
        val hColor : String = "",
        val hSexType : String = "",
        val hGender : String = "",
        val hDOB : String = "" ,
        val hBreed : String = "",
        val hStrain : String = ""
    ): Parcelable , DataModelInterface
}