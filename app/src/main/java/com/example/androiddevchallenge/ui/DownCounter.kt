package com.example.androiddevchallenge.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import java.util.concurrent.TimeUnit

@Composable
fun DownCounter(model: TimerViewModel = viewModel()) {
    Column(modifier = Modifier.fillMaxWidth()) {
        val leftTime by model.leftTime.observeAsState(0L)

        val hours = TimeUnit.MILLISECONDS.toHours(leftTime)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(leftTime) - hours * 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(leftTime) % 60

        Text(text = "Time left: $hours:$minutes:$seconds")
        Button(onClick = { model.start(2 * 60 * 1000)}) {
            Text(text = "Start")
        }
    }
}

