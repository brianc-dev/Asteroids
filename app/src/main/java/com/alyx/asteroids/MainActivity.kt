package com.alyx.asteroids

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var scoresStorage: ScoresStorage
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scoresStorage = ScoreStoragePreferences(this)

        hideNavigationBarAndKeepScreenOn()
        setupListeners()
    }

    private fun hideNavigationBarAndKeepScreenOn() {
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
    }

    private fun setupListeners() {
        findViewById<View>(R.id.Button1).also {
            it.setOnClickListener {
                launchGame()
            }
        }
        findViewById<View>(R.id.Button2).also {
            it.setOnClickListener {
                launchSettings()
            }
        }
        findViewById<View>(R.id.Button3).setOnClickListener {
            launchAbout()
        }
        findViewById<View>(R.id.Button4).setOnClickListener {
            launchScores()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1234 && resultCode== RESULT_OK && data != null) {
            data.extras?.let {
                val score = it.getInt("score")
                val name = "me"
                scoresStorage.saveScores(score, name, System.currentTimeMillis())
            }
            launchScores()
        }
    }

    private fun launchGame() {
        val intent = Intent(this, AsteroidsGame::class.java)
        startActivityForResult(intent, 1234)
    }

    private fun launchAbout() {
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)
    }

    private fun launchSettings() {
        val intent = Intent(this, Preferences::class.java)
        startActivity(intent)
    }

    private fun launchScores() {
        val intent = Intent(this, ScoresActivity::class.java)
        startActivity(intent)
    }
}