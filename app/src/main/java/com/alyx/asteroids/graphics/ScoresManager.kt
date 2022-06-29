package com.alyx.asteroids.graphics

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class ScoresManager(tSize: Float) {

    private val paint = Paint().apply {
        isAntiAlias = false
        color = Color.WHITE
        textSize =  tSize
    }
    private var score = 0
    private var scoreIncrement = 100

    fun drawScore(canvas: Canvas) {
        canvas.save()
        canvas.drawText("SCORE: $score", canvas.width / 10.0f, canvas.height / 10.0f, paint)
        canvas.restore()
    }

    fun incrementScore() {
        score += scoreIncrement
    }
}