package com.example.marketplace.view.productDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.R
import com.example.marketplace.databinding.FragmentProductDetailsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class ProductDetails : Fragment(), OnMapReadyCallback {
    private val productImagesAdapter by lazy {ItemImageRecyclerViewAdapter(requireContext())}
    private val productDetailsBinding by lazy { FragmentProductDetailsBinding.inflate(layoutInflater) }
    private var listOfProductImages = mutableListOf<String>()
    private val args by navArgs<ProductDetailsArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.setNavigationIcon(R.drawable.icon_back)
        toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_productDetails_to_itemFragment)
        }
        setImagesList()
        fetchLocation()
    }
    private fun fetchLocation(){
           val supportMapFragment =
               childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        supportMapFragment.getMapAsync(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return productDetailsBinding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng(args.responseItem.place.latitude,args.responseItem.place.longitude)
        val markerOptions = MarkerOptions().position(latLng)
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f))
        googleMap.addMarker(markerOptions)
    }
    private fun setImagesList(){
        val recycle = view?.findViewById<RecyclerView>(R.id.images_list)
        if(arguments != null) {
            listOfProductImages = args.responseItem.images.toMutableList()
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

}
