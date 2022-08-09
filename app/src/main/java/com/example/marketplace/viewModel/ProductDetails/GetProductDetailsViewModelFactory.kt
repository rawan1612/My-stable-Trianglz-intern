package com.example.marketplace.viewModel.ProductDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.marketplace.model.RepositoryInterface
import com.example.marketplace.viewModel.ProductsList.GetProductViewModel

class GetProductDetailsViewModelFactory (private val repo: RepositoryInterface): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(GetProductDetailsViewModel::class.java)) {
            GetProductDetailsViewModel(repo) as T
        } else {
            throw IllegalArgumentException("GetProductDetailsViewModelFactory Class not found")
        }
    }
}
