package com.example.marketplace.viewModel.ProductsList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketplace.model.DataModelInterface
import com.example.marketplace.model.IProductsRepository
import com.example.marketplace.model.ProductCategory
import kotlinx.coroutines.*

class GetProductViewModel(private val repository: IProductsRepository): ViewModel() {
    private val allProductListMutableLiveData : MutableLiveData<List<DataModelInterface.ProductInfo>> = MutableLiveData()
    val allProductListLiveData : LiveData<List<DataModelInterface.ProductInfo>> = allProductListMutableLiveData
    private val filteredProductListMutableLiveData : MutableLiveData<List<DataModelInterface.ProductInfo>> = MutableLiveData()
    val filteredProductListLiveData : LiveData<List<DataModelInterface.ProductInfo>> = filteredProductListMutableLiveData
    init {
        getAllProduct()
    }

    fun getAllProduct() {
        Log.i("TAG", "Load  Data: ")
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getProducts()
                delay(2000)
                if(response.isNotEmpty()) {
                    allProductListMutableLiveData.postValue(response)
                }
                else{
                    Log.e(
                        "GetProductViewModel",
                        "Error fetching data in GetProductViewModel"
                    )
                }
        }
    }
    fun getProductsByCategory(selectedCategory :ProductCategory){
        viewModelScope.launch(Dispatchers.IO){
                delay(1000)
                val response = repository.getProductsByCategory(selectedCategory)
                    if (response!=null) {
                        filteredProductListMutableLiveData.postValue(response)
                    } else {
                        Log.i("TAG", "getProductsByCategory: error ")
            }
        }

    }
}