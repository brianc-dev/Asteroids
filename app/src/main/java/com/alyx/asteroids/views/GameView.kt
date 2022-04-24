package com.alyx.asteroids.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.alyx.asteroids.graphics.Graphics

class GameView(context: Context, attrs: AttributeSet): View(context, attrs) {

    private val asteroids: MutableList<Graphics>
    private val asteroidsNumber = 5
    private val particlesNumber = 3

    init {
        val ship: Drawable
        val asteroidDrawable: Drawable = ResourcesCompat.getDrawable(context.resources, android.R.drawable.star_on, null) ?: throw NoSuchElementException()//context.resources.getDrawable(android.R.drawable.star_on)
        val missile: Drawable

        asteroids = mutableListOf()

        for (i in 0 until asteroidsNumber) {
            val asteroid = Graphics(this, asteroidDrawable)
            asteroid.incY = Math.random() * 4 - 2
            asteroid.incX = Math.random() * 4 - 2
            asteroid.angle = Math.random() * 360
            asteroid.rotation = Math.random() * 8 - 4
            asteroids.add(asteroid)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        for (asteroid in asteroids) {
            asteroid.posX = Math.random() * (w - asteroid.width)
            asteroid.posY = Math.random() * (h - asteroid.height)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            for (asteroid in asteroids) {
                asteroid.drawGraphic(it)
            }
        }
    }
}