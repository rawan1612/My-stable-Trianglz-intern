package com.example.marketplace.view.productsList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marketplace.databinding.ItemSimilarProductsBinding
import com.example.marketplace.model.DataModelInterface
import com.example.marketplace.view.productDetails.ProductDetailsFragmentDirections

class SimilarIPostsAdapter(private val context: Context) : ListAdapter<DataModelInterface.ProductInfo, SimilarIPostsAdapter.SimilarProductsViewHolder>(ProductItemDiffUtil()) {
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
        holder.itemView.setOnClickListener {
            val action = item.productId?.let { productID ->
                ProductDetailsFragmentDirections.actionProductDetailsSelf(
                    productID
                )
            }
            if (action != null) {
                Navigation.findNavController(holder.itemView).navigate(action)
            }
        }
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




        }

    }

}