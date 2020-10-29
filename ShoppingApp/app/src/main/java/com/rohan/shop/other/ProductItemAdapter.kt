package com.rohan.shop.other

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rohan.shop.R
import com.rohan.shop.data.db.entity.Product
import com.rohan.shop.ui.productlist.ProductViewModel
import kotlinx.android.synthetic.main.product_item.view.*

class ProductItemAdapter(
    var dataList: List<Product>,
    private val viewModel: ProductViewModel
) : RecyclerView.Adapter<ProductItemAdapter.ProductHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ProductHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        val currProduct = dataList[position]
        holder.itemView.tvName.text = currProduct.name
        holder.itemView.tvAmount.text = "${currProduct.amount}"
        holder.itemView.ivDelete.setOnClickListener {
            viewModel.delete(currProduct)
        }
        holder.itemView.ivPlus.setOnClickListener {
            currProduct.amount++
            viewModel.upsert(currProduct)
        }
        holder.itemView.ivMinus.setOnClickListener {

            if (currProduct.amount > 0) {
                currProduct.amount--
            } else {
                currProduct.amount = 0
            }

            viewModel.upsert(currProduct)
        }


    }

    inner class ProductHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }


}
