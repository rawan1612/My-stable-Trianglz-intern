package com.example.marketplace.presentation.ProductsListViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.marketplace.domain.model.Interactors

class GetProductsViewModelFactory(private val interactors: Interactors): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(GetProductsViewModel::class.java)) {
            GetProductsViewModel(interactors) as T
        } else {
            throw IllegalArgumentException("GetProductsViewModelFactory Class not found")
        }
    }
}
