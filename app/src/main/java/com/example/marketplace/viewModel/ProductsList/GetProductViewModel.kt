package com.example.marketplace.viewModel.ProductsList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketplace.model.RepositoryInterface
import com.example.marketplace.model.Response
import kotlinx.coroutines.*

class GetProductViewModel(private val repository: RepositoryInterface): ViewModel() {
    private val allProductListMutableLiveData : MutableLiveData<List<Response>> = MutableLiveData()
    val allProductListLiveData : LiveData<List<Response>> = allProductListMutableLiveData
    private val filterdProductListMutableLiveData : MutableLiveData<List<Response>> = MutableLiveData()
    val filterdProductListLiveData : LiveData<List<Response>> = filterdProductListMutableLiveData
    init {
        getAllProduct()
    }

    fun getAllProduct() {
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
    fun getProductsByCategory(selectedCategory :String){
        viewModelScope.launch(Dispatchers.IO){
                delay(1000)
                val response = repository.getProductsByCategory(selectedCategory)
                    if (response!=null) {
                        filterdProductListMutableLiveData.postValue(response)
                    } else {
                        Log.i("TAG", "getProductsByCategory: error ")
            }
        }

    }
}