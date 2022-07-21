package com.example.marketplace

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.marketplace.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setUpSpinner()

    }
    fun setUpSpinner(){
        binding.apply {

            ArrayAdapter.createFromResource(this@MainActivity,R.array.Countries,R.layout.spinner_text_view).also {
                arrayAdapter -> arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                spinner.adapter = arrayAdapter
            }
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    Log.i("TAG", "onItemSelected: ${spinner.selectedItem}")
                    var frg: Fragment? = null
                    frg = supportFragmentManager.findFragmentById(R.id.myNavHostFragment)
                    val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                    if (frg != null) {
                        Log.i("TAG", "onItemSelected: inside if")
                        ft.detach(frg)
                        ft.attach(frg)
                        ft.commit()
                    }

                }

            }

        }
    }



}