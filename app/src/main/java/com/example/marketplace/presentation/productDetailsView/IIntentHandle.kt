package com.example.marketplace.presentation.productDetailsView

interface IIntentHandle{
     fun makeCall(phoneNumber: String)
     fun sendMail(email: String)
}