package com.example.androiddevchallenge.util

import android.os.CountDownTimer
import java.util.concurrent.TimeUnit

class Timer {
    private var mTimer: CountDownTimer? = null
    data class Units(
        val leftTime: Long = 0, val hours: Long = 0L, val minutes: Long = 0L, val seconds: Long = 0L
    ) {
        fun format(): String {
            val hours = hours.toString().padStart(2, '0')
            val minutes = minutes.toString().padStart(2, '0')
            val seconds = seconds.toString().padStart(2, '0')
            return "$hours:$minutes:$seconds"
        }
    }

    fun start(
        time: Long, callback: (millisUntilFinished: Units?) -> Unit
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