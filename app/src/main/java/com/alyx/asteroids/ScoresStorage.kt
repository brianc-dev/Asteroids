package com.alyx.asteroids

interface ScoresStorage {
    fun saveScores(score: Int, name: String, date: Long)
    suspend fun scoresList(quantity: Int): List<String>
}