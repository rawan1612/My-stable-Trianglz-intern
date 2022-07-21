package com.example.marketplace.view

import android.app.Dialog
import android.os.Bundle
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
import com.example.marketplace.viewModel.GetProductViewModel
import com.example.marketplace.viewModel.GetProductsViewModelFactory
import com.google.android.material.tabs.TabLayout

/**
 * A fragment representing a list of Items.
 */
class ItemFragment : Fragment() {
    private var columnCount = 2
    private val viewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            factory = GetProductsViewModelFactory(
                Repository.getInstance(
                    Client.getInstance(),
                    requireContext()
                )
            )
        )[GetProductViewModel::class.java]
    }
lateinit var dialog : Dialog
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
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)
var recycle = view.findViewById<RecyclerView>(R.id.list)
        val tabLayout = view.findViewById<TabLayout>(R.id.tabs)
        tabLayout.addTab(tabLayout.newTab().setText(R.string.allTab))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.horseTradingTab))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.usedEquipmentTab))
       


        // Set the adapter
        if (recycle is RecyclerView) {
            with(recycle) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)

                    else -> GridLayoutManager(context, columnCount)
                }
                viewModel.allProductListLiveData.observe(viewLifecycleOwner){
                    if(!viewModel.allProductListLiveData.value.isNullOrEmpty()){
                        dialog.dismiss()
                        adapter = MyItemRecyclerViewAdapter(it, view.context)

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

    private fun showProgressDialog(){
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.progress_dialog)
        dialog.show()
    }
    private fun stopProgressDialog(){
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.progress_dialog)
        dialog.dismiss()
    }
}