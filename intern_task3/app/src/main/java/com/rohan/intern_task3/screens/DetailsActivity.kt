package com.rohan.intern_task3.screens

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.rohan.intern_task3.R
import com.rohan.intern_task3.data_models.Restaurant
import com.rohan.intern_task3.data_models.RestaurantDetails
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.restaurant_layout.*
import kotlinx.android.synthetic.main.restaurant_layout.view.*

class DetailsActivity : AppCompatActivity() {


    var res_id: String? = null
    val resDetails = RestaurantDetails()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        res_id = intent.getStringExtra("res_id")
        getRestaurantDetails(res_id!!)
    }

    fun setData(restaurantDetails: RestaurantDetails) {
        detailsNameTV.text = restaurantDetails.name
        detailsAddressTV.text = "Address : " + restaurantDetails.address
        detailsCuisineTV.text = "Cuisines : " + restaurantDetails.cuisine
        detailsTimingTV.text = "Timings : " + restaurantDetails.timings
        detailsRatingValue.text = restaurantDetails.user_rating.toString()
        detailsRatingBar.rating = restaurantDetails.user_rating!!.toFloat()
        Glide.with(this).load(restaurantDetails.imgUrl)
            .into(detailsIMV)

    }


    fun getRestaurantDetails(res_id: String) {
        val builder: Uri.Builder = Uri.Builder()
        builder.scheme("https")
            .authority("developers.zomato.com")
            .appendPath("api")
            .appendPath("v2.1")
            .appendPath("restaurant")
            .appendQueryParameter("res_id", res_id)

        val URL_CITY1: String = builder.build().toString()


        val jsonObjRequ = object : JsonObjectRequest(
            Request.Method.GET, URL_CITY1, null,
            Response.Listener { response ->
                Log.d("Restaurant ID:", res_id)
                Log.d("Details", response.toString())
                val restaurantObj = response
                var id = restaurantObj.getString("id")
                var name = restaurantObj.getString("name")
                var cuisines = restaurantObj.getString("cuisines")
                var establishment = restaurantObj.getString("establishment")
                var timing = restaurantObj.getString("timings")


                val photosArray = restaurantObj.getJSONArray("photos")
                val photoObj = photosArray.getJSONObject(0).getJSONObject("photo")
                var img_url = photoObj.getString("url")


                var locationObj = restaurantObj.getJSONObject("location")
                var address = locationObj.getString("address")


                var userRatingObj = restaurantObj.getJSONObject("user_rating")
                var rating = userRatingObj.getString("aggregate_rating")



                resDetails.id = id
                resDetails.name = name
                resDetails.cuisine = cuisines
                resDetails.timings = timing
                resDetails.establishment = establishment
                resDetails.imgUrl = img_url
                resDetails.address = address
                resDetails.user_rating = rating



                setData(resDetails)

            },
            Response.ErrorListener { error ->
                Log.d("Zomato", error.message.toString())

            }) {

            // override getHeader for pass session to service
            override fun getHeaders(): MutableMap<String, String> {

                val header = mutableMapOf<String, String>()
                header.put("user-key", "967dc52fe56b334a7f5c0dda612175b1")

                return header
            }
        }
        Volley.newRequestQueue(this).add(jsonObjRequ)

    }
}
