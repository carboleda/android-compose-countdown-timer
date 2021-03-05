package com.example.androiddevchallenge.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
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
import com.example.androiddevchallenge.extensions.formatToTimeUnits
import com.example.androiddevchallenge.extensions.timeUnitsToMilliseconds
import com.example.androiddevchallenge.util.Timer

@Composable
fun DownCounter(model: TimerViewModel = viewModel()) {
    Column {
        Display(model)

        SetupArea(model)
    }
}

@Composable
fun Display(model: TimerViewModel) {
    val leftTimeUnits by model.leftTime.observeAsState(Timer.Units())

    Text(text = "Time left: ${leftTimeUnits.format()}")
}

@Composable
fun SetupArea(model: TimerViewModel) {
    var time by rememberSaveable { mutableStateOf("") }
    SetTime(time) {
        time = if(it.isDigitsOnly() && it.length <= 6) it else time
    }

    Spacer(modifier = Modifier.height(10.dp))

    Button(
        enabled = time.timeUnitsToMilliseconds() > 0,
        onClick = {
            Log.d("DownCounter", "Time: $time, Milli: ${time.timeUnitsToMilliseconds()}")
            model.start(time.timeUnitsToMilliseconds())
        }
    ) {
        Text(text = "Start")
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
            visualTransformation = MyTransformation(),
            onValueChange = { onValueChange(it) },
            textStyle = TextStyle(fontSize = 25.sp, color = Color.Magenta, textAlign = TextAlign.Center),
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

class MyTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val newText = AnnotatedString(text.text.formatToTimeUnits())
        return TransformedText(newText, OffsetMapping.Identity)
    }
}