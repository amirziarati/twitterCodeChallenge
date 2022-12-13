package com.twitter.challenge.repo

import com.twitter.challenge.dto.WeatherInfo
import retrofit2.Response

class WeatherRemoteImpl(private val networkClient: NetworkClient) : WeatherRemote {
    override suspend fun getWeatherInfo(): WeatherInfo {
        val networkResponse: Response<WeatherInfo> =
            networkClient.fetchWeatherInfo()

        if (networkResponse.isSuccessful) {
            networkResponse.body()?.let {
                return it
            } ?: throw ServerException(networkResponse.code(), "response body is empty")
        } else {
            throw ServerException(networkResponse.code(), networkResponse.errorBody().toString())
        }
    }

    override suspend fun getNextFiveDaysWeather(): List<WeatherInfo> {
        val nextFiveDaysWeather = ArrayList<WeatherInfo>()
        for (i in 1..5) {
            val networkResponse: Response<WeatherInfo> =
                networkClient.fetchFutureWeather(i)

            if (networkResponse.isSuccessful) {
                networkResponse.body()?.let {
                    nextFiveDaysWeather.add(it)
                } ?: throw ServerException(networkResponse.code(), "response body is empty")
            } else {
                throw ServerException(networkResponse.code(),
                                      networkResponse.errorBody().toString())
            }
        }

        return nextFiveDaysWeather
    }
}