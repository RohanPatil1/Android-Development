package com.rohan.intern_task3.screens


import android.location.Location

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

import com.rohan.intern_task3.R
import com.rohan.intern_task3.adapter.RestaurantAdapter
import com.rohan.intern_task3.data_models.Restaurant
import kotlinx.android.synthetic.main.activity_main.*
import android.os.AsyncTask
import android.view.Menu
import android.widget.SearchView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.json.JSONArray
import java.lang.NullPointerException


class MainActivity : AppCompatActivity() {


    private lateinit var fusedLocationClient: FusedLocationProviderClient


    lateinit var adapter: RestaurantAdapter
    var lat: Double? = null
    var lng: Double? = null
    private val URL_CATEGORY = "https://developers.zomato.com/api/v2.1/categories"

    var restaurantDataList: MutableList<Restaurant> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
//        val asyncTask = AsynCtask()
//        asyncTask.execute()

//        getCityName()
        restaurantRV.layoutManager = LinearLayoutManager(this)

        adapter = RestaurantAdapter(restaurantDataList)
        restaurantRV.adapter = adapter


        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                // Got last known location. In some rare situations this can be null.
                lat = location?.latitude
                lng = location?.longitude
                mMsgView.setText("Locating...")
                val asynCtask =AsynCtask()
                asynCtask.execute()

}

        getCityRest("")
    }





    fun getCityRest(query:String) {
        var URL_RESTAURANTS: String?
        if(!query.equals("")){
            val builder: Uri.Builder = Uri.Builder()
            builder.scheme("https")
                .authority("developers.zomato.com")
                .appendPath("api")
                .appendPath("v2.1")
                .appendPath("search")
                .appendQueryParameter("q", query)
             URL_RESTAURANTS = builder.build().toString()
        }else{
            val builder: Uri.Builder = Uri.Builder()
            builder.scheme("https")
                .authority("developers.zomato.com")
                .appendPath("api")
                .appendPath("v2.1")
                .appendPath("search")

                .appendQueryParameter("entity_type", "metro")
                .appendQueryParameter("entity_type", "metro")
                .appendQueryParameter("lat", lat.toString())
                .appendQueryParameter("lon", lng.toString())
             URL_RESTAURANTS= builder.build().toString()

        }




        val jsonObjRequ = object : JsonObjectRequest(Request.Method.GET, URL_RESTAURANTS, null,
            Response.Listener { response ->


                Log.d("TAG","-----------------------------------")
                Log.d("TAG","Query ="+query)
                Log.d("TAG","URL ="+URL_RESTAURANTS)
                Log.d("TAG",response.toString())
                Log.d("TAG","-----------------------------------")

                val restaurantsArray = response.getJSONArray("restaurants")
                var photosArray:JSONArray?= null
                var img_url:String?=null

                for (i in 0..(restaurantsArray.length() - 1)) {
                    val restaurantObj =
                        restaurantsArray.getJSONObject(i).getJSONObject("restaurant")


                    if(!query.equals("")){
                        img_url="https://www.umam-dr.org/public/uploads/noimg.jpg"
                    }else{
                        photosArray=restaurantObj.getJSONArray("photos")
                        val photoObj = photosArray.getJSONObject(0).getJSONObject("photo")
                        img_url = photoObj.getString("url")
                    }

                    var id = restaurantObj.getInt("id")
                    var name = restaurantObj.getString("name")
                    var cuisines = restaurantObj.getString("cuisines")
                    var currRest =
                        Restaurant()
                    currRest.name = name
                    currRest.id = id
                    currRest.cuisine = cuisines
                    currRest.imgUrl = img_url
                    restaurantDataList.add(currRest)
                }
                adapter.notifyDataSetChanged()
                restaurantRV.adapter = adapter
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

    fun getCityName() {
        val builder: Uri.Builder = Uri.Builder()
        builder.scheme("https")
            .authority("developers.zomato.com")
            .appendPath("api")
            .appendPath("v2.1")
            .appendPath("cities")

            .appendQueryParameter("lat", lat.toString())
            .appendQueryParameter("lon", lng.toString())
        val URL_RESTAURANTS: String = builder.build().toString()


        val jsonObjRequ = object : JsonObjectRequest(Request.Method.GET, URL_RESTAURANTS, null,
            Response.Listener { response ->


                val locationArray = response.getJSONArray("location_suggestions")
                Log.d("TAG",response.toString())
                Log.d("TAG",locationArray.toString())

                val locationObj = locationArray.getJSONObject(0)
                val cityName = locationObj.getString("name")
                val country = locationObj.getString("country_name")
                mMsgView.setText(cityName+","+country)
                Log.d("city", cityName)


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


    inner class AsynCtask : AsyncTask<String, String, String>() {

        var resp: String? = null
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
//        mMsgView.setText("Lat="lat.toString())
        }

        override fun doInBackground(vararg p0: String?): String {
        getCityName()



            return ""
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        var menuInflator =menuInflater
        menuInflator.inflate(R.menu.menu,menu)

        var menuItem=menu?.findItem(R.id.search)
        val searchView:SearchView = menuItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {

                getCityRest(p0.toString())
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                adapter.filter.filter(p0)
                return false
            }
        })


        return super.onCreateOptionsMenu(menu)
    }
}