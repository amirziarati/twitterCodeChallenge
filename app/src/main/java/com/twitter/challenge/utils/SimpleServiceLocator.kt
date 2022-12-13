package com.twitter.challenge.utils

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.twitter.challenge.BuildConfig
import com.twitter.challenge.repo.NetworkClient
import com.twitter.challenge.repo.WeatherRemoteImpl
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

/////////
//could also use a Dependency injection like Koin or Dagger
/////////
object SimpleServiceLocator {

    private var networkClient: NetworkClient

    init {


        var retrofit = Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .addConverterFactory(getConvertorFactory())
            .client(getHttpClient())
            .build()
        networkClient = retrofit.create(NetworkClient::class.java)
    }


    fun getWeatherRemoteImpl() = WeatherRemoteImpl(networkClient)

    private fun getHttpClient() = OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) {
            val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            this.addInterceptor(interceptor)
        }
    }.build()

    private fun getConvertorFactory() = Json.asConverterFactory("application/json".toMediaType())

    private fun getBaseUrl() = "https://twitter-code-challenge.s3.amazonaws.com/"
}