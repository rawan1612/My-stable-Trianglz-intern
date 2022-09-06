package com.example.marketplace.presentation.ProductDetailsViewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketplace.R
import com.example.marketplace.domain.model.DataModelInterface
import com.example.marketplace.data.IProductsRepository
import com.example.marketplace.domain.model.Interactors
import com.example.marketplace.domain.model.ProductCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class GetProductDetailsViewModel(private val interactor: Interactors, val context: Context): ViewModel()  {
private val productDetailsMutableLiveData  : MutableLiveData<ArrayList<DataModelInterface>?> = MutableLiveData()
val productDetailsLiveData : LiveData<ArrayList<DataModelInterface>?> = productDetailsMutableLiveData
     private val objectsList = ArrayList<DataModelInterface>()
    private var similarData: DataModelInterface.SimilarProducts = DataModelInterface.SimilarProducts(similarProducts = null)

    fun getProductDetailsById(id : Int) {
        val theMap = HashMap<String, String>()
        var productWithEditDate : DataModelInterface.ProductInfo? = null
        viewModelScope.launch(Dispatchers.IO){
            val response = interactor.getProductDetailsById?.invoke(id)
            getProductsByCategory(response?.productInfo?.category!!,id)
            objectsList.clear()
            if (response!=null){
                productWithEditDate = response.productInfo.copy(expiredDate = response.productInfo.expiredDate?.let {
                    convertDate(
                        it)
                })


                objectsList.add(productWithEditDate!!)
                response.seller?.let { objectsList.add(it) }
                response.place?.let { objectsList.add(it) }
                if (response.horseInfo?.name!=null){
                    theMap[context.getString(R.string.Horse_Name)] = response.horseInfo.name
                }
                if (response.horseInfo?.color!=null){
                    theMap[context.getString(R.string.Horse_Color)] = response.horseInfo.color
                }
                if (response.horseInfo?.sexType!=null){
                    theMap[context.getString(R.string.Horse_Sex_type)] = response.horseInfo.sexType
                }
                if (response.horseInfo?.breed!=null){
                    theMap[context.getString(R.string.Horse_Breed)] = response.horseInfo.breed
                }
                if (response.horseInfo?.gender!=null){
                    theMap[context.getString(R.string.Horse_Gender)] = response.horseInfo.gender
                }
                if (response.horseInfo?.DOB!=null){
                    theMap[context.getString(R.string.DOB)] = response.horseInfo.DOB
                }
                if (response.horseInfo?.strain!=null){
                    theMap[context.getString(R.string.Horse_Strain)] = response.horseInfo.strain
                }
                theMap.forEach { key, value ->
                    objectsList.add(DataModelInterface.HorseInfoResponse(key = key,value = value))
                }
                if(!similarData.similarProducts.isNullOrEmpty()) {
                    objectsList.add(similarData)
                }
                productDetailsMutableLiveData.postValue(objectsList)
            }else{
                Log.i("TAG", "getProductDetailsById: error")
            }
        }
    }
    private fun getProductsByCategory(selectedCategory :ProductCategory,itemId:Int){
        viewModelScope.launch(Dispatchers.IO){
            val response = interactor.getProductsByCategory?.invoke(selectedCategory)
            if (!response.isNullOrEmpty()) {
                similarData.similarProducts  = response.filter { it->it.productId!=itemId }

            } else {
                Log.i("TAG", "getProductsByCategory: error ")
            }
        }
    }

    fun convertDate(date : String):String{
        val d: ZonedDateTime = ZonedDateTime.parse(date)
        return  d.dayOfMonth.toString()+" "+d.month.name.lowercase().replaceFirstChar { it.uppercase() }
    }
}

