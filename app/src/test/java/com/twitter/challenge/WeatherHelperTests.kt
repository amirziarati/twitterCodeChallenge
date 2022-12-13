package com.twitter.challenge

import com.twitter.challenge.utils.WeatherHelper
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Java6Assertions
import org.junit.Assert.assertThrows
import org.junit.Test
import org.junit.rules.ExpectedException

import org.junit.Rule
import org.junit.function.ThrowingRunnable


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class WeatherHelperTests {
    @Test
    fun testCelsiusToFahrenheitConversion() {
        val precision = Java6Assertions.within(0.01f)
        assertThat(WeatherHelper.celsiusToFahrenheit(-50f)).isEqualTo(-58f, precision)
        assertThat(WeatherHelper.celsiusToFahrenheit(0f)).isEqualTo(32f, precision)
        assertThat(WeatherHelper.celsiusToFahrenheit(10f)).isEqualTo(50f, precision)
        assertThat(WeatherHelper.celsiusToFahrenheit(21.11f)).isEqualTo(70f, precision)
        assertThat(WeatherHelper.celsiusToFahrenheit(37.78f)).isEqualTo(100f, precision)
        assertThat(WeatherHelper.celsiusToFahrenheit(100f)).isEqualTo(212f, precision)
        assertThat(WeatherHelper.celsiusToFahrenheit(1000f)).isEqualTo(1832f, precision)
    }



    @Test
    fun testDeviationOfNumbers() {
        val precision = Java6Assertions.within(0.001f)

        assertThat(WeatherHelper.deviationOfNumbers(arrayListOf(10f,12f,16f,21f,23f,23f,23f,16f))).isEqualTo(5.237f, precision)
        assertThat(WeatherHelper.deviationOfNumbers(arrayListOf(10f,12f,21f,23f,23f,16f))).isEqualTo(5.683f, precision)
        assertThat(WeatherHelper.deviationOfNumbers(arrayListOf(10f,12f,16f))).isEqualTo(3.055f, precision)
        assertThat(WeatherHelper.deviationOfNumbers(arrayListOf(0f,-1f,1f))).isEqualTo(1f, precision)
        assertThat(WeatherHelper.deviationOfNumbers(arrayListOf(0f,-1f,1f,-30f))).isEqualTo(15.022f, precision)
        assertThat(WeatherHelper.deviationOfNumbers(arrayListOf(1f,0f))).isEqualTo(0.707f, precision)

        assertThrows(ArithmeticException::class.java) { WeatherHelper.deviationOfNumbers(arrayListOf(1f)) }
    }
}