package com.example.marketplace.model

import java.util.*

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
)
data class Place(
    val latitude : Double = 0.0,
    val longitude : Double = 0.0,
)

data class Owner(
    val name : String = "",
    val phoneNumber : Long = 0,
    val email : String = ""
)

data class HorseInfo(
    val hName : String ="",
    val hColor : String = "",
    val hSexType : String = "",
    val hGender : String = "",
    val hDOB : Date ,
    val hBreed : String = "",
    val hStrain : String = ""
)
