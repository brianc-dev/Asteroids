package com.alyx.asteroids

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    companion object {
        val scoresStorage: ScoresStorage = ScoresStorageArray()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val anim = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.turn_and_zoom)
        val anim2 = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.appear)
        val anim3 = AnimationUtils.loadAnimation(this, R.anim.right_translation)
        findViewById<TextView>(R.id.game_title).startAnimation(anim)
        findViewById<Button>(R.id.Button1).also {
            it.setOnClickListener {
                launchGame()
            }
            it.startAnimation(anim2)
        }
        findViewById<Button>(R.id.Button2).also {
            it.startAnimation(anim3)
            it.setOnClickListener {
                launchSettings()
            }
        }
        findViewById<Button>(R.id.Button3).setOnClickListener {
            launchAbout()
        }
        findViewById<Button>(R.id.Button4).setOnClickListener {
            launchScores()
        }
    }

    private fun launchGame() {
        val intent = Intent(this, AsteroidsGame::class.java)
        startActivity(intent)
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
        val intent = Intent(this, Scores::class.java)
        startActivity(intent)
    }
}