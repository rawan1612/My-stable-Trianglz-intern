package com.example.marketplace.viewModel.ProductsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.marketplace.model.IProductsRepository

class GetProductsViewModelFactory(private val repo: IProductsRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(GetProductViewModel::class.java)) {
            GetProductViewModel(repo) as T
        } else {
            throw IllegalArgumentException("GetProductsViewModelFactory Class not found")
        }
    }
}
