package com.example.marketplace.model

import android.os.Parcelable
import java.util.*

import kotlinx.android.parcel.Parcelize
@Parcelize
data class Response(
    val productId : Double = 0.0,
    val country : String = "",
    val itemName : String = "",
    val price : Double = 0.0,
    val currency : String = "",
    val thumbnail : String = "",
    val images : List<String> = listOf(),
    val description : String = "",
    val place : Place ,
    val seller : Owner,
    val horseInfo: HorseInfo? = null,
    val isAvailable : Boolean = false,
    val category : String,
    val expiredDate : Date,
): Parcelable
@Parcelize
data class Place(
    val latitude : Double = 0.0,
    val longitude : Double = 0.0,
): Parcelable

@Parcelize
data class Owner(
    val name : String = "",
    val phoneNumber : Long = 0,
    val email : String = ""
): Parcelable

@Parcelize
data class HorseInfo(
    val hName : String ="",
    val hColor : String = "",
    val hSexType : String = "",
    val hGender : String = "",
    val hDOB : Date ,
    val hBreed : String = "",
    val hStrain : String = ""
): Parcelable
