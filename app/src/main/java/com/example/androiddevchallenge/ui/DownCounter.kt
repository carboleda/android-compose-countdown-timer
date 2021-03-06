package com.example.androiddevchallenge.ui

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
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
import com.example.androiddevchallenge.ui.theme.darkblue700
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

    DefaultVisibilityAnimation(visible = !isRunning) {
        SetupArea(model::start)
    }
}

@Composable
fun SetupArea(onStart: (time: Long) -> Unit) {
    var time by rememberSaveable { mutableStateOf("") }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SetTime(time) {
            time = if(it.isDigitsOnly() && it.length <= 6) it else time
        }

        Spacer(modifier = Modifier.height(10.dp))

        StyledButton(
            text = R.string.go,
            enabled = time.timeUnitsToMilliseconds() > 0,
            onClick = { onStart(time.timeUnitsToMilliseconds()) }
        )
    }
}

@Composable
fun SetTime(value: String, onValueChange: (value: String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusable(enabled = false),
            value = value,
            visualTransformation = TimeUnitTransformation(),
            onValueChange = { onValueChange(it) },
            textStyle = TextStyle(fontSize = 25.sp, color = darkblue700, textAlign = TextAlign.Center),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            cursorBrush = SolidColor(Color.Transparent),
            decorationBox = { innerTextField ->
                Row(
                    Modifier
                        .background(Color.LightGray, RoundedCornerShape(percent = 8))
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
        // Log.d("Display", "${((leftTimeUnits.leftTime*100)/model.initialTime)}")
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

    Log.d("Animation", "degrees: $frameIndex")
    Image(
        painter = painterResource(id = ninjaFrames[frameIndex]!!),
        contentDescription = null,
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
            style = MaterialTheme.typography.h6,
        )
    }
}

class TimeUnitTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val newText = AnnotatedString(text.text.formatToTimeUnits())
        return TransformedText(newText, OffsetMapping.Identity)
    }
}