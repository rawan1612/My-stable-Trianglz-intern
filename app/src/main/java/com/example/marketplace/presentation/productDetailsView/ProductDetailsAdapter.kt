package com.example.marketplace.presentation.productDetailsView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marketplace.R
import com.example.marketplace.databinding.*
import com.example.marketplace.domain.model.DataModelInterface
import com.example.marketplace.presentation.productsListView.OnClickListenerProduct
import com.example.marketplace.presentation.productsListView.SimilarIPostsAdapter
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class ProductDetailsAdapter(var context : Context, private val handle: IIntentHandle,private val onClickListener: OnClickListenerProduct) : ListAdapter<DataModelInterface, RecyclerView.ViewHolder>(ProductDetailsDiffUtil()) {
    private val productInfo = R.layout.product_details_view
    private val locationInfo = R.layout.location_info_view
    private val similarProducts = R.layout.similar_products_view
    private val ownerInfo = R.layout.owner_info_view
    private val horseInfo = R.layout.horse_info_view
    private val similarProductsAdapter by lazy { SimilarIPostsAdapter(context,onClickListener) }
    var firstTimeFlag: Boolean = true
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            productInfo -> {
                var view = ProductDetailsViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ProductDataViewHolder(view)
            }
            ownerInfo -> {
                var view = OwnerInfoViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return OwnerDataViewHolder(view)
            }
            horseInfo -> {
                var view = HorseInfoViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return HorseDataViewHolder(view)
            }
            similarProducts -> {
                var view = SimilarProductsViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return SimilarDataViewHolder(view)
            }
            else -> {
                var view = LocationInfoViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return LocationDataViewHolder(view)
            }

        }
    }


    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataModelInterface.Place -> locationInfo
            is DataModelInterface.Owner -> ownerInfo
            is DataModelInterface.HorseInfoResponse -> horseInfo
            is DataModelInterface.SimilarProducts -> similarProducts
            else -> productInfo
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            productInfo -> (holder as ProductDataViewHolder).setData(getItem(position) as DataModelInterface.ProductInfo)
            locationInfo -> (holder as LocationDataViewHolder).setData(
                getItem(
                    position
                ) as DataModelInterface.Place
            )
            ownerInfo -> (holder as OwnerDataViewHolder).setData(
                getItem(
                    position
                ) as DataModelInterface.Owner
            )
            horseInfo -> (holder as HorseDataViewHolder).setData(
                getItem(
                    position
                ) as DataModelInterface.HorseInfoResponse
            )
            similarProducts -> (holder as SimilarDataViewHolder).setData(
                getItem(position) as DataModelInterface.SimilarProducts
            )
        }
    }

    inner class ProductDataViewHolder(val binding: ProductDetailsViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val productImagesAdapter by lazy { ProductImagesAdapter() }
        private var listOfProductImages = listOf<String>()
        fun setData(item: DataModelInterface.ProductInfo) {
            val productName = binding.productName
            val productPrice = binding.productPriceTv
            val expireDate = binding.publishDateTv
            val details = binding.detailsDescTv
            val recycle = binding.imagesListRV
            val productSoldOut = binding.productSoldOutBtn
            if (item.isAvailable == true) {
                productSoldOut.visibility = View.GONE
            } else {
                productSoldOut.visibility = View.VISIBLE

            }
            with(recycle) {
                val linearLayoutManager =
                    LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
                this.layoutManager = linearLayoutManager
                this.setHasFixedSize(true)
                this.adapter = productImagesAdapter
                listOfProductImages =
                    item.images?.toMutableList() ?: emptyList<String>().toMutableList()
                productImagesAdapter.submitList(listOfProductImages)
            }
            productName.text = item.itemName
            productName.text = item.itemName
            productPrice.text =
                context.getString(
                    R.string.currencyAndPrice,
                    item.currency,
                    item.price.toString()
                )
            expireDate.text = context.getString(R.string.expireDate,item.expiredDate)
            details.text = item.description
        }
    }


        inner class LocationDataViewHolder(val binding: LocationInfoViewBinding) :
            RecyclerView.ViewHolder(binding.root),
            OnMapReadyCallback {
            private lateinit var latLng: LatLng
            fun setData(item: DataModelInterface.Place) {
                val map: MapView = binding.mapView
                GoogleMapOptions()
                    .liteMode(true)
                map.onCreate(null)
                map.onResume()
                map.getMapAsync(this)
                if (item.latitude != null && item.longitude != null) {
                    latLng = LatLng(item.latitude, item.longitude)
                }
                val address = binding.locationDescription
                address.text = item.address
            }

            override fun onMapReady(googleMap: GoogleMap) {
                val markerOptions = MarkerOptions().position(latLng)
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
                googleMap.addMarker(markerOptions)
            }
        }


        inner class OwnerDataViewHolder(val binding: OwnerInfoViewBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun setData(item: DataModelInterface.Owner) {
                val ownerName = binding.ownerName
                val ownerPhone = binding.imageButtonOwnerPhone
                val ownerEmail = binding.imageButtonOwnerEmail
                val ownerImg = binding.ownerImg
                Glide.with(context)
                    .load(item.profileImg)
                    .placeholder(R.drawable.profile_img_placeholder)
                    .centerCrop()
                    .into(ownerImg)
                ownerName.text = item.name
                ownerPhone.setOnClickListener {
                    val phoneNumber = item.phoneNumber?.toString()?.trim()
                    if (phoneNumber != null) {
                        handle.makeCall(phoneNumber)
                    } else {
                        Toast.makeText(context, R.string.no_phone, Toast.LENGTH_SHORT).show()
                    }
                }
                ownerEmail.setOnClickListener {
                    if (item.email != null) {
                        val recipientEmail = item.email.trim()
                        if (recipientEmail.isNotEmpty()) {
                            handle.sendMail(recipientEmail)
                        } else {
                            Toast.makeText(context, R.string.no_email, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        }

        inner class HorseDataViewHolder(val binding: HorseInfoViewBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun setData(item: DataModelInterface.HorseInfoResponse) {
                val key = binding.key
                val value = binding.value
                val label = binding.horseInfoLabel
                if (firstTimeFlag) {
                    label.visibility = View.VISIBLE
                    firstTimeFlag = false
                } else {
                    label.visibility = View.GONE
                }
                key.text = item.key
                value.text = item.value

            }
        }

        inner class SimilarDataViewHolder(val binding: SimilarProductsViewBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun setData(item: DataModelInterface.SimilarProducts) {
                val recycleSimilarProducts =
                    itemView.findViewById<RecyclerView>(R.id.similar_items_Recycler)
                with(recycleSimilarProducts) {
                    val linearLayoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    this?.layoutManager = linearLayoutManager
                    this?.setHasFixedSize(true)
                    this?.adapter = similarProductsAdapter
                    item.similarProducts?.let { similarProductsAdapter.submitList(it) }
                }
            }
        }


    class ProductDetailsDiffUtil : DiffUtil.ItemCallback<DataModelInterface>() {
        override fun areItemsTheSame(
            oldItem: DataModelInterface,
            newItem: DataModelInterface
        ): Boolean {
            return when {
                oldItem is DataModelInterface.ProductInfo && newItem is DataModelInterface.ProductInfo -> {
                    oldItem == newItem
                }
                else -> false
            }
        }

        override fun areContentsTheSame(
            oldItem: DataModelInterface,
            newItem: DataModelInterface
        ): Boolean {
            return when {
                oldItem is DataModelInterface.ProductInfo && newItem is DataModelInterface.ProductInfo -> {
                    oldItem.productId == newItem.productId
                }
                else -> false
            }
        }
    }
}
