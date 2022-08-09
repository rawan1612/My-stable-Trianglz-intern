package com.example.marketplace.view.productsList

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.marketplace.R
import com.example.marketplace.databinding.FragmetListOfProductsBinding
import com.example.marketplace.localSource.Client
import com.example.marketplace.model.DataModelInterface
import com.example.marketplace.model.Repository
import com.example.marketplace.viewModel.ProductsList.GetProductViewModel
import com.example.marketplace.viewModel.ProductsList.GetProductsViewModelFactory
import com.google.android.material.tabs.TabLayout

class ListOfProductFragment : Fragment() {
    private var columnCount = 2
    private var allProductsList: List<DataModelInterface.Response> = listOf()
    private var filteredList: MutableList<DataModelInterface.Response> = arrayListOf()
    private var selectedCategory : String = "all"
    private val productAdapter by lazy {
        ProductItemListRecyclerViewAdapter(
            requireContext(), OnClickListenerProduct { response -> goToDetails(response) }
        )
    }
    private val viewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            factory = GetProductsViewModelFactory(
                Repository.getInstance(
                    Client.getInstance())
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
    ): View? {
        val view = binding.root
        setUpTabLayout()
        return view
    }

    private fun filterTrading(): List<DataModelInterface.Response> {
        dialog.show()
            viewModel.getProductsByCategory("trading")
            viewModel.filteredProductListLiveData.observe(viewLifecycleOwner) {
                if (viewModel.filteredProductListLiveData.value!!.isNotEmpty()) {
                    invisibleNoProductsViewItems()
                    dialog.dismiss()
                    filteredList = it.toMutableList()
                    productAdapter.setProductList(filteredList)
                }
                else {
                    dialog.dismiss()
                    productAdapter.setProductList(emptyList())
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
    private fun filterEquipment(): List<DataModelInterface.Response> {
        dialog.show()
        viewModel.getProductsByCategory("usedEqu")
            viewModel.filteredProductListLiveData.observe(viewLifecycleOwner) {
                if (viewModel.filteredProductListLiveData.value!!.isNotEmpty()) {
                    invisibleNoProductsViewItems()
                    dialog.dismiss()
                    filteredList = it.toMutableList()
                    productAdapter.setProductList(filteredList)
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
    private fun goToDetails(response : DataModelInterface.Response){
        val action = ListOfProductFragmentDirections.actionItemFragmentToProductDetails(response,selectedCategory)
        findNavController().navigate(action)
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
            1 -> { productAdapter.setProductList(filteredList); selectedCategory = "trading"}
            2 -> { productAdapter.setProductList(filteredList) ; selectedCategory = "usedEqu" }
        }
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {

                when (tab?.text) {
                    activity?.getString(R.string.allTab) -> {
                        productAdapter.setProductList(allProductsList)
                        selectedCategory = "all"
                    }
                    activity?.getString(R.string.horseTradingTab) -> {
                        filterTrading()
                        selectedCategory = "trading"
                    }
                    activity?.getString(R.string.usedEquipmentTab) -> {
                        filterEquipment()
                        selectedCategory = "usedEqu"

                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                filteredList.clear()
                invisibleNoProductsViewItems()

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                when (tab?.text) {
                    activity?.getString(R.string.allTab) -> productAdapter.setProductList(allProductsList)
                    activity?.getString(R.string.horseTradingTab) -> productAdapter.setProductList(filteredList)
                    activity?.getString(R.string.usedEquipmentTab) -> productAdapter.setProductList(filteredList)

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
                       productAdapter.setProductList(it)
                       allProductsList = it
                   }
                }

            }
        }
    }
}