package com.example.marketplace.presentation.ProductDetailsViewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.marketplace.data.IProductsRepository
import com.example.marketplace.domain.model.Interactors

class GetProductDetailsViewModelFactory (private val interactor: Interactors, private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(GetProductDetailsViewModel::class.java)) {
            GetProductDetailsViewModel(interactor,context) as T
        } else {
            throw IllegalArgumentException("GetProductDetailsViewModelFactory Class not found")
        }
    }
}
