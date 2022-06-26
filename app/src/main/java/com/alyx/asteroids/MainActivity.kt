package com.alyx.asteroids

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var scoresStorage: ScoresStorage
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scoresStorage = ScoreStoragePreferences(this)

        val anim = AnimationUtils.loadAnimation(this, R.anim.turn_and_zoom)
        val anim2 = AnimationUtils.loadAnimation(this, R.anim.appear)
        val anim3 = AnimationUtils.loadAnimation(this, R.anim.right_translation)
//        findViewById<TextView>(R.id.game_title).startAnimation(anim)
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