package com.alyx.asteroids

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.alyx.asteroids.sounds.GameMusicService
import com.alyx.asteroids.views.GameView

class AsteroidsGame: Activity() {

    private lateinit var gameView: GameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.asteroids_game)
        gameView = findViewById(R.id.gameView)
    }

    override fun onResume() {
        super.onResume()
        val startMusicIntent = Intent(applicationContext, GameMusicService::class.java)
        startService(startMusicIntent)
        gameView.thread.resumeTh()
    }

    override fun onPause() {
        super.onPause()
        val stopMusicIntent = Intent(applicationContext, GameMusicService::class.java)
        stopService(stopMusicIntent)
        gameView.thread.pauseTh()
    }

    override fun onDestroy() {
        gameView.thread.stopTh()
        super.onDestroy()

    }
}