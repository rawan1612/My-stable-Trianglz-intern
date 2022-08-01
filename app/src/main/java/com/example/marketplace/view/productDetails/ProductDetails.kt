package com.example.marketplace.view.productDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marketplace.R
import com.example.marketplace.databinding.FragmentItemListBinding
import com.example.marketplace.databinding.ProductDetailsViewBinding
import com.example.marketplace.model.Place
import com.example.marketplace.view.productsList.MyItemRecyclerViewAdapter
import com.example.marketplace.view.productsList.OnClickListenerProduct
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class ProductDetails : Fragment(), OnMapReadyCallback {
    private val productAdapter by lazy {
        ItemImageRecyclerViewAdapter(
            requireContext()
        )
    }
    private val productImagesListBinding by lazy { ProductDetailsViewBinding.inflate(layoutInflater) }
    private val listOfProductImages : List<String> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.setNavigationIcon(R.drawable.icon_back)
        toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_productDetails_to_itemFragment)
        }
        fetchLocation()
    }
    private fun fetchLocation(){
           val supportMapFragment =
               childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
           supportMapFragment?.getMapAsync(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = productImagesListBinding.root
        val recycle = productImagesListBinding.imagesList
        with(recycle) {
            layoutManager = LinearLayoutManager(this.context)
                    recycle.adapter = productAdapter
                    productAdapter.setProductsList(listOfProductImages.toMutableList())

        }
        return view

    }

    override fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng(37.7750, -122.4183)
        val markerOptions = MarkerOptions().position(latLng).title("I am here!")
        googleMap?.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f))
        googleMap?.addMarker(markerOptions)
    }

}
