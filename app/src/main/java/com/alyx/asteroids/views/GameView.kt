package com.alyx.asteroids.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.PathShape
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.alyx.asteroids.graphics.Graphics

private const val SHIP_TILT_STEP = 5
private const val SHIP_ACCELERATION_STEP = 0.5f

class GameView(context: Context, attrs: AttributeSet): View(context, attrs) {

    private val asteroids: MutableList<Graphics>
    private val asteroidsNumber = 5
    private val particlesNumber = 3

    // Ship
    private val ship: Graphics
    private val shipTilt: Int = 0
    private val shipAcceleration: Float = 0f

    init {
        val asteroidDrawable: Drawable
        val sharedPreferences = context.getSharedPreferences("com.alyx.asteroids_preferences", Context.MODE_PRIVATE)
        if (sharedPreferences.getString("graphics", "1").equals("0")) {
            val asteroidPath = Path()
            asteroidPath.moveTo(0.3f, 0.0f)
            asteroidPath.lineTo(0.6f, 0.0f)
            asteroidPath.lineTo(0.6f, 0.3f)
            asteroidPath.lineTo(0.8f, 0.2f)
            asteroidPath.lineTo(1.0f, 0.4f)
            asteroidPath.lineTo(0.8f, 0.6f)
            asteroidPath.lineTo(0.9f, 0.9f)
            asteroidPath.lineTo(0.8f, 1.0f)
            asteroidPath.lineTo(0.4f, 1.0f)
            asteroidPath.lineTo(0.0f, 0.6f)
            asteroidPath.lineTo(0.0f, 0.2f)
            asteroidPath.lineTo(0.3f, 0.0f)
            val asteroidD = ShapeDrawable(PathShape(asteroidPath, 1f, 1f))
            asteroidD.paint.color = Color.WHITE
            asteroidD.paint.style = Paint.Style.STROKE
            asteroidD.intrinsicWidth = 100
            asteroidD.intrinsicHeight = 100

            asteroidDrawable = asteroidD
            setBackgroundColor(Color.BLACK)
        } else {
            asteroidDrawable = ResourcesCompat.getDrawable(context.resources, android.R.drawable.star_on, null) ?: throw NoSuchElementException()//context.resources.getDrawable(android.R.drawable.star_on)
        }

        val shipDrawable: Drawable = ResourcesCompat.getDrawable(context.resources, android.R.drawable.arrow_up_float, null) ?: throw NoSuchElementException()
        val missile: Drawable

        this.ship = Graphics(this, shipDrawable)

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

        ship.posX = w / 2.0 - (ship.width / 2.0)
        ship.posY = h / 2.0 - (ship.height / 2.0)

        for (asteroid in asteroids) {
            do {
            asteroid.posX = Math.random() * (w - asteroid.width)
            asteroid.posY = Math.random() * (h - asteroid.height)
            } while (asteroid.distance(ship) < (w + h) / 5)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {

            ship.drawGraphic(it)

            for (asteroid in asteroids) {
                asteroid.drawGraphic(it)
            }
        }
    }
}