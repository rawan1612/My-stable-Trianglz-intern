package com.example.marketplace.view.productDetails

import com.example.marketplace.model.DataModelInterface

interface IIntentHandle{
     fun makeCall(phoneNumber: String)
     fun sendMail(email: String)
}