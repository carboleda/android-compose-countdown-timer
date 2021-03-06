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

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.extensions.formatToTimeUnits
import com.example.androiddevchallenge.extensions.timeUnitsToMilliseconds
import com.example.androiddevchallenge.ui.theme.darkBlue700
import com.example.androiddevchallenge.util.Timer
import kotlin.math.round

@Composable
fun DownCounter(model: TimerViewModel = viewModel()) {
    val isRunning by model.isRunning.observeAsState(false)

    DefaultVisibilityAnimation(
        visible = isRunning
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ClockAnimation()
            Display(model, model::stop)
        }
    }

    if (!isRunning) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            SetupTimer(model::start)
        }
    }
}

@Composable
fun SetupTimer(onStart: (time: Long) -> Unit) {
    var time by rememberSaveable { mutableStateOf("") }
    InputTime(time) {
        time = if (it.isDigitsOnly() && it.length <= 6) it else time
    }

    Spacer(modifier = Modifier.height(10.dp))

    StyledButton(
        text = R.string.go,
        enabled = time.timeUnitsToMilliseconds() > 0,
        onClick = { onStart(time.timeUnitsToMilliseconds()) }
    )
}

@Composable
fun InputTime(value: String, onValueChange: (value: String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = stringResource(R.string.set_timer),
            style = MaterialTheme.typography.h5.copy(color = MaterialTheme.colors.primary)
        )
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusable(enabled = false),
            value = value,
            visualTransformation = TimeUnitTransformation(),
            onValueChange = { onValueChange(it) },
            textStyle = TextStyle(fontSize = 25.sp, color = Color.LightGray, textAlign = TextAlign.Center),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            cursorBrush = SolidColor(Color.Transparent),
            decorationBox = { innerTextField ->
                Row(
                    Modifier
                        .background(darkBlue700, RoundedCornerShape(percent = 8))
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    innerTextField()
                }
            }
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Display(model: TimerViewModel, onStop: () -> Unit) {
    val leftTimeUnits by model.leftTime.observeAsState(Timer.Units())

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = leftTimeUnits.format(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center
        )

        StyledButton(
            text = R.string.stop,
            onClick = { onStop() }
        )
    }
}

@Composable
fun ClockAnimation() {
    val infiniteTransition = rememberInfiniteTransition()
    val frame = infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 20f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2000,
                delayMillis = 1000,
                easing = LinearEasing
            )
        )
    )
    val frameIndex = round(frame.value).toInt()

    Image(
        painter = painterResource(id = ninjaFrames[frameIndex]!!),
        contentDescription = null,
        modifier = Modifier.scale(0.5f)
    )
}

@Composable
fun StyledButton(text: Int, enabled: Boolean = true, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = enabled,
    ) {
        Text(
            text = stringResource(text),
            style = MaterialTheme.typography.h6.copy(color = Color.White),
        )
    }
}

class TimeUnitTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val newText = AnnotatedString(text.text.formatToTimeUnits())
        return TransformedText(newText, OffsetMapping.Identity)
    }
}
