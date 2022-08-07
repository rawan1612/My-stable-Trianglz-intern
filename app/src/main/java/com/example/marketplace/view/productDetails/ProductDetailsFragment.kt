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
import com.example.marketplace.model.DataModelInterface

class ProductDetailsFragment : Fragment(){
    private val productDetailsBinding by lazy { FragmentProductDetailsBinding.inflate(layoutInflater) }
    private val args by navArgs<ProductDetailsFragmentArgs>()
    private lateinit var product: DataModelInterface.Response
    private lateinit var productInfoAdapter : ProductDetailsFragmentRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.setNavigationIcon(R.drawable.icon_back)
        toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_productDetails_to_itemFragment)
        }
        val dataList = ArrayList<DataModelInterface>()
        product.productInfo?.let { dataList.add(it) }
        product.seller?.let { dataList.add(it) }
        product.place?.let { dataList.add(it) }
        product.horseInfo?.let { dataList.add(it) }
        val recycle = view.findViewById<RecyclerView>(R.id.main_recyclerView)
        with(recycle) {
            val linearLayoutManager = LinearLayoutManager(view?.context)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            this?.layoutManager = linearLayoutManager
            this?.setHasFixedSize(true)
            productInfoAdapter = ProductDetailsFragmentRecyclerViewAdapter(requireContext(),requireActivity().supportFragmentManager)
            this?.adapter = productInfoAdapter
            productInfoAdapter.setData(dataList)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        product = args.responseItem
        return productDetailsBinding.root
    }
}
