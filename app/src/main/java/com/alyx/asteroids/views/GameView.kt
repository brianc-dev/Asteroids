package com.alyx.asteroids.views

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.IntDef
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.alyx.asteroids.R
import com.alyx.asteroids.graphics.Graphics
import com.alyx.asteroids.graphics.ScoresManager
import com.alyx.asteroids.graphics.TimeManager
import kotlinx.coroutines.*
import java.util.*
import kotlin.math.*

private const val SHIP_TILT_STEP = 5
private const val SHIP_ACCELERATION_STEP = 0.5f

class GameView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    companion object {
        private const val EXECUTION_PERIOD = 50
        private const val MISSILE_SPEED_STEP = 18

        @IntDef(EASY, MEDIUM, HARD)
        @Retention(AnnotationRetention.SOURCE)
        annotation class Difficulty

        // Difficulties
        private const val EASY = 1
        private const val MEDIUM = 2
        private const val HARD = 3

    }

    @Difficulty
    private var difficulty: Int = EASY

    private val difficultyFactor: Double
    get() {
        return when (difficulty) {
            EASY -> 1.0
            MEDIUM -> 2.5
            HARD -> 5.0
            else -> 1.0
        }
    }

    // Pause flag
    private var pause: Boolean = false

    // Scores
    private var scores = 0

    private val parentActivity: Activity?
        get() {
            while (context is ContextWrapper) {
                if (context is Activity) {
                    return context as Activity
                }
            }
            return null
        }

    private lateinit var scoresManager: ScoresManager
    private lateinit var timeManager: TimeManager

    // Sound
    private val soundPool: SoundPool
    private val missileSoundId: Int
    private val explosionSoundId: Int

    // Missile
    private lateinit var missile: Graphics
    private var missileActive = false
    private var missileTime = 0

    private var mX = 0
    private var mY = 0
    private var shot = false

    private val coroutine = CoroutineScope(Dispatchers.Default)
    private var job: Job? = null
//    val thread: GameThread = GameThread()
    private var lastProcess: Long = 0L

