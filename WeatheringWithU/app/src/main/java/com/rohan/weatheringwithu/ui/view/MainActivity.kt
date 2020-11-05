package com.rohan.weatheringwithu.ui.view

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.rohan.weatheringwithu.data.repository.MainRepository
import com.rohan.weatheringwithu.ui.view_model.MainViewModel
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_main.*

import com.rohan.weatheringwithu.R
import com.rohan.weatheringwithu.data.network.response.Weather
import egolabsapps.basicodemine.videolayout.VideoLayout
import kotlinx.android.synthetic.main.activity_main.search_progress
import kotlinx.android.synthetic.main.activity_main.videoLayout
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainRepository: MainRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        videoLayout.setGravity(VideoLayout.VGravity.start)
        videoLayout.setIsLoop(true)
        videoLayout.setSound(true)

        mainRepository = MainRepository()
        mainViewModel = getViewModel(mainRepository)

        search_btn.setOnClickListener {
            Log.d(
                "OPENW",
                "onCreate: " + "MainActivity searchWeather(${search_city_et.text.toString()})"
            )

            mainViewModel.searchWeather(search_city_et.text.toString())
            search_city_et.setText("")
        }

        mainViewModel.showLoading.observe(this, Observer {
            if (it) {
                search_progress.visibility = View.VISIBLE
            } else {
                search_progress.visibility = View.GONE
            }
        })

        mainViewModel.weatherResponse.observe(this, Observer {

            if (it == null) {
                weatherViewLL.visibility = View.GONE
                return@Observer
            }

            val date = getCurrentDateTime()
//            val dateInString = date.toString("EEEE, MMMM d, yyyy 'at' h:mm a")
            val dateInString = date.toString("EEEE, MMMM d")
            dateTV.text = dateInString
            val city: String = it.name + "," + it.sys.country
            val iconId: String = it.weather[0].icon
            val temp: String = it.main.temp.roundToInt().toString() + "°C"
            val weatherType: String = it.weather[0].main
//            val url: String = "http://openweathermap.org/img/wn/${iconId}@2x.png"
//            Log.d("OPENW", "onCreate: " + url)
            Glide.with(this).load("https://openweathermap.org/img/wn/${iconId}@2x.png")
                .apply(RequestOptions().override(120, 120))
                .into(weatherIconIMV)
            cityTV.text = city
            weatherTempTV.text = temp
            weatherTypeTV.text = weatherType

            //Data Loaded,Show WeatherDetails
            weatherViewLL.visibility = View.VISIBLE

        })


    }

    private fun getViewModel(
        repo: MainRepository
    ): MainViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel(repo) as T
            }
        })[MainViewModel::class.java]
    }

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }


}