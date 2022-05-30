package com.alyx.asteroids

class ScoresStorageArray: ScoresStorage {
    private val scores: MutableList<String> = mutableListOf("321000 Charles Brandon", "324000 Gary Roach", "28000 Patrick Don")

    override fun saveScores(score: Int, name: String, date: Long) {
        scores.add(0, (scores + " " + name).toString())
    }

    override suspend fun scoresList(quantity: Int): List<String> = scores
}