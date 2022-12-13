package com.twitter.challenge.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.twitter.challenge.repo.WeatherRemote

class MainViewModelFactory(
    private val weatherRemote: WeatherRemote
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return MainViewModel(weatherRemote) as T
    }

}