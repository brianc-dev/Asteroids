package com.alyx.asteroids.graphics

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import androidx.core.content.withStyledAttributes
import com.alyx.asteroids.R
import com.alyx.asteroids.toPx
import com.google.android.material.color.MaterialColors

open class AsteroidsButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): View(context, attrs, defStyleAttr) {

    protected val paint: Paint = Paint().apply {
        color = Color.WHITE
        textAlign = Paint.Align.CENTER
        textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PT, 11f, context.resources.displayMetrics)
        strokeWidth = borderStrokeWidth
    }

    var borderPadding = 5.toPx
    var borderStrokeWidth = 5f.toPx

    protected var text: String = "Button"
    private var color: Int = Color.GREEN

    init {
        isClickable = true
        context.withStyledAttributes(attrs, R.styleable.AsteroidsButton) {
            text = getString(R.styleable.AsteroidsButton_text) ?: ""
            color = getColor(R.styleable.AsteroidsButton_color, Color.GREEN)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        requireNotNull(canvas)

        paint.style = Paint.Style.STROKE
        canvas.drawRect(borderPadding, borderPadding, width - borderPadding, height - borderPadding, paint)
        paint.style = Paint.Style.FILL
        canvas.drawText(text, (width / 2f) + borderPadding, (height / 2f) + borderPadding, paint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)
        requireNotNull(event)
        paint.color = when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> color
            else -> Color.WHITE
        }
        invalidate()
        return true
    }
}