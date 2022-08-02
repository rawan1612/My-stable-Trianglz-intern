package com.example.marketplace.view.productDetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marketplace.R
import com.example.marketplace.databinding.FragmentProductDetailsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.text.DateFormatSymbols

class ProductDetailsFragment : Fragment(), OnMapReadyCallback {
    private val productImagesAdapter by lazy { ProductItemImageRecyclerViewAdapter(requireContext()) }
    private val productDetailsBinding by lazy { FragmentProductDetailsBinding.inflate(layoutInflater) }
    private var listOfProductImages = mutableListOf<String>()
    private val args by navArgs<ProductDetailsFragmentArgs>()
    private lateinit var product: com.example.marketplace.model.Response

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.setNavigationIcon(R.drawable.icon_back)
        toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_productDetails_to_itemFragment)
        }
        setImagesList()
        setProductInfo()
        fetchLocation()
    }

    private fun fetchLocation() {
        val supportMapFragment =
            childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        supportMapFragment.getMapAsync(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        product = args.responseItem
        return productDetailsBinding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng(product.place.latitude, product.place.longitude)
        val markerOptions = MarkerOptions().position(latLng)
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f))
        googleMap.addMarker(markerOptions)
    }

    private fun setImagesList() {
        val recycle = view?.findViewById<RecyclerView>(R.id.images_list)
        if (arguments != null) {
            listOfProductImages = product.images.toMutableList()
        }
        with(recycle) {
            val linearLayoutManager = LinearLayoutManager(view?.context)
            linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            this?.layoutManager = linearLayoutManager
            this?.setHasFixedSize(true)
            this?.adapter = productImagesAdapter
            productImagesAdapter.setProductsList(listOfProductImages)
        }
    }

    private fun setProductInfo() {
        val productName = view?.findViewById<TextView>(R.id.product_name)
        val productPrice = view?.findViewById<TextView>(R.id.product_price)
        val expireDate = view?.findViewById<TextView>(R.id.publish_date)
        val details = view?.findViewById<TextView>(R.id.details)
        val ownerName = view?.findViewById<TextView>(R.id.owner_name)
        val ownerPhone = view?.findViewById<ImageView>(R.id.owner_phone)
        val ownerEmail = view?.findViewById<ImageView>(R.id.owner_email)
        val ownerImg = view?.findViewById<ImageView>(R.id.owner_img)
        checkProductAvailable()
        productName?.text = product.itemName
        productPrice?.text = context?.getString(R.string.currencyAndPrice,product.currency,product.price.toString())
        details?.text = product.description
        ownerName?.text = product.seller.name
        val month = getMonth(product.expiredDate.month)
        expireDate?.text = context?.getString(R.string.expireDate, product.expiredDate.day.toString() , month)
        ownerPhone?.setOnClickListener {
            val phoneNumber = product.seller.phoneNumber.toString().trim()
            if (phoneNumber != "0") {
                makeCall(phoneNumber)
            } else {
                Toast.makeText(requireContext(), R.string.no_phone, Toast.LENGTH_SHORT).show()
            }
        }
        ownerEmail?.setOnClickListener {
            val recipientEmail = product.seller.email.trim()
            if (recipientEmail.isNotEmpty()) {
                sendMail(recipientEmail)
            } else {
                Toast.makeText(requireContext(), R.string.no_email, Toast.LENGTH_SHORT).show()
            }
        }
        if (ownerImg != null) {
            Glide.with(requireContext())
                .load(product.seller.profileImg)
                .placeholder(R.drawable.profile_img_placeholder)
                .fitCenter()
                .into(ownerImg)
        }

    }

    private fun setHorseInfo() {
        val horseName = view?.findViewById<TextView>(R.id.horse_name)
        val horseColor = view?.findViewById<TextView>(R.id.horse_color)
        val horseGender = view?.findViewById<TextView>(R.id.horse_gender)
        val horseBreed = view?.findViewById<TextView>(R.id.horse_breed)
        val horseStrain = view?.findViewById<TextView>(R.id.horse_strain)
        val horseType = view?.findViewById<TextView>(R.id.horse_type)
        val horseDOB = view?.findViewById<TextView>(R.id.HDOB)
        horseName?.text = product.horseInfo?.hName
        horseGender?.text = product.horseInfo?.hGender
        horseBreed?.text = product.horseInfo?.hBreed
        horseColor?.text = product.horseInfo?.hColor
        horseStrain?.text = product.horseInfo?.hStrain
        horseType?.text = product.horseInfo?.hSexType
        horseDOB?.text = product.horseInfo?.hDOB
    }

    private fun getMonth(month: Int): String {
        return DateFormatSymbols().months[month - 1]
    }

    private fun makeCall(phoneNumber: String) {
        val callIntent = Intent(Intent.ACTION_DIAL)
        callIntent.data = Uri.parse("tel:$phoneNumber")
        startActivity(callIntent)

    }

    private fun sendMail(email: String) {
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.data = Uri.parse("mailto:")
        emailIntent.type = "text/plain"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        try {
            startActivity(
                Intent.createChooser(
                    emailIntent,
                    activity?.getString(R.string.email_client)
                )
            )
        } catch (e: Exception) {
            Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
        }
    }
    private fun checkProductAvailable(){
        val productSoldOut = view?.findViewById<RelativeLayout>(R.id.product_sold_out)
        val ownerInfoView = view?.findViewById<LinearLayout>(R.id.owner_info_view)
        val horseInfoView = view?.findViewById<LinearLayout>(R.id.horse_info_view)

        if(!product.isAvailable){
            productSoldOut?.visibility = View.VISIBLE
            ownerInfoView?.visibility= View.GONE
            horseInfoView?.visibility = View.GONE


        }else{
            productSoldOut?.visibility = View.GONE
            ownerInfoView?.visibility= View.VISIBLE
            if (product.horseInfo != null){
                horseInfoView?.visibility = View.VISIBLE
                setHorseInfo()
            }else{
                horseInfoView?.visibility = View.GONE
            }

        }
    }
}
