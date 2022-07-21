package com.example.marketplace.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.marketplace.R
import com.example.marketplace.localSource.Client
import com.example.marketplace.model.Repository
import com.example.marketplace.model.Response
import com.example.marketplace.viewModel.GetProductViewModel
import com.example.marketplace.viewModel.GetProductsViewModelFactory
import com.google.android.material.tabs.TabLayout

class ItemFragment : Fragment() {
    private var columnCount = 2
    private var allProductsList: List<Response> = listOf()
    private var filteredList: MutableList<Response> = arrayListOf()
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
    private lateinit var dialog: Dialog
    override fun onDetach() {
        super.onDetach()
        Log.i("TAG", "onDetach: ")
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.i("TAG", "onAttach: ")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("TAG", "onCreate: ")
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
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)
        val recycle = view.findViewById<RecyclerView>(R.id.list)
        val tabLayout = view.findViewById<TabLayout>(R.id.tabs)
        tabLayout.addTab(tabLayout.newTab().setText(R.string.allTab))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.horseTradingTab))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.usedEquipmentTab))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.text) {
                    activity?.getString(R.string.allTab) -> productAdapter.setProductList(allProductsList)
                    activity?.getString(R.string.horseTradingTab) -> productAdapter.setProductList(
                        filterTrading()
                    )
                    activity?.getString(R.string.usedEquipmentTab) -> productAdapter.setProductList(
                        filterEquipment()
                    )

                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                filteredList.clear()
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

        // Set the adapter
        if (recycle is RecyclerView) {
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

                //   adapter = MyItemRecyclerViewAdapter(PlaceholderContent.ITEMS)
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
        for (i in allProductsList) {
            if (i.category == "trading") {
                filteredList.add(i)
            }
        }
        return filteredList
    }

    private fun filterEquipment(): List<Response> {

        for (i in allProductsList) {
            if (i.category == "usedEqu") {
                filteredList.add(i)
            }
        }
        return filteredList
    }
}