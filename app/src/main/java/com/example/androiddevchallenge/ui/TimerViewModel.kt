package com.example.androiddevchallenge.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androiddevchallenge.util.Timer

class TimerViewModel : ViewModel() {
    private val timer = Timer()
    private val _leftTime = MutableLiveData(Timer.Units())
    val leftTime: LiveData<Timer.Units> = _leftTime

    fun start(time: Long) {
        timer.start(time) {
            _leftTime.postValue(it)
        }
    }
}