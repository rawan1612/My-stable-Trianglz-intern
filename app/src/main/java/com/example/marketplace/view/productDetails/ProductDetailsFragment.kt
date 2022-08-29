package com.example.marketplace.view.productDetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.R
import com.example.marketplace.databinding.FragmentProductDetailsBinding
import com.example.marketplace.localSource.IProductLocalSourceImpl
import com.example.marketplace.model.DataModelInterface
import com.example.marketplace.model.IProductsRepositoryImpl
import com.example.marketplace.view.productsList.ListOfProductFragmentDirections
import com.example.marketplace.view.productsList.OnClickListenerProduct
import com.example.marketplace.viewModel.ProductDetails.GetProductDetailsViewModel
import com.example.marketplace.viewModel.ProductDetails.GetProductDetailsViewModelFactory


class ProductDetailsFragment : Fragment(),IIntentHandle{
    private val productDetailsBinding by lazy { FragmentProductDetailsBinding.inflate(layoutInflater) }
    private val args by navArgs<ProductDetailsFragmentArgs>()
    private lateinit var productInfoAdapter : ProductDetailsAdapter
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            factory = GetProductDetailsViewModelFactory(
                IProductsRepositoryImpl.getInstance(
                    IProductLocalSourceImpl.getInstance()),requireContext()
            )
        )[GetProductDetailsViewModel::class.java]
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getProductByID()
        handleBackButton()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return productDetailsBinding.root
    }

    private fun getProductByID(){
        viewModel.getProductDetailsById(args.productID)
        setMainRecyclerView()
    }


    private fun setMainRecyclerView(){
        val recycle = view?.findViewById<RecyclerView>(R.id.main_recyclerView)
        with(recycle) {
            val linearLayoutManager = LinearLayoutManager(view?.context, LinearLayoutManager.VERTICAL, false)
            this?.layoutManager = linearLayoutManager
            this?.setHasFixedSize(true)
            productInfoAdapter = ProductDetailsAdapter(requireContext(),this@ProductDetailsFragment)
            this?.adapter = productInfoAdapter
            viewModel.productDetailsLiveData.observe(viewLifecycleOwner){
                productInfoAdapter.submitList(it)
            }

        }
    }
    private fun goToDetails(response : DataModelInterface.ProductInfo){
            val action = response.productId?.let { productID ->
                ProductDetailsFragmentDirections.actionProductDetailsSelf(
                    productID
                )
            }
            if (action != null) {
                view?.let { Navigation.findNavController(it).navigate(action) }
            }


    }

    private fun handleBackButton() {
        productDetailsBinding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }


    }
     override fun makeCall(phoneNumber: String) {
        val callIntent = Intent(Intent.ACTION_DIAL)
        callIntent.data = Uri.parse("tel:$phoneNumber")
        context?.startActivity(callIntent)
    }
    override fun sendMail(email: String) {
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.data = Uri.parse("mailto:")
        emailIntent.type = "text/plain"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        try {
            context?.startActivity(
                Intent.createChooser(emailIntent, context?.getString(R.string.email_client))
            )
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }
    }
}
