package com.alyx.asteroids

import android.app.Activity
import android.os.Bundle

class AsteroidsGame: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.asteroids_game)
    }
}