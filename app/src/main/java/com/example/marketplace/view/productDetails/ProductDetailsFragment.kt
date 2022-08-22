package com.example.marketplace.view.productDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.R
import com.example.marketplace.databinding.FragmentProductDetailsBinding
import com.example.marketplace.localSource.Client
import com.example.marketplace.model.DataModelInterface
import com.example.marketplace.model.Repository
import com.example.marketplace.view.productsList.SimilarItemListRecyclerViewAdapter
import com.example.marketplace.viewModel.ProductDetails.GetProductDetailsViewModel
import com.example.marketplace.viewModel.ProductDetails.GetProductDetailsViewModelFactory
import com.example.marketplace.viewModel.ProductsList.GetProductViewModel
import com.example.marketplace.viewModel.ProductsList.GetProductsViewModelFactory

class ProductDetailsFragment : Fragment(){
    private val productDetailsBinding by lazy { FragmentProductDetailsBinding.inflate(layoutInflater) }

    private val args by navArgs<ProductDetailsFragmentArgs>()
    private lateinit var product: DataModelInterface.Response
    private lateinit var productInfoAdapter : ProductDetailsFragmentRecyclerViewAdapter
    private val viewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            factory = GetProductDetailsViewModelFactory(
                Repository.getInstance(
                    Client.getInstance())
            )
        )[GetProductDetailsViewModel::class.java]
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.setNavigationIcon(R.drawable.icon_back)
        toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_productDetails_to_itemFragment)
        }
        if (args.category == "all") {
            viewModel.getAllProduct()
        } else {
            viewModel.getProductsByCategory(args.category)
        }
        val dataList = ArrayList<DataModelInterface>()
        var similarData: DataModelInterface.SimilarProducts = DataModelInterface.SimilarProducts(similarProducts = null)

        viewModel.filteredProductListLiveData.observe(viewLifecycleOwner) {
            if (viewModel.filteredProductListLiveData.value!!.isNotEmpty()) {
                similarData.similarProducts = it
            }
            product.productInfo?.let { dataList.add(it) }
            product.seller?.let { dataList.add(it) }
            product.place?.let { dataList.add(it) }
            product.horseInfo?.let { dataList.add(it) }
            similarData?.let { it1 -> dataList.add(it1)
            }
            val recycle = view.findViewById<RecyclerView>(R.id.main_recyclerView)
            with(recycle) {
                val linearLayoutManager = LinearLayoutManager(view.context)
                linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
                this?.layoutManager = linearLayoutManager
                this?.setHasFixedSize(true)
                productInfoAdapter = ProductDetailsFragmentRecyclerViewAdapter(requireContext())
                this?.adapter = productInfoAdapter
                productInfoAdapter.setData(dataList)
                dataList.clear()
            }

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
