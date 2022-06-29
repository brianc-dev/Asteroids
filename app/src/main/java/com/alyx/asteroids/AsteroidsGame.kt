package com.alyx.asteroids

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.alyx.asteroids.sounds.GameMusicService
import com.alyx.asteroids.views.GameView

class AsteroidsGame: Activity() {

    private lateinit var gameView: GameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.asteroids_game)
        gameView = findViewById(R.id.gameView)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, findViewById<View>(android.R.id.content).rootView).let { controller ->
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
    }

    override fun onResume() {
        super.onResume()
        val startMusicIntent = Intent(applicationContext, GameMusicService::class.java)
        startService(startMusicIntent)
        gameView.resumeCoroutine()
    }

    override fun onPause() {
        super.onPause()
        val stopMusicIntent = Intent(applicationContext, GameMusicService::class.java)
        stopService(stopMusicIntent)
        gameView.stopCoroutine()
    }

    override fun onDestroy() {
        gameView.stopCoroutine()
        super.onDestroy()
    }
}