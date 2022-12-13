package com.twitter.challenge.repo

import com.twitter.challenge.dto.WeatherInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkClient {

    @GET(value = "current.json")
    suspend fun fetchWeatherInfo(): Response<WeatherInfo>

    @GET(value = "future_{daysFromToday}.json")
    suspend fun fetchFutureWeather(@Path(value = "daysFromToday") daysFromToday: Int): Response<WeatherInfo>
}