package com.example.helloworld

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.media.MediaPlayer.*
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import java.io.IOException


class MediaPlayerService : Service(), OnCompletionListener {
    private val iBinder: IBinder = LocalBinder()
    private lateinit var mediaPlayer: MediaPlayer
    private var songId = 1

    private fun setSong(songId: Int) {
        mediaPlayer.reset()
        val assetFileDescriptor = resources.openRawResourceFd(resources.getIdentifier("bgm$songId", "raw", packageName))
        try {
            mediaPlayer.setDataSource(
                    assetFileDescriptor.fileDescriptor,
                    assetFileDescriptor.startOffset,
                    assetFileDescriptor.length
            )
            assetFileDescriptor.close()
            mediaPlayer.prepare()
            Log.d("setSong", getString(R.string.done))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun initMediaPlayer() {
        mediaPlayer = MediaPlayer()
        mediaPlayer.setOnCompletionListener(this)
        setSong(songId)
    }

    private fun play() {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
            Log.d("play", "${getString(R.string.done)} New SongId$songId")
        }
    }

    private fun pause() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            Log.d("pause", getString(R.string.done))
        }
    }

    private fun stop() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            setSong(songId)
            Log.d("stop", getString(R.string.done))
        }
    }

    private fun back() {
        if (songId > 1) {
            songId = songId.minus(1)
            if (!mediaPlayer.isPlaying) {
                mediaPlayer.stop()
            }
            setSong(songId)
            mediaPlayer.start()
            Log.d("back", "${getString(R.string.done)} New SongId$songId")
        }
        else
            Toast.makeText(applicationContext, "To jest pierwszy utwór c:", Toast.LENGTH_SHORT).show()
    }

    private fun skip() {
        if (songId < 147) {
            songId = songId.plus(1)
            if (!mediaPlayer.isPlaying) {
                mediaPlayer.stop()
            }
            setSong(songId)
            mediaPlayer.start()
            Log.d("skip", "${getString(R.string.done)} New SongId$songId")
        }
        else
            Toast.makeText(applicationContext, "To już ostatni utwór :c", Toast.LENGTH_SHORT).show()
    }

    private val audioPlayer: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == null) return
            when (intent.getStringExtra("button")) {
                applicationContext.getString(R.string.action_play) -> play()
                applicationContext.getString(R.string.action_pause) -> pause()
                applicationContext.getString(R.string.action_skip) -> skip()
                applicationContext.getString(R.string.action_back) -> back()
                applicationContext.getString(R.string.action_stop) -> stop()
            }
            Log.d("broadCastReceiver", getString(R.string.done))
        }
    }

    private fun registerAudioPlayer() {
        val filter = IntentFilter(getString(R.string.broadcast_audio_player))
        registerReceiver(audioPlayer, filter)
    }

    override fun onBind(intent: Intent?): IBinder {
        return iBinder
    }

    override fun onCreate() {
        super.onCreate()
        registerAudioPlayer()
        Log.d("onCreate", getString(R.string.done))
    }

    override fun onCompletion(mp: MediaPlayer?) {
        mp?.stop()
        setSong(songId)
        Log.d("onCompletion", getString(R.string.done))
    }

    override fun onDestroy() {
        super.onDestroy()
        stop()
        mediaPlayer.release()
        unregisterReceiver(audioPlayer)
        Log.d("onDestroy", getString(R.string.done))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        initMediaPlayer()
        Log.d("onStartCommand", getString(R.string.done))
        return super.onStartCommand(intent, flags, startId)
    }

    inner class LocalBinder : Binder() {
        val service
            get() = this@MediaPlayerService
    }
}