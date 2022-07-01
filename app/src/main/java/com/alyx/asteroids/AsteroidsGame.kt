package com.alyx.asteroids

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.alyx.asteroids.graphics.AsteroidButton
import com.alyx.asteroids.sounds.GameMusicService
import com.alyx.asteroids.views.GameView

class AsteroidsGame : Activity() {

    private lateinit var gameView: GameView
    private lateinit var pauseButton: AsteroidButton

    private var music = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.asteroids_game)
        gameView = findViewById(R.id.gameView)
        pauseButton = findViewById(R.id.pauseButton)
        pauseButton.setOnClickListener {
            togglePause()
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(
            window,
            findViewById<View>(android.R.id.content).rootView
        ).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        getSharedPreferences(getString(R.string.preferences_file), MODE_PRIVATE).run {
            music = getBoolean(getString(R.string.music_key), true)
        }
    }

    override fun onResume() {
        super.onResume()
        if (music) {
            val startMusicIntent = Intent(applicationContext, GameMusicService::class.java)
            startService(startMusicIntent)
        }
        gameView.resumeCoroutine()
    }

    override fun onPause() {
        super.onPause()
        if (music) {
            val stopMusicIntent = Intent(applicationContext, GameMusicService::class.java)
            stopService(stopMusicIntent)
        }
        gameView.stopCoroutine()
    }

    override fun onDestroy() {
        gameView.stopCoroutine()
        super.onDestroy()
    }

    private fun togglePause() {
        gameView.togglePause()
    }
}