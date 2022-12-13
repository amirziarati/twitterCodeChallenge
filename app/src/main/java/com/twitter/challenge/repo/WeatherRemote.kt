package com.twitter.challenge.repo

import com.twitter.challenge.dto.WeatherInfo

interface WeatherRemote {
    suspend fun getWeatherInfo(): WeatherInfo
    suspend fun getNextFiveDaysWeather(): List<WeatherInfo>
}