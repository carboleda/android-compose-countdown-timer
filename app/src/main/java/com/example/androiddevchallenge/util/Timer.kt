package com.example.androiddevchallenge.util

import android.os.CountDownTimer

class Timer {
    private var mTimer: CountDownTimer? = null

    fun start(
        time: Long, callback: (millisUntilFinished: Long) -> Unit
    ) {
        stop()

        mTimer = object : CountDownTimer(time, Constants.DEFAULT_TIMER_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                callback.invoke(millisUntilFinished)
            }

            override fun onFinish() {
                callback.invoke(0)
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