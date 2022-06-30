package com.alyx.asteroids.graphics

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class AsteroidButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    init {
        isClickable = true
    }

    private var pause = false

    private val paint = Paint().apply {
        isAntiAlias = false
        color = Color.WHITE
        textAlign = Paint.Align.CENTER
        strokeWidth = 2f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        paint.textSize = h / 4f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        requireNotNull(canvas)
        paint.style = Paint.Style.FILL
        val text = if (pause) {
            "PLAY"
        } else {
            "PAUSE"
        }
        canvas.drawText(text, width / 2f, (height / 2f) + height * 0.1f, paint)
        paint.style = Paint.Style.STROKE
        canvas.drawRect(height * 0.1f, width * 0.1f, height * 0.9f, width * 0.9f, paint)
    }

    override fun performClick(): Boolean {
        super.performClick()
        pause = !pause
        invalidate()
        return true
    }
}