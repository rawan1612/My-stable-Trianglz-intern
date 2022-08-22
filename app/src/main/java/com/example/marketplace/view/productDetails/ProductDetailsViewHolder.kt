package com.example.marketplace.view.productDetails

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marketplace.R
import com.example.marketplace.model.DataModelInterface
import com.example.marketplace.view.productsList.ListOfProductFragmentDirections
import com.example.marketplace.view.productsList.OnClickListenerProduct
import com.example.marketplace.view.productsList.SimilarItemListRecyclerViewAdapter
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.text.DateFormatSymbols

class ProductDetailsViewHolder(itemView: View , context: Context) : RecyclerView.ViewHolder(itemView) ,
    OnMapReadyCallback {
    val context : Context = context
    private val productImagesAdapter by lazy { ProductItemImageRecyclerViewAdapter(context) }
    private val similarProducts by lazy { SimilarItemListRecyclerViewAdapter(context,
        OnClickListenerProduct { response -> goToDetails(response) })}

    private fun goToDetails(response: DataModelInterface.Response) {
        val action = ProductDetailsFragmentDirections.actionProductDetailsSelf(response,response.productInfo!!.category)
            Navigation.findNavController(itemView).navigate(action)

    }

    private var listOfProductImages = mutableListOf<String>()
    private lateinit var latLng : LatLng


    private fun bindProductData(item: DataModelInterface.ProductInfo) {
        val productName = itemView.findViewById<TextView>(R.id.product_name)
        val productPrice = itemView.findViewById<TextView>(R.id.product_price)
        val expireDate = itemView.findViewById<TextView>(R.id.publish_date)
        val details = itemView.findViewById<TextView>(R.id.details)
        val recycle = itemView.findViewById<RecyclerView>(R.id.images_list)

        val productSoldOut = itemView.findViewById<RelativeLayout>(R.id.product_sold_out)
        if(item.isAvailable){
            productSoldOut?.visibility = View.GONE
        }else{
            productSoldOut?.visibility = View.VISIBLE

        }
        with(recycle) {
            val linearLayoutManager = LinearLayoutManager(itemView.context)
            linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            this?.layoutManager = linearLayoutManager
            this?.setHasFixedSize(true)
            this?.adapter = productImagesAdapter
            listOfProductImages = item.images.toMutableList()
            productImagesAdapter.setProductsList(listOfProductImages)
        }
        val month = getMonth(item.expiredDate.month)
        productName.text = item.itemName
        productPrice.text = context.getString(
            R.string.currencyAndPrice,
            item.currency,
            item.price.toString()
        )
        expireDate.text = context.getString(
            R.string.expireDate,
            item.expiredDate.day.toString(),
            month
        )
        details.text = item.description
    }
    private fun bindOwnerData(item: DataModelInterface.Owner) {
        val ownerName = itemView.findViewById<TextView>(R.id.owner_name)
        val ownerPhone = itemView.findViewById<ImageView>(R.id.owner_phone)
        val ownerEmail = itemView.findViewById<ImageView>(R.id.owner_email)
        val ownerImg = itemView.findViewById<ImageView>(R.id.owner_img)
        Glide.with(context)
            .load(item.profileImg)
            .placeholder(R.drawable.profile_img_placeholder)
            .centerCrop()
            .into(ownerImg)
        ownerName?.text = item.name
        ownerPhone?.setOnClickListener {
            val phoneNumber = item.phoneNumber.toString().trim()
            if (phoneNumber != "0") {
                makeCall(phoneNumber)
            } else {
                Toast.makeText(context, R.string.no_phone, Toast.LENGTH_SHORT).show()
            }
        }
        ownerEmail?.setOnClickListener {
            val recipientEmail = item.email.trim()
            if (recipientEmail.isNotEmpty()) {
                sendMail(recipientEmail)
            } else {
                Toast.makeText(context, R.string.no_email, Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun bindLocationData(item: DataModelInterface.Place){
        val map : MapView = itemView.findViewById(R.id.map_view) as MapView
        GoogleMapOptions()
            .liteMode(true)
        map.onCreate(null)
        map.onResume()
        map.getMapAsync(this)
        latLng = LatLng(item.latitude, item.longitude)
        val address = itemView.findViewById<TextView>(R.id.location_description)
        address.text = item.address
    }
    private fun bindHorseData(item : DataModelInterface.HorseInfo){
        val horseName = itemView.findViewById<TextView>(R.id.horse_name)
        val horseColor = itemView.findViewById<TextView>(R.id.horse_color)
        val horseGender = itemView.findViewById<TextView>(R.id.horse_gender)
        val horseBreed = itemView.findViewById<TextView>(R.id.horse_breed)
        val horseStrain = itemView.findViewById<TextView>(R.id.horse_strain)
        val horseType = itemView.findViewById<TextView>(R.id.horse_type)
        val horseDOB = itemView.findViewById<TextView>(R.id.HDOB)
        horseName?.text = item.hName
        horseGender?.text = item.hGender
        horseBreed?.text = item.hBreed
        horseColor?.text = item.hColor
        horseStrain?.text = item.hStrain
        horseType?.text = item.hSexType
        horseDOB?.text = item.hDOB

    }

     private fun bindSimilarData(item: DataModelInterface.SimilarProducts){
        val recycleSimilarProducts = itemView.findViewById<RecyclerView>(R.id.similar_items_Recycler)
        with(recycleSimilarProducts) {
            val linearLayoutManager = LinearLayoutManager(itemView.context)
            linearLayoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
            this?.layoutManager = linearLayoutManager
            this?.setHasFixedSize(true)
            this?.adapter = similarProducts
            item.similarProducts?.let { similarProducts.setProductList(it) }
        }

    }

    fun bind(dataModel: DataModelInterface) {
        when (dataModel) {
            is DataModelInterface.ProductInfo -> bindProductData(dataModel)
            is DataModelInterface.Owner -> bindOwnerData(dataModel)
            is DataModelInterface.Place -> bindLocationData(dataModel)
            is DataModelInterface.HorseInfo -> bindHorseData(dataModel)
            is DataModelInterface.SimilarProducts -> bindSimilarData(dataModel)

        }
    }

private fun getMonth(month: Int): String {
    return DateFormatSymbols().months[month - 1]
}
private fun makeCall(phoneNumber: String) {
    val callIntent = Intent(Intent.ACTION_DIAL)
    callIntent.data = Uri.parse("tel:$phoneNumber")
    context.startActivity(callIntent)
}
private fun sendMail(email: String) {
    val emailIntent = Intent(Intent.ACTION_SEND)
    emailIntent.data = Uri.parse("mailto:")
    emailIntent.type = "text/plain"
    emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
    try {
        context.startActivity(
            Intent.createChooser(emailIntent, context.getString(R.string.email_client))
        )
    } catch (e: Exception) {
        Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
    }
}

    override fun onMapReady(googleMap: GoogleMap) {
        val markerOptions = MarkerOptions().position(latLng)
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f))
        googleMap.addMarker(markerOptions)
    }
}