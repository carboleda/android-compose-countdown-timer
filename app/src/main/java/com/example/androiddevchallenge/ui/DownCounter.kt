package com.example.androiddevchallenge.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun DownCounter(model: TimerViewModel = viewModel()) {
    Row(modifier = Modifier.fillMaxWidth()) {
        val leftTime by model.leftTime.observeAsState()
        Text(text = "Time left: $leftTime")
        Button(onClick = { model.start(5000)}) {
            Text(text = "Start")
        }
    }
}

