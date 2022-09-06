package com.example.marketplace.presentation.productsListView

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.marketplace.R
import com.example.marketplace.databinding.FragmetListOfProductsBinding
import com.example.marketplace.framework.IProductLocalSourceImpl
import com.example.marketplace.domain.model.DataModelInterface
import com.example.marketplace.domain.model.Interactors
import com.example.marketplace.domain.model.ProductCategory
import com.example.marketplace.framework.IProductsRepositoryImpl
import com.example.marketplace.interactors.GetAllProducts
import com.example.marketplace.interactors.GetProductsByCategory
import com.example.marketplace.presentation.ProductsListViewModel.GetProductsViewModel
import com.example.marketplace.presentation.ProductsListViewModel.GetProductsViewModelFactory
import com.google.android.material.tabs.TabLayout

class ListOfProductFragment : Fragment() {
    private var columnCount = 2
    private val productAdapter by lazy {
        ProductsListAdapter(
            requireContext(), OnClickListenerProduct { response -> goToDetails(response) }
        )
    }
    private val repo  =   IProductsRepositoryImpl.getInstance(
        IProductLocalSourceImpl.getInstance())
    private val viewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            factory = GetProductsViewModelFactory(
              Interactors(getAllProducts = GetAllProducts(repo),getProductsByCategory = GetProductsByCategory(repo))
            )
        )[GetProductsViewModel::class.java]
    }
    private val binding by lazy { FragmetListOfProductsBinding.inflate(layoutInflater) }
    private lateinit var dialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isAdded) {
            dialog = Dialog(requireContext())
        }
        dialog.setContentView(R.layout.progress_dialog)
        dialog.show()
        viewModel.getAllProduct()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = binding.root
        setUpTabLayout()
        return view
    }



    private fun goToDetails(response : DataModelInterface.ProductInfo){
        if(response.productId!=null) {
            val action = ListOfProductFragmentDirections.actionItemFragmentToProductDetails(
                response.productId!!
            )
            findNavController().navigate(action)
        }
    }
    private fun invisibleNoProductsViewItems(){
        binding.apply {
            productList.visibility = View.VISIBLE
            notAvailableLocation.visibility = View.INVISIBLE
            noItemTextView.visibility = View.INVISIBLE
            noProduct.visibility = View.INVISIBLE
        }
    }
    private fun noProduct(){
        binding.apply {
            productList.visibility = View.INVISIBLE
            notAvailableLocation.visibility = View.VISIBLE
            noItemTextView.visibility = View.VISIBLE
            noProduct.visibility = View.VISIBLE
        }
    }

    private fun setUpTabLayout(){
        val tabLayout = binding.tabs
        tabLayout.addTab(tabLayout.newTab().setText(R.string.allTab))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.horseTradingTab))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.usedEquipmentTab))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.usedEquipmentTab))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.usedEquipmentTab))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.usedEquipmentTab))
        setUpProductsList()
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.text) {
                    activity?.getString(R.string.allTab) -> {
                        viewModel.getAllProduct()
                        dialog.show()

                    }
                    activity?.getString(R.string.horseTradingTab) -> {
                        viewModel.getProductsByCategory(ProductCategory.trading)
                        dialog.show()
                    }
                    activity?.getString(R.string.usedEquipmentTab) -> {
                        viewModel.getProductsByCategory(ProductCategory.usedEqu)
                        dialog.show()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                invisibleNoProductsViewItems()
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        }
        )
    }
    private fun setUpProductsList(){
        val recycle = binding.productList
        with(recycle) {
            layoutManager =  GridLayoutManager(this.context, columnCount)
            viewModel.productListLiveData.observe(viewLifecycleOwner) {
                if (!viewModel.productListLiveData.value.isNullOrEmpty()) {
                    dialog.dismiss()
                    recycle.adapter = productAdapter
                       productAdapter.submitList(it)
                }else{
                    dialog.dismiss()
                    noProduct()
                }
            }
        }
    }
}