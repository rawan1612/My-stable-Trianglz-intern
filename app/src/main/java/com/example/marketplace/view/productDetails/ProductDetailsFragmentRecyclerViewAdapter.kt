package com.example.marketplace.view.productDetails

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.R
import com.example.marketplace.model.DataModelInterface


class ProductDetailsFragmentRecyclerViewAdapter (context: Context,  fragmentManager: FragmentManager): RecyclerView.Adapter<ProductDetailsViewHolder>(){
    companion object {
        const val PRODUCT_INFO = 1
        const val LOCATION_INFO = 2
        const val PRODUCT_IMAGES = 3
        const val OWNER_INFO = 4
        const val HORSE_INFO = 5
    }
    val context : Context = context
    val fragmentManager = fragmentManager
    private val objects = mutableListOf<DataModelInterface>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductDetailsViewHolder {
        val layout = when (viewType) {
            PRODUCT_INFO -> R.layout.product_details_view
            LOCATION_INFO -> R.layout.location_info_view
            OWNER_INFO -> R.layout.owner_info_view
            HORSE_INFO -> R.layout.horse_info_view
            else -> throw IllegalArgumentException("Invalid type")
        }
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layout, parent, false)
        return ProductDetailsViewHolder(view,context,fragmentManager)
    }

    override fun onBindViewHolder(holder: ProductDetailsViewHolder, position: Int) {
        holder.bind(objects[position])
    }

    override fun getItemCount(): Int {
        Log.i("TAG", "getItemCount:${objects.size} ")
      return  objects.size
    }

    override fun getItemViewType(position: Int): Int {
        return  return when (objects[position]) {
            is DataModelInterface.Place -> LOCATION_INFO
            is DataModelInterface.Owner -> OWNER_INFO
            is DataModelInterface.HorseInfo   ->  HORSE_INFO
            else ->   PRODUCT_INFO
        }
    }
    fun setData(data: List<DataModelInterface>) {
        objects.apply {
            clear()
            addAll(data)
        }
    }



    }
