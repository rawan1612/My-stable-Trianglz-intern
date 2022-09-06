package com.example.marketplace.presentation.ProductsListViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketplace.domain.model.DataModelInterface
import com.example.marketplace.data.IProductsRepository
import com.example.marketplace.domain.model.Interactors
import com.example.marketplace.domain.model.ProductCategory
import kotlinx.coroutines.*

class GetProductsViewModel(private val interactors: Interactors): ViewModel() {
    private val productListMutableLiveData : MutableLiveData<List<DataModelInterface.ProductInfo>> = MutableLiveData()
    val productListLiveData : LiveData<List<DataModelInterface.ProductInfo>> = productListMutableLiveData

    fun getAllProduct() {
        Log.i("TAG", "Load  Data: ")
        viewModelScope.launch(Dispatchers.IO) {
            val response = interactors.getAllProducts?.invoke()
                delay(2000)
            if (response != null) {
                if(response.isNotEmpty()) {
                    productListMutableLiveData.postValue(response!!)
                } else{
                    Log.e(
                        "GetProductViewModel",
                        "Error fetching data in GetProductViewModel"
                    )
                }
            }
        }
    }
    fun getProductsByCategory(selectedCategory :ProductCategory){
        viewModelScope.launch(Dispatchers.IO){
                delay(1000)
                val response = interactors.getProductsByCategory?.invoke(selectedCategory)
                    if (response!=null) {
                        productListMutableLiveData.postValue(response!!)
                    } else {
                        Log.i("TAG", "getProductsByCategory: error ")
            }
        }

    }
}