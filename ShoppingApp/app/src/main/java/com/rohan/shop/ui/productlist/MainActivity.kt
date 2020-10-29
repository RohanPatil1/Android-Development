package com.rohan.shop.ui.productlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.rohan.shop.R
import com.rohan.shop.data.db.ProductDatabase
import com.rohan.shop.data.db.entity.Product
import com.rohan.shop.data.repository.ProductRepository
import com.rohan.shop.other.ProductItemAdapter
import com.rohan.shop.ui.productlist.dialogutils.AddDialogListener
import com.rohan.shop.ui.productlist.dialogutils.AddProductDialog
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), KodeinAware {
    override val kodein: Kodein by kodein()
    private val factory: ProductViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val viewModel = ViewModelProviders.of(this, factory).get(ProductViewModel::class.java)

        val adapter = ProductItemAdapter(listOf(), viewModel)
        rvShoppingItems.layoutManager = LinearLayoutManager(this)
        rvShoppingItems.adapter = adapter

        //because we made it LiveData,we can observe
        viewModel.getAllProducts().observe(this, Observer {
            adapter.dataList = it
            adapter.notifyDataSetChanged()
        })

        fab.setOnClickListener {
            AddProductDialog(this, object : AddDialogListener {
                override fun onAddButtonClicked(item: Product) {

                    viewModel.upsert(item)
                }
            }
            ).show()
        }
    }
}