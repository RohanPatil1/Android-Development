package com.rohan.interntask4.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rohan.interntask4.R
import com.rohan.interntask4.data_models.Product
import kotlinx.android.synthetic.main.product_list_item.view.*

class ProductAdapter(val prodList:MutableList<Product>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.product_list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return prodList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var viewHolder = (holder as MyViewHolder).itemView

        viewHolder.prodName.text = prodList[position].name
        viewHolder.prodPrice.text ="₹"+ prodList[position].price.toString()


    }

}