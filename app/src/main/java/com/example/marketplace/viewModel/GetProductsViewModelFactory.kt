package com.example.marketplace.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.marketplace.model.RepositoryInterface

class GetProductsViewModelFactory(private val repo: RepositoryInterface): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(GetProductViewModel::class.java)) {
            GetProductViewModel(repo) as T
        } else {
            throw IllegalArgumentException("AddToFavoriteViewModel Class not found")
        }
    }
}
