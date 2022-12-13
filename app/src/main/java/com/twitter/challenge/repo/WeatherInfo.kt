package com.twitter.challenge.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherInfo(val coord: Coord,
                       val weather: Weather,
                       val wind: Wind,
                       val rain: Rain,
                       val clouds: Clouds,
                       val name: String)

@Serializable
data class Weather(val temp: Float,
                   val pressure: Int,
                   val humidity: Int
)

@Serializable
data class Coord(val lon: Double,
                 val lat: Double
)

@Serializable
data class Rain(@SerialName("3h") val threeH: Int
)

@Serializable
data class Wind(val speed: Float,
                val deg: Int
)

@Serializable
data class Clouds(val cloudiness: Int
)