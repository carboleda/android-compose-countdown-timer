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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.example.androiddevchallenge.R

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DefaultVisibilityAnimation(visible: Boolean, content: @Composable () -> Unit) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            initialOffsetY = { -40 }
        ) + expandVertically(
            expandFrom = Alignment.Top
        ) + fadeIn(initialAlpha = 0.3f),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        content()
    }
}

val ninjaFrames = mapOf(
    1 to R.drawable.ic_ninja_1,
    2 to R.drawable.ic_ninja_2,
    3 to R.drawable.ic_ninja_3,
    4 to R.drawable.ic_ninja_4,
    5 to R.drawable.ic_ninja_5,
    6 to R.drawable.ic_ninja_6,
    7 to R.drawable.ic_ninja_7,
    8 to R.drawable.ic_ninja_8,
    9 to R.drawable.ic_ninja_9,
    10 to R.drawable.ic_ninja_10,
    11 to R.drawable.ic_ninja_11,
    12 to R.drawable.ic_ninja_12,
    13 to R.drawable.ic_ninja_13,
    14 to R.drawable.ic_ninja_14,
    15 to R.drawable.ic_ninja_15,
    16 to R.drawable.ic_ninja_16,
    17 to R.drawable.ic_ninja_17,
    18 to R.drawable.ic_ninja_18,
    19 to R.drawable.ic_ninja_19,
    20 to R.drawable.ic_ninja_20,
)
