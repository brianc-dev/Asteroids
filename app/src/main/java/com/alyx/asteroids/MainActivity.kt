package com.alyx.asteroids

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    companion object {
        val scoresStorage: ScoresStorage = ScoresStorageArray()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.Button2).setOnClickListener {
            launchSettings()
        }
        findViewById<Button>(R.id.Button3).setOnClickListener {
            launchAbout()
        }
        findViewById<Button>(R.id.Button4).setOnClickListener {
            launchScores()
        }

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