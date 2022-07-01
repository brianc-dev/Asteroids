package com.alyx.asteroids.graphics

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import androidx.core.content.withStyledAttributes
import androidx.core.graphics.drawable.toBitmap
import com.alyx.asteroids.R

class AsteroidsFloatingButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AsteroidsButton(context, attrs, defStyleAttr) {

    private var icon: Bitmap? = null

    init {
        context.withStyledAttributes(attrs, R.styleable.AsteroidsFloatingButton) {
            val drawable = getDrawable(R.styleable.AsteroidsFloatingButton_icon)
            icon = drawable?.toBitmap()
        }

        paint.textSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            12f,
            context.resources.displayMetrics
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        icon?.let {
        icon = Bitmap.createScaledBitmap(it, (w * 0.7).toInt(), (h * 0.7).toInt(), false)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        requireNotNull(canvas)
        paint.style = Paint.Style.STROKE
        canvas.drawCircle(width / 2f, height / 2f, (width / 2f) - borderPadding, paint)
        icon?.let {
            canvas.drawBitmap(it, (width / 2f) - it.width / 2f, (height / 2f) - it.height / 2f, paint)
        }
    }
}