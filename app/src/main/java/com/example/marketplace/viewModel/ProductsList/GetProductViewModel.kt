package com.example.marketplace.viewModel.ProductsList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketplace.model.DataModelInterface
import com.example.marketplace.model.RepositoryInterface
import kotlinx.coroutines.*

class GetProductViewModel(private val repository: RepositoryInterface): ViewModel() {
    private val allProductListMutableLiveData : MutableLiveData<List<DataModelInterface.Response>> = MutableLiveData()
    val allProductListLiveData : LiveData<List<DataModelInterface.Response>> = allProductListMutableLiveData
    private val filteredProductListMutableLiveData : MutableLiveData<List<DataModelInterface.Response>> = MutableLiveData()
    val filteredProductListLiveData : LiveData<List<DataModelInterface.Response>> = filteredProductListMutableLiveData
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
    fun getProductsByCategory(selectedCategory :String){
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