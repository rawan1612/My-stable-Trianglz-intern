package com.example.marketplace.presentation.productsListView

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marketplace.databinding.ItemSimilarProductsBinding
import com.example.marketplace.domain.model.DataModelInterface
import com.example.marketplace.presentation.productDetailsView.ProductDetailsFragmentDirections

class SimilarIPostsAdapter(private val context: Context,private val onClickListener: OnClickListenerProduct) : ListAdapter<DataModelInterface.ProductInfo, SimilarIPostsAdapter.SimilarProductsViewHolder>(ProductItemDiffUtil()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SimilarIPostsAdapter.SimilarProductsViewHolder {
        return SimilarProductsViewHolder(
            ItemSimilarProductsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: SimilarIPostsAdapter.SimilarProductsViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.setData(item)
    }


    inner class SimilarProductsViewHolder(binding: ItemSimilarProductsBinding) : RecyclerView.ViewHolder(binding.root) {
        val img : ImageView = binding.similarItemImage
        val itemName : TextView = binding.similarItemName
        val itemPrice : TextView = binding.similarItemPrice
        fun setData(item : DataModelInterface.ProductInfo) {
            Glide.with(context)
                .load(item.thumbnail)
                .into(img)
           itemName.text = item.itemName
            itemPrice.text = item.currency + " " + item.price
            itemView.setOnClickListener {
                onClickListener.onClick(item)
            }




        }


    }

}