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
package com.example.androiddevchallenge.util

import android.os.CountDownTimer
import java.util.concurrent.TimeUnit

class Timer {
    private var mTimer: CountDownTimer? = null
    data class Units(
        val leftTime: Long = 0,
        val hours: Long = 0L,
        val minutes: Long = 0L,
        val seconds: Long = 0L
    ) {
        fun format(): String {
            val hours = hours.toString().padStart(2, '0')
            val minutes = minutes.toString().padStart(2, '0')
            val seconds = seconds.toString().padStart(2, '0')
            return "$hours:$minutes:$seconds"
        }
    }

    fun start(
        time: Long,
        callback: (millisUntilFinished: Units?) -> Unit
    ) {
        stop()

        mTimer = object : CountDownTimer(time, Constants.DEFAULT_TIMER_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                val hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished)
                val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - hours * 60
                val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60

                callback.invoke(Units(millisUntilFinished, hours, minutes, seconds))
            }

            override fun onFinish() {
                mTimer = null
                callback.invoke(null)
            }
        }.start()
    }

    fun stop() {
        if (mTimer == null) {
            return
        }

        mTimer?.cancel()
        mTimer = null
    }
}