//    private val asteroids: Vector<Graphics>
    private val asteroids: Vector<Graphics>

    private val asteroidsNumber = 5
    private val particlesNumber = 2

    private val asteroidDrawable: ArrayList<Drawable> = ArrayList(2)

    // Ship
    private lateinit var ship: Graphics
    private var shipTilt: Int = 0
    private var shipAcceleration: Float = 0f

    init {

        // Load sound FX
        val audioAttributes = AudioAttributes.Builder().apply {
            setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            setUsage(AudioAttributes.USAGE_GAME)
        }.build()
        soundPool = SoundPool.Builder().setMaxStreams(5).setAudioAttributes(audioAttributes).build()
        explosionSoundId = soundPool.load(context, R.raw.explosion, 0)
        missileSoundId = soundPool.load(context, R.raw.missile, 0)

//        val asteroidDrawable: Drawable
        val sharedPreferences = context.getSharedPreferences("com.alyx.asteroids_preferences", Context.MODE_PRIVATE)
//        if (sharedPreferences.getString("graphics", "1").equals("0")) {
//            val asteroidPath = Path()
//            asteroidPath.moveTo(0.3f, 0.0f)
//            asteroidPath.lineTo(0.6f, 0.0f)
//            asteroidPath.lineTo(0.6f, 0.3f)
//            asteroidPath.lineTo(0.8f, 0.2f)
//            asteroidPath.lineTo(1.0f, 0.4f)
//            asteroidPath.lineTo(0.8f, 0.6f)
//            asteroidPath.lineTo(0.9f, 0.9f)
//            asteroidPath.lineTo(0.8f, 1.0f)
//            asteroidPath.lineTo(0.4f, 1.0f)
//            asteroidPath.lineTo(0.0f, 0.6f)
//            asteroidPath.lineTo(0.0f, 0.2f)
//            asteroidPath.lineTo(0.3f, 0.0f)
//            val asteroidD = ShapeDrawable(PathShape(asteroidPath, 1f, 1f))
//            asteroidD.paint.color = Color.WHITE
//            asteroidD.paint.style = Paint.Style.STROKE
//            asteroidD.intrinsicWidth = 100
//            asteroidD.intrinsicHeight = 100
//
//            asteroidDrawable = asteroidD
//            setBackgroundColor(Color.BLACK)
//
//            // Initialize missile
//            val missileD = ShapeDrawable(RectShape())
//            missileD.paint.color = Color.WHITE
//            missileD.paint.style = Paint.Style.STROKE
//            missileD.intrinsicWidth = 15
//            missileD.intrinsicHeight = 3
//            missileDrawable = missileD
//
//        } else {
//            asteroidDrawable =
//                ResourcesCompat.getDrawable(context.resources,
//                    R.drawable.tier3_asteroid,
//                    null)
//                    ?: throw NoSuchElementException()
//        }
        when (sharedPreferences.getString("difficulty", null)) {
            "easy" -> difficulty = EASY
            "medium" -> difficulty = MEDIUM
            "hard" -> difficulty = HARD
        }

        // Missile initialize
//        missileDrawable = ResourcesCompat.getDrawable(
//            context.resources,
//            R.drawable.missile_1,
//            null
//        ) ?: throw NoSuchElementException()

//        this.missile = Graphics(this, missileDrawable)

        // Ship initialize
//        val shipDrawable: Drawable =
//            ResourcesCompat.getDrawable(context.resources, R.drawable.ship_1, null) ?: throw NoSuchElementException()
//        val shipBitmap = shipDrawable.toBitmap(100, 100, null)
//        this.ship = Graphics(this, shipBitmap.toDrawable(resources))

//        asteroids = Vector(asteroidsNumber)
        // Small size asteroid
        asteroids = Vector()
//        for (i in 0 until asteroidsNumber) {
//            val asteroid = Graphics(this, asteroidDrawable)
//            asteroid.incY = Math.random() * 4 - 2 * difficultyFactor
//            asteroid.incX = Math.random() * 4 - 2 * difficultyFactor
//            asteroid.angle = Math.random() * 360
//            asteroid.rotation = Math.random() * 8 - 4
//            asteroids.add(asteroid)
//        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        this.setBackgroundResource(R.drawable.bg_1)

        scoresManager = ScoresManager(h * 0.05f)
        timeManager = TimeManager(h * 0.05f)

        // Set ship
        val shipDrawable = ResourcesCompat.getDrawable(resources, R.drawable.ship_1, null) ?: throw NoSuchElementException("Couldn't get ship resource")
        val shipWidthAndHeight = h / 20
        val shipBitmap = shipDrawable.toBitmap()
        val d = BitmapDrawable(resources, Bitmap.createScaledBitmap(shipBitmap, shipWidthAndHeight, shipWidthAndHeight, false))
        d.setTargetDensity(resources.displayMetrics)
        ship = Graphics(this, d)

        // Set asteroids
        var asteroidDrawable: Drawable
        var asteroidBitmap: Bitmap
        var a: BitmapDrawable

        asteroidDrawable = ResourcesCompat.getDrawable(resources, R.drawable.tier3_asteroid, null) ?: throw NoSuchElementException("Asteroid Drawable not found")
        asteroidBitmap = asteroidDrawable.toBitmap()
        a = BitmapDrawable(resources, Bitmap.createScaledBitmap(asteroidBitmap, shipWidthAndHeight * 2, shipWidthAndHeight * 2, false))
        this.asteroidDrawable.add(a)

        asteroidDrawable = ResourcesCompat.getDrawable(resources, R.drawable.tier3_asteroid2, null) ?: throw NoSuchElementException("Asteroid Drawable not found")
        asteroidBitmap = asteroidDrawable.toBitmap()
        a = BitmapDrawable(resources, Bitmap.createScaledBitmap(asteroidBitmap, shipWidthAndHeight * 3, shipWidthAndHeight * 3, false))
        this.asteroidDrawable.add(a)

        for (i in 0 until asteroidsNumber) {
            val asteroidD = this.asteroidDrawable[0]
            val asteroid = Graphics(this, asteroidD)
            asteroid.incY = Math.random() * 4 - 2 * difficultyFactor
            asteroid.incX = Math.random() * 4 - 2 * difficultyFactor
            asteroid.angle = Math.random() * 360
            asteroid.rotation = Math.random() * 8 - 4
            asteroids.add(asteroid)
        }

        ship.posX = w / 2.0 - (ship.width / 2.0)
        ship.posY = h / 2.0 - (ship.height / 2.0)

        for (asteroid in asteroids) {
            do {
                asteroid.posX = Math.random() * (w - asteroid.width)
                asteroid.posY = Math.random() * (h - asteroid.height)
            } while (asteroid.distance(ship) < (w + h) / 5)
        }

        // Set missile
        val missileDrawable = ResourcesCompat.getDrawable(context.resources, R.drawable.missile_1, null) ?: throw NoSuchElementException()
        val missileBitmap = missileDrawable.toBitmap()
        val m = BitmapDrawable(resources, Bitmap.createScaledBitmap(missileBitmap, shipWidthAndHeight / 2, shipWidthAndHeight / 2, false))

        this.missile = Graphics(this, m)

//        for (asteroid in asteroids) {
//            do {
//                asteroid.posX = Math.random() * (w - asteroid.width)
//                asteroid.posY = Math.random() * (h - asteroid.height)
//            } while (asteroid.distance(ship) < (w + h) / 5)
//        }

        lastProcess = System.currentTimeMillis()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)
        requireNotNull(event)
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                shot = true
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = abs(x - mX)
                val dy = abs(y - mY)

                if (dy < 6 && dx > 6) {
                    shipTilt = ((x - mX) / 2.0).roundToInt()//round((x - mX) / 2.0).toInt()
                    shot = false
                } else if (dx < 6 && dy > 6) {
                    shipAcceleration = ((mY - y) / 25.0).roundToInt().toFloat()//round((mY - y) / 25.0F)
                    shot = false
                }
            }
            MotionEvent.ACTION_UP -> {
                shipTilt = 0
                shipAcceleration = 0F
                if (shot) {
                    launchMissile()
                }
            }
        }
        mX = x.toInt(); mY = y.toInt()
        return true
    }

    @Synchronized
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {

            ship.drawGraphic(it)

            // Draw asteroids
            for (asteroid in asteroids) {
                asteroid.drawGraphic(it)
            }

            if (missileActive) {
                missile.drawGraphic(it)
            }

            // Draw score
            scoresManager.drawScore(it)
            // Draw time
            timeManager.drawTime(it)
        }
    }

    @Synchronized
    private fun updatePhysics() {

        if (pause) run {lastProcess = System.currentTimeMillis(); pause = false }
        // Calculate delta time
        val now = System.currentTimeMillis()

        if (lastProcess + EXECUTION_PERIOD > now) {
            return
        }
        val deltaT = now - lastProcess
        timeManager.countTime(deltaT)
        val delay: Double = (deltaT) / EXECUTION_PERIOD.toDouble()
        lastProcess = now

        // Calculate ship position
        ship.angle = ship.angle + shipTilt * delay
        val nIncX = ship.incX + shipAcceleration * cos(Math.toRadians(ship.angle)) * delay
        val nIncY = ship.incY + shipAcceleration * sin(Math.toRadians(ship.angle)) * delay

        if (hypot(nIncX, nIncY) <= Graphics.MAX_SPEED) {
            ship.incX = nIncX
            ship.incY = nIncY
        }

        ship.incrementPos(delay)

        // Calculate asteroids position

        for (asteroid in asteroids) {
            asteroid.incrementPos(delay)
        }

        if (missileActive) {
            missile.incrementPos(delay)
//            missileTime -= delay.toInt()
            if (isMissileOutscreen()) {
                missileActive = false
            } else {

                val iterator = asteroids.iterator()

//                for (asteroid in tier3Iterator) {
//                    if (missile.checkCollision(asteroid)) {
////                        destroyAsteroid(tier3Iterator, asteroid)
//                        val newAsteroids = divideAsteroid(tier3Iterator, asteroid)
//                        tier2Asteroid.addAll(newAsteroids)
//                    }
//                }
//
//                for (asteroid in tier2Iterator) {
//                    if (missile.checkCollision(asteroid)) {
////                        destroyAsteroid(tier2Iterator, asteroid)
//                        val newAsteroids = divideAsteroid(tier2Iterator, asteroid)
//                        tier1Asteroid.addAll(newAsteroids)
//                    }
//                }

                for (asteroid in iterator) {
                    if (missile.checkCollision(asteroid)) {
                        destroyAsteroid(iterator)
                    }
                }

//                val asteroidsIterator = asteroids.iterator()
//                for (asteroid in asteroidsIterator) {
//                    if (missile.checkCollision(asteroid)) {
//                        destroyAsteroid(asteroidsIterator, null)
//                        break
//                    }
//                }
            }
        }

        // Check collision with ship
        for (asteroid in asteroids) {
            if (asteroid.checkCollision(ship)) exit()
        }
    }

    private fun destroyAsteroid(iterator: MutableIterator<Graphics>) {
        iterator.remove()
        missileActive = false
        soundPool.play(explosionSoundId, 1f, 1f, 0, 0, 1f)
        scoresManager.incrementScore()
        if (asteroids.isNotEmpty()) return
        exit()
    }

    private fun divideAsteroid(iterator: MutableIterator<Graphics>, destroyedAsteroid: Graphics): MutableList<Graphics> {
        iterator.remove()
        missileActive = false
        soundPool.play(explosionSoundId, 1f, 1f, 0, 0, 1f)
        val particles = mutableListOf<Graphics>()
        for (i in 0 until particlesNumber) {
            val asteroidDrawable = asteroidDrawable[0]
            val asteroid = Graphics(this, asteroidDrawable)
            asteroid.posX = destroyedAsteroid.posX
            asteroid.posY = destroyedAsteroid.posY
            asteroid.incY = Math.random() * 4 - 2 * difficultyFactor
            asteroid.incX = Math.random() * 4 - 2 * difficultyFactor
            asteroid.angle = Math.random() * 360
            asteroid.rotation = Math.random() * 8 - 4
            particles.add(asteroid)
        }
        return particles
    }

    private fun launchMissile() {
        if (missileActive) return
        missile.posX = ship.posX + ship.width / 2.0 - missile.width / 2.0
        missile.posY = ship.posY + ship.height / 2.0 - missile.height / 2.0
        missile.angle = ship.angle
        missile.incX = cos(Math.toRadians(missile.angle)) * MISSILE_SPEED_STEP
        missile.incY = sin(Math.toRadians(missile.angle)) * MISSILE_SPEED_STEP
        missileActive = true
        soundPool.play(missileSoundId, 1.5f, 1.5f, 0, 0, 1f)
    }

    private fun isMissileOutscreen(): Boolean {
        return missile.posX < x || missile.posY < y || missile.posX > width || missile.posY > height
    }

    private fun exit() {
        val activity = parentActivity
        val bundle = Bundle()
        bundle.putInt("score", scores)
        val intent = Intent().apply {
            putExtras(bundle)
        }
            activity?.let {
                it.setResult(Activity.RESULT_OK, intent)
                it.finish()
            }
    }

    fun resumeCoroutine() {
        job = coroutine.launch {
            delay(1000)
            while (isActive) {
                updatePhysics()
            }
        }
    }

    fun stopCoroutine() {
        coroutine.launch {
            pause = true
            job?.cancelAndJoin()
        }
    }
}