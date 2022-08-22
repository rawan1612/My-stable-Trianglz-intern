package com.example.marketplace.view.productDetails

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.R
import com.example.marketplace.model.DataModelInterface


class ProductDetailsFragmentRecyclerViewAdapter (context: Context ): RecyclerView.Adapter<ProductDetailsViewHolder>(){
    companion object {
        const val PRODUCT_INFO = 1
        const val LOCATION_INFO = 2
        const val SIMILAR_PRODUCTS = 3
        const val OWNER_INFO = 4
        const val HORSE_INFO = 5
    }
    val context : Context = context
    private val objects = mutableListOf<DataModelInterface>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductDetailsViewHolder {
            val layout = when (viewType) {
                PRODUCT_INFO -> R.layout.product_details_view
                LOCATION_INFO -> R.layout.location_info_view
                OWNER_INFO -> R.layout.owner_info_view
                HORSE_INFO -> R.layout.horse_info_view
                SIMILAR_PRODUCTS -> R.layout.similar_products_view
                else -> throw IllegalArgumentException("Invalid type")
            }
            val view = LayoutInflater
                .from(parent.context)
                .inflate(layout, parent, false)

            return ProductDetailsViewHolder(view,context)
    }

    override fun onBindViewHolder(holder: ProductDetailsViewHolder, position: Int) {
            holder.bind(objects[position])
    }

    override fun getItemCount(): Int {
      return  objects.size
    }

    override fun getItemViewType(position: Int): Int {
        return  when (objects[position]) {
            is DataModelInterface.Place -> LOCATION_INFO
            is DataModelInterface.Owner -> OWNER_INFO
            is DataModelInterface.HorseInfo   ->  HORSE_INFO
           is DataModelInterface.SimilarProducts -> SIMILAR_PRODUCTS
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

