package com.example.marketplace.viewModel.ProductDetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketplace.model.DataModelInterface
import com.example.marketplace.model.RepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GetProductDetailsViewModel(private val repository: RepositoryInterface): ViewModel()  {
    private val filteredProductListMutableLiveData : MutableLiveData<List<DataModelInterface.Response>> = MutableLiveData()
    val filteredProductListLiveData : LiveData<List<DataModelInterface.Response>> = filteredProductListMutableLiveData

    fun getAllProduct() {
        Log.i("TAG", "Load  Data: ")
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getProducts()
            if(response.isNotEmpty()) {
                filteredProductListMutableLiveData.postValue(response)
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
            val response = repository.getProductsByCategory(selectedCategory)
            if (response!=null) {
                filteredProductListMutableLiveData.postValue(response)
            } else {
                Log.i("TAG", "getProductsByCategory: error ")
            }
        }

    }

}