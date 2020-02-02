package com.rohan.interntask4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.rohan.interntask4.adapter.ProductAdapter
import com.rohan.interntask4.data_models.Product
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    var mFirestore: FirebaseFirestore? = null
    val prodList: MutableList<Product> = mutableListOf()
    var productAdapter: ProductAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mFirestore = FirebaseFirestore.getInstance()

        getData()

        productRV.layoutManager = LinearLayoutManager(this)
        productAdapter = ProductAdapter(prodList)
        productRV.adapter = productAdapter
    }

    fun getData() {
        mFirestore?.collection("products")?.get()
            ?.addOnCompleteListener { task: Task<QuerySnapshot> ->
                if (task.isSuccessful) {
                    for (snapshot in task.getResult()!!) {
                        var currData = snapshot.toObject(Product::class.java)
                        prodList.add(currData)
                        Log.d("DATA", currData.name)
                    }
                    productAdapter?.notifyDataSetChanged()
                    Toast.makeText(this, "Done", LENGTH_SHORT).show()
                }
            }

    }

}
