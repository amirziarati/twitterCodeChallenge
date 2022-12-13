package com.twitter.challenge.utils

import kotlin.math.pow
import kotlin.math.sqrt

object WeatherHelper {
    fun celsiusToFahrenheit(temperatureInCelsius: Float): Float {
        return temperatureInCelsius * 1.8f + 32
    }

    fun deviationOfNumbers(numbers: List<Float>): Float {
        if (numbers.size < 2) {
            throw ArithmeticException("there needs to be at least two numbers")
        }
        var tempSum = 0f
        for (num: Float in numbers) {
            tempSum += num
        }
        val tempAverage = tempSum / numbers.size

        var sigma = 0f
        for (num: Float in numbers) {
            sigma += (num - tempAverage).pow(2f)
        }

        return sqrt(sigma / (numbers.size - 1))
    }
}