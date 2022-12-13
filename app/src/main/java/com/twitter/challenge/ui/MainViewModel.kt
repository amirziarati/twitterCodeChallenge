package com.twitter.challenge.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.twitter.challenge.dto.WeatherInfo
import com.twitter.challenge.repo.ServerException
import com.twitter.challenge.repo.WeatherRemote
import com.twitter.challenge.utils.WeatherHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val weatherRemote: WeatherRemote) : ViewModel() {


    private var weatherInfo: MutableLiveData<WeatherInfo> = MutableLiveData<WeatherInfo>()
    private var nextFiveDaysDeviation: MutableLiveData<Float> = MutableLiveData<Float>()
    private var error: MutableLiveData<ServerException> = MutableLiveData<ServerException>()


    fun getWeatherInfo(): LiveData<WeatherInfo> {
        //this condition makes the data survive config change
        //of course in a more robust app we need to call the api again if the day has changed
        if (weatherInfo.value == null)
            fetchWeatherInfo()
        return this.weatherInfo
    }

    fun getFiveDaysDeviation(): LiveData<Float> {
        //this condition makes the data survive config change
        //of course in a more robust app we need to call the api again if the day has changed
        if (nextFiveDaysDeviation.value == null)
            fetchFiveDayDeviation()
        return this.nextFiveDaysDeviation
    }

    fun getError(): LiveData<ServerException> {
        return this.error
    }

    private fun fetchWeatherInfo() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                weatherInfo.value = weatherRemote.getWeatherInfo()
            } catch (ex: ServerException) {
                error.value = ex
            }
        }

    }

    private fun fetchFiveDayDeviation() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val weatherInfos = weatherRemote.getNextFiveDaysWeather()
                nextFiveDaysDeviation.value =
                    WeatherHelper.deviationOfNumbers(weatherInfos.map { it.weather.temp })
            } catch (ex: ServerException) {
                error.value = ex
            }
        }
    }
}