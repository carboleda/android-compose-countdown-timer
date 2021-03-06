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
package com.example.androiddevchallenge.extensions

fun String.formatToTimeUnits(): String {
    val units = listOf('s', 'm', 'h')
    val strIn = this.padStart(6, '0')
    val strOut = mutableListOf<String>()

    for ((index, i) in (6 downTo 1 step 2).withIndex()) {
        strOut.add(0, "${strIn.subSequence(i - 2, i)}${units[index]}")
    }

    return strOut.joinToString(" ")
}

fun String.timeUnitsToMilliseconds(): Long {
    return try {
        var time = this.toLong()
        var milliseconds = 0L
        var index = 0
        val multiplies = listOf(1, 60, 60)

        while (time > 0) {
            milliseconds += (time % 100) * multiplies[index++]
            time /= 100
        }

        milliseconds * 1000
    } catch (error: Exception) {
        0
    }
}
