package com.alyx.asteroids

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
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
            finishAndRemoveTask()
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
}