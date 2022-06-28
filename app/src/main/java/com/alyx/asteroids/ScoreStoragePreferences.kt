package com.alyx.asteroids

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.net.Socket

class ScoreStoragePreferences(private val context: Context): ScoresStorage {
    companion object {
        private const val PREFERENCES = "preferences"
    }

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun saveScores(score: Int, name: String, date: Long) {
        coroutineScope.launch {
            try {
                val socket = Socket("192.168.1.107", 2500)
                val input = BufferedReader(InputStreamReader(socket.getInputStream()))
                val output = PrintWriter(OutputStreamWriter(socket.getOutputStream()), true)
                output.println("$score $name")
                val response = input.readLine()
                if (!response.equals("OK")) {
                    Log.e("Asteroids", "Error: Incorrect server response", )
                }
                socket.close()
            } catch (e: Exception) {
                Log.e("Asteroids", e.toString(), e)
            }
        }
//        val preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
//        val preferencesEditor = preferences.edit()
//
//        for (i in 9 downTo 1) {
//            preferencesEditor.putString("score $i", preferences.getString("score ${i - 1}", ""))
//        }
//        preferencesEditor.putString("score 0", "$score $name")
//        preferencesEditor.apply()
    }

    override suspend fun scoresList(quantity: Int): List<String> {
            val result = coroutineScope.async<List<String>> {
                try {
                val list = mutableListOf<String>()

                val socket = Socket("192.168.1.107", 2500)
                val input = BufferedReader(InputStreamReader(socket.getInputStream()))
                val output = PrintWriter(OutputStreamWriter(socket.getOutputStream()), true)
                output.println("SCORES")
                var n = 0
                var response: String?
                do {
                    response = input.readLine()
                    response?.let {
                        list.add(response)
                        n++
                    }
                } while (n < quantity && response != null)
                socket.close()
                list
                } catch (e: Exception) {
                    Log.e("Asteroids", e.toString(), e)
                    emptyList()
                }

        }
        return result.await()
    }
//        val preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
//
//        for (i in 0..9) {
//            val s = preferences.getString("score $i", "") ?: ""
//            if (s.isNotEmpty()) result.add(s)
//        }
//
//        return result
//    }
}