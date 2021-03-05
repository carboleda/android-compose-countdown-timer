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
            milliseconds += (time%100) * multiplies[index++]
            time /= 100
        }

        milliseconds * 1000
    } catch (error: Exception) {
        0
    }
}