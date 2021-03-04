package com.example.androiddevchallenge.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androiddevchallenge.util.Timer

class TimerViewModel(private val timer: Timer) : ViewModel() {
    private val _leftTime = MutableLiveData(0L)
    val leftTime: LiveData<Long> = _leftTime

    fun start(time: Long) {
        timer.start(time) {
            _leftTime.postValue(it)
        }
    }
}