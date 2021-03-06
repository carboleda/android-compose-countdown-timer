/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androiddevchallenge.util.Timer

class TimerViewModel : ViewModel() {
    private val timer = Timer()
    private val _leftTime = MutableLiveData(Timer.Units())
    val leftTime: LiveData<Timer.Units> = _leftTime
    private val _isRunning = MutableLiveData(false)
    val isRunning: LiveData<Boolean> = _isRunning
    var initialTime: Long = 0
        private set

    fun start(time: Long) {
        Log.d("TimerViewModel::start", "Time: $time")
        initialTime = time
        timer.start(time) {
            if (it == null) {
                _isRunning.value = false
            } else {
                _leftTime.value = it
            }
        }
        _isRunning.value = true
    }

    fun stop() {
        timer.stop()
        _isRunning.value = false
    }
}
