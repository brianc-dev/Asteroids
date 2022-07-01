package com.alyx.asteroids

import android.os.Bundle
import android.preference.PreferenceActivity
import android.view.View
import android.view.WindowManager
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.alyx.asteroids.graphics.AsteroidsFloatingButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Preferences : PreferenceActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.preference_activity)
        addPreferencesFromResource(R.xml.preferences)
        findViewById<AsteroidsFloatingButton>(R.id.goBackButton).also {
            it.setOnClickListener {
                finish()
            }
        }
        hideNavigationBarAndKeepScreenOn()
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
}