package com.alyx.asteroids

import java.util.*

interface ScoresStorage {
    fun saveScores(score: Int, name: String, date: Long)
    fun scoresList(quantity: Int): List<String>
}