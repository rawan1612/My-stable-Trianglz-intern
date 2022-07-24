package com.example.marketplace.view

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.marketplace.R
import com.example.marketplace.databinding.ActivityMainBinding
import com.example.marketplace.databinding.FragmentItemListBinding
import com.example.marketplace.localSource.Client
import com.example.marketplace.model.Repository
import com.example.marketplace.model.Response
import com.example.marketplace.viewModel.GetProductViewModel
import com.example.marketplace.viewModel.GetProductsViewModelFactory
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ItemFragment : Fragment() {
    private var columnCount = 2
    private var allProductsList: List<Response> = listOf()
    private var filteredList: MutableList<Response> = arrayListOf()
    private lateinit var  txt :  TextView
    private val productAdapter by lazy {
        MyItemRecyclerViewAdapter(
            requireContext()
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
    private val binding by lazy { FragmentItemListBinding.inflate(layoutInflater) }
    private lateinit var dialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
        if (isAdded) {
            dialog = Dialog(requireContext())
        }
        dialog.setContentView(R.layout.progress_dialog)
        viewModel.getAllProduct()
        dialog.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = binding.root
        val recycle = binding.list
        val tabLayout = binding.tabs
        tabLayout.addTab(tabLayout.newTab().setText(R.string.allTab))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.horseTradingTab))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.usedEquipmentTab))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.usedEquipmentTab))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.usedEquipmentTab))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.usedEquipmentTab))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Log.i("TAG", "onTabSelected: ")
                when (tab?.text) {
                    activity?.getString(R.string.allTab) -> productAdapter.setProductList(allProductsList)
                    activity?.getString(R.string.horseTradingTab) -> filterTrading()
                    activity?.getString(R.string.usedEquipmentTab) -> filterEquipment()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Log.i("TAG", "onTabUnselected: ")
                filteredList.clear()
                binding.list.visibility = View.VISIBLE
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                Log.i("TAG", "onTabReselected: ")
                when (tab?.text) {
                    activity?.getString(R.string.allTab) -> productAdapter.setProductList(allProductsList)
                    activity?.getString(R.string.horseTradingTab) -> productAdapter.setProductList(filteredList)
                    activity?.getString(R.string.usedEquipmentTab) -> productAdapter.setProductList(filteredList)

                }
            }

        }

        )

        // Set the adapter
        with(recycle) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)

                else -> GridLayoutManager(context, columnCount)
            }
            viewModel.allProductListLiveData.observe(viewLifecycleOwner) {
                if (!viewModel.allProductListLiveData.value.isNullOrEmpty()) {
                    dialog.dismiss()
                    recycle.adapter = productAdapter
                    productAdapter.setProductList(it)
                    allProductsList = it
                }

            }
        }
        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            ItemFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }


    private fun filterTrading(): List<Response> {
        dialog.show()
            viewModel.getProductsByCategory("usedEqu")
            viewModel.getProductsByCategory("trading")
            viewModel.filterdProductListLiveData.observe(viewLifecycleOwner) {
                if (viewModel.filterdProductListLiveData.value!!.isNotEmpty()) {
                    dialog.dismiss()
                    filteredList = it.toMutableList()
                    productAdapter.setProductList(filteredList)
                }
//                else {
//                    dialog.dismiss()
//                    binding.apply {
//                      list.visibility = View.INVISIBLE
//                    }
//                }
            }
        return filteredList
    }

    private fun filterEquipment(): List<Response> {
        dialog.show()
        viewModel.getProductsByCategory("usedEqu")
            viewModel.filterdProductListLiveData.observe(viewLifecycleOwner) {
                if (viewModel.filterdProductListLiveData.value!!.isNotEmpty()) {
                    dialog.dismiss()
                    filteredList = it.toMutableList()
                    productAdapter.setProductList(filteredList)
                }
//                else{
//                    dialog.dismiss()
//                    binding.apply {
//                       list.visibility = View.INVISIBLE
//                    }
//            }
            }
        return filteredList
    }
}