package com.alyx.asteroids.graphics

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.CountDownTimer

class TimeManager(tSize: Float) {
    private val paint: Paint = Paint().apply {
        isAntiAlias = false
        color = Color.WHITE
        textSize =  tSize
        textAlign = Paint.Align.RIGHT
    }

    private var timeLimit: Long = 30000
    private var time: Long = 0

    fun drawTime(canvas: Canvas) {
        val millisecond = ((timeLimit - time) % 1000)
        val second = ((timeLimit - time) / 1000) % 60
        val timeToDisplay = "TIME: $second:${millisecond.toString().take(1)}"
        canvas.save()
        canvas.drawText(timeToDisplay, canvas.width - canvas.width / 10f, canvas.height / 10f, paint)
        canvas.restore()
    }

    fun countTime(time: Long) {
        this.time += time
    }
}