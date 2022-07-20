package com.example.marketplace.view

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marketplace.R
import com.example.marketplace.view.placeholder.PlaceholderContent
import com.google.android.material.tabs.TabLayout

/**
 * A fragment representing a list of Items.
 */
class ItemFragment : Fragment() {

    private var columnCount = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
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
                adapter = MyItemRecyclerViewAdapter(PlaceholderContent.ITEMS)
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
}