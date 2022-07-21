package com.example.marketplace.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketplace.model.RepositoryInterface
import com.example.marketplace.model.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GetProductViewModel(private val repository: RepositoryInterface): ViewModel() {
    private val allProductListMutableLiveData : MutableLiveData<List<Response>> = MutableLiveData()
    val allProductListLiveData : LiveData<List<Response>> = allProductListMutableLiveData

    init {
        getAllProduct()
    }

    fun getAllProduct() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getProducts()
            withContext(Dispatchers.Main) {
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
    }
}