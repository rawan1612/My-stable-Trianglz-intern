package com.example.marketplace.view.productsList

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
import com.example.marketplace.localSource.IProductLocalSourceImpl
import com.example.marketplace.model.DataModelInterface
import com.example.marketplace.model.ProductCategory
import com.example.marketplace.model.IProductsRepositoryImpl
import com.example.marketplace.viewModel.ProductsList.GetProductViewModel
import com.example.marketplace.viewModel.ProductsList.GetProductsViewModelFactory
import com.google.android.material.tabs.TabLayout

class ListOfProductFragment : Fragment() {
    private var columnCount = 2
    private var allProductsList: List<DataModelInterface.ProductInfo> = listOf()
    private var filteredList: MutableList<DataModelInterface.ProductInfo> = arrayListOf()
    private var selectedCategory : String = ProductCategory.all.name
    private val productAdapter by lazy {
        ProductsListAdapter(
            requireContext(), OnClickListenerProduct { response -> goToDetails(response) }
        )
    }
    private val viewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            factory = GetProductsViewModelFactory(
                IProductsRepositoryImpl.getInstance(
                    IProductLocalSourceImpl.getInstance())
            )
        )[GetProductViewModel::class.java]
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

    private fun filterTrading(): List<DataModelInterface.ProductInfo> {
        dialog.show()
            viewModel.getProductsByCategory(ProductCategory.trading)
            viewModel.filteredProductListLiveData.observe(viewLifecycleOwner) {
                if (viewModel.filteredProductListLiveData.value!!.isNotEmpty()) {
                    invisibleNoProductsViewItems()
                    dialog.dismiss()
                    filteredList = it.toMutableList()
                    productAdapter.submitList(filteredList)
                }
                else {
                    dialog.dismiss()
                    productAdapter.submitList(emptyList())
                    binding.apply {
                        list.visibility = View.INVISIBLE
                        notAvailableLocation.visibility = View.VISIBLE
                        noItemTextView.visibility = View.VISIBLE
                        noProduct.visibility = View.VISIBLE
                    }
                }
            }
        return filteredList
    }
    private fun filterEquipment(): List<DataModelInterface.ProductInfo> {
        dialog.show()
        viewModel.getProductsByCategory(ProductCategory.usedEqu)
            viewModel.filteredProductListLiveData.observe(viewLifecycleOwner) {
                if (viewModel.filteredProductListLiveData.value!!.isNotEmpty()) {
                    invisibleNoProductsViewItems()
                    dialog.dismiss()
                    filteredList = it.toMutableList()
                    productAdapter.submitList(filteredList)
                }
                else{
                    dialog.dismiss()
                    binding.apply {
                        list.visibility = View.INVISIBLE
                        notAvailableLocation.visibility = View.VISIBLE
                        noItemTextView.visibility = View.VISIBLE
                        noProduct.visibility = View.VISIBLE
                    }
          }
            }
        return filteredList
    }
    private fun goToDetails(response : DataModelInterface.ProductInfo){
        if(response.category!=null && response.productId!=null) {
            val action = ListOfProductFragmentDirections.actionItemFragmentToProductDetails(
                response.productId!!
            )
            findNavController().navigate(action)
        }
    }
    private fun invisibleNoProductsViewItems(){
        binding.apply {
            list.visibility = View.VISIBLE
            notAvailableLocation.visibility = View.INVISIBLE
            noItemTextView.visibility = View.INVISIBLE
            noProduct.visibility = View.INVISIBLE
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
        var position = tabLayout.selectedTabPosition
        setUpAllProductsList(position)
        when (position){
            1 -> { productAdapter.submitList(filteredList); selectedCategory = "trading"}
            2 -> { productAdapter.submitList(filteredList) ; selectedCategory = "usedEqu" }
        }
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.text) {
                    activity?.getString(R.string.allTab) -> {
                        productAdapter.submitList(allProductsList)
                        selectedCategory = "all"
                    }
                    activity?.getString(R.string.horseTradingTab) -> {
                        filterTrading()
                        selectedCategory = ProductCategory.trading.name
                    }
                    activity?.getString(R.string.usedEquipmentTab) -> {
                        filterEquipment()
                        selectedCategory = ProductCategory.usedEqu.name

                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                filteredList.clear()
                invisibleNoProductsViewItems()

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                when (tab?.text) {
                    activity?.getString(R.string.allTab) -> productAdapter.submitList(allProductsList)
                    activity?.getString(R.string.horseTradingTab) -> productAdapter.submitList(filteredList)
                    activity?.getString(R.string.usedEquipmentTab) -> productAdapter.submitList(filteredList)

                }
            }

        }

        )

    }
    private fun setUpAllProductsList(position: Int){
        val recycle = binding.list
        // Set the adapter
        with(recycle) {
            layoutManager =  GridLayoutManager(this.context, columnCount)
            viewModel.allProductListLiveData.observe(viewLifecycleOwner) {
                if (!viewModel.allProductListLiveData.value.isNullOrEmpty()) {
                    dialog.dismiss()
                    recycle.adapter = productAdapter
                   if (position==0) {
                       productAdapter.submitList(it)
                       allProductsList = it
                   }
                }
            }
        }
    }
}