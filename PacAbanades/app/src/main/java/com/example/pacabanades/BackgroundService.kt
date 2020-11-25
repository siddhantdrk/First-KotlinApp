package com.example.pacabanades

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.os.IBinder
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import java.lang.Integer.parseInt


class BackgroundSoundService : Service() {
    var player: MediaPlayer? = null
    override fun onBind(arg0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        player = MediaPlayer.create(this, R.raw.background_song)
        player!!.isLooping = true // Set looping
        player!!.setVolume(100f, 100f)

        LocalBroadcastManager.getInstance(this).registerReceiver(StateReceiver,  IntentFilter("status"))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        player!!.start()
        return startId
    }

    private val StateReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val status = intent.getStringExtra("status")
            if (status.equals("pause")) {
                player!!.pause()
            } else {
                player!!.start()
            }
        }
    }


    override fun onStart(intent: Intent?, startId: Int) {
        // TO DO
    }

    fun onUnBind(arg0: Intent?): IBinder? {
        // TO DO Auto-generated method
        return null
    }

    fun onStop() {}
    fun onPause() {}
    override fun onDestroy() {
        player!!.stop()
        player!!.release()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(StateReceiver)
    }

    override fun onLowMemory() {}

    companion object {
        private val TAG: String? = null
    }


}