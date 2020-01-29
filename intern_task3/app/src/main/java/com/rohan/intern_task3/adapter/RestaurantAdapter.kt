package com.rohan.intern_task3.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rohan.intern_task3.R
import com.rohan.intern_task3.data_models.Restaurant
import com.rohan.intern_task3.screens.DetailsActivity

import kotlinx.android.synthetic.main.restaurant_layout.view.*

class RestaurantAdapter(val restaurantDataList: MutableList<Restaurant>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {


    var restaurantDataListFilterd: MutableList<Restaurant> = restaurantDataList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.restaurant_layout, parent, false)
        return MyViewHolder(view)

    }

    override fun getItemCount(): Int {
        return restaurantDataList.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var viewHolder = (holder as MyViewHolder).itemView

        viewHolder.restaNameTV.text = restaurantDataList[position].name
        viewHolder.restaCuisinesTV.text = restaurantDataList[position].cuisine.toString()

        Glide.with(holder.itemView.context).load(restaurantDataList[position].imgUrl)
            .into(viewHolder.restaImgIMV)

        viewHolder.restaurantLayout.setOnClickListener { view ->
            val detailsIntent = Intent(view.context, DetailsActivity::class.java)
            detailsIntent.putExtra("res_id", restaurantDataList[position].id.toString())
            view.context.startActivity(detailsIntent)
        }

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            private val filterResults = FilterResults()
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                restaurantDataListFilterd.clear()
                if (constraint.isNullOrBlank()) {
                    restaurantDataListFilterd.addAll(restaurantDataList)
                } else {

                    val filterPattern=constraint.toString().toLowerCase().toString()

                    for(item:Restaurant in restaurantDataList){
                                if(item.name?.toLowerCase()!!.contains(filterPattern)){
                                            restaurantDataListFilterd.add(item)
                                }
                    }

                }
                var results=FilterResults()
                results.values=restaurantDataListFilterd
                return  results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                restaurantDataListFilterd.clear()
                restaurantDataListFilterd.addAll(results?.values as Collection<Restaurant>)
                notifyDataSetChanged()

            }
        }
    }

}