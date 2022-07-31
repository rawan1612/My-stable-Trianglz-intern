package com.example.marketplace.view.ProductDetails

import android.app.Activity
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import com.example.marketplace.R
import com.example.marketplace.model.Place
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class ProductDetails : Fragment(), OnMapReadyCallback {
val address = Place(latitude = 42.9096,
    longitude = 25.2276)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
    //    (activity as AppCompatActivity).setSupportActionBar(view.findViewById(R.id.toolbar))
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

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_details, container, false)

    }
    private fun addMarkers(googleMap: GoogleMap) {

    }

    override fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng(37.7750, -122.4183)
        val markerOptions = MarkerOptions().position(latLng).title("I am here!")
        googleMap?.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f))
        googleMap?.addMarker(markerOptions)
    }

}