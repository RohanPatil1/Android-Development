package com.rohan.intern_task3.data_models

data class RestaurantDetails(

    var name: String? = null,
    var id: String? = null,
    var cuisine: String? = null,
    var imgUrl: String? = null,
    var address: String? = null,

    var user_rating: String? = null,
    var establishment: String? = null,
    var timings: String? = null

) {}