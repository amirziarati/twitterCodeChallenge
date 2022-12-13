package com.twitter.challenge.ui

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.twitter.challenge.R
import com.twitter.challenge.databinding.ActivityMainBinding
import com.twitter.challenge.utils.SimpleServiceLocator
import com.twitter.challenge.utils.WeatherHelper


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this, MainViewModelFactory(SimpleServiceLocator.getWeatherRemoteImpl())).get(
            MainViewModel::class.java)

        init()
    }

    private fun init() {
        if (!isOnline()) {
            Snackbar.make(binding.root,
                          "App needs internet connection to operate !!\n please connect to internet and restart the app !!",
                          Snackbar.LENGTH_INDEFINITE).show()
            return
        }
        viewModel.getWeatherInfo().observe(this, { weatherInfo ->

            val temp = weatherInfo.weather.temp
            val inFahrenheit = WeatherHelper.celsiusToFahrenheit(temp)
            updateTemperature(Temperature(temp.toInt(), inFahrenheit.toInt()))
            updateWindSpeed(weatherInfo.wind.speed)

            if (weatherInfo.clouds.cloudiness > 50)
                showCloudyIcon()
            else
                showSunnyIcon()

        })

        viewModel.getError().observe(this, {
            showError(it.error)
        })

        binding.btnFiveDays.setOnClickListener {
            if (isOnline()) {
                viewModel.getFiveDaysDeviation().observe(this, { temp ->
                    val inFahrenheit = WeatherHelper.celsiusToFahrenheit(temp)
                    showFiveDayWeatherInfo(Temperature(temp.toInt(), inFahrenheit.toInt()))
                })
            } else {
                Snackbar.make(binding.root,
                              "App needs internet connection to operate !!\n please connect to internet and restart the app !!",
                              Snackbar.LENGTH_INDEFINITE).show()
            }
        }
    }

    private fun updateTemperature(temperature: Temperature) {
        binding.tvTemperatureC.text = getString(R.string.temperatureC, temperature.inCelsius)
        binding.tvTemperatureF.text = getString(R.string.temperatureF, temperature.inFahrenheit)
    }

    private fun updateWindSpeed(windSpeed: Float) {
        binding.tvWindSpeed.text = getString(R.string.wind_speed, windSpeed)
    }


    private fun showFiveDayWeatherInfo(temperature: Temperature) {
        binding.fiveDayContainer.visibility = View.VISIBLE
        binding.tvFiveDayC.text = getString(R.string.temperatureC, temperature.inCelsius)
        binding.tvFiveDayF.text = getString(R.string.temperatureF, temperature.inFahrenheit)
    }

    private fun showError(error: String) {
        Snackbar.make(binding.root, error, Snackbar.LENGTH_LONG).show()
    }

    private fun showCloudyIcon() {
        binding.imgWeather.setImageResource(R.mipmap.cloudy)
    }

    private fun showSunnyIcon() {
        binding.imgWeather.setImageResource(R.mipmap.cloudy)
    }

    private fun isOnline(): Boolean {
        val connectivityManager =
            getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        Log.d("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        Log.d("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        Log.d("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                        return true
                    }
                }
            }
        }
        return false
    }
}