package com.alyx.asteroids.graphics

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import kotlin.math.hypot

class Graphics(
    private val view: View,
    private val drawable: Drawable
) {

    companion object {
        const val MAX_SPEED = 20
    }

    val width = drawable.intrinsicWidth
    val height = drawable.intrinsicHeight
    private val collisionRadio = (width + height) / 5

    var posX: Double = 0.0
    var posY: Double = 0.0
    var incX: Double = 0.0
    var incY: Double = 0.0
    var angle: Double = 0.0
    var rotation: Double = 0.0

    fun drawGraphic(canvas: Canvas) {
        canvas.save()
        val x = (posX + width / 2).toInt()
        val y = (posY + height / 2).toInt()
        canvas.rotate(angle.toFloat(), x.toFloat(), y.toFloat())
        drawable.setBounds(posX.toInt(), posY.toInt(), (posX + width).toInt(), (posY + height).toInt())
        drawable.draw(canvas)
        canvas.restore()
        view.invalidate()
    }

    fun incrementPos(factor: Double) {
        posX += incX * factor * 1.5
        if (posX < -width / 2.0) posX = view.width - width / 2.0
        if (posX > view.width - width / 2.0) posX = -width / 2.0
        posY += incY * factor * 1.5
        if (posY < -height / 2.0) posY = view.height - height / 2.0
        if (posY > view.height - height / 2.0) posY = -width / 2.0
        angle += rotation * factor * 1.2
    }

    fun distance(graphics: Graphics) = hypot(posX - graphics.posX, posY - graphics.posY)

    fun checkCollision(graphics: Graphics) = (distance(graphics) < (collisionRadio + graphics.collisionRadio))
}