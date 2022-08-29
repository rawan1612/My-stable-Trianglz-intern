package com.example.marketplace.viewModel.ProductDetails

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.marketplace.model.IProductsRepository

class GetProductDetailsViewModelFactory (private val repo: IProductsRepository,private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(GetProductDetailsViewModel::class.java)) {
            GetProductDetailsViewModel(repo,context) as T
        } else {
            throw IllegalArgumentException("GetProductDetailsViewModelFactory Class not found")
        }
    }
}
