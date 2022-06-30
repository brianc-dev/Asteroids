package com.alyx.asteroids.sounds

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import com.alyx.asteroids.R

class GameMusicService: Service() {

    companion object {
        var playback: Int = 0
    }

        private lateinit var gameMediaPlayer: MediaPlayer

    override fun onCreate() {
        super.onCreate()
        gameMediaPlayer = MediaPlayer.create(this, R.raw.main_music)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        gameMediaPlayer.seekTo(playback)
        gameMediaPlayer.start()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        playback = gameMediaPlayer.currentPosition
        gameMediaPlayer.pause()
        gameMediaPlayer.release()
    }


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}