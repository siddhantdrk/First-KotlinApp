package com.example.pacabanades

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import java.util.*
import kotlin.concurrent.schedule


class Activity3 : AppCompatActivity() {

    var check:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_3)

        Toast.makeText(applicationContext, "esto es Activity3",Toast.LENGTH_SHORT).show()
        val createNewServiceBtn=findViewById<ImageButton>(R.id.play_Img_Button)
        val stopServiceBtn=findViewById<ImageButton>(R.id.stop_Img_Button)
        val gotBackToActivity1=findViewById<ImageButton>(R.id.Back_Img_Button)
//        val playSongBtn=findViewById<ImageButton>(R.id.play_song_Img_Button)

        val svc = Intent(this, BackgroundSoundService::class.java)
        
        val spinner=findViewById<Spinner>(R.id.spinner)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    when (position) {
                        1 -> if(check) send_status("start") else Toast.makeText(applicationContext, "Crea un nuevo servicio",Toast.LENGTH_SHORT).show()
                        2-> if(check) send_status("pause") else Toast.makeText(applicationContext, "Crea un nuevo servicio",Toast.LENGTH_SHORT).show()
                        3-> if(check) pauseFortime() else Toast.makeText(applicationContext, "Crea un nuevo servicio",Toast.LENGTH_SHORT).show()
                    }
                    spinner.setSelection(0)
            }

        }

//        playSongBtn.setOnClickListener{
//            if(check) send_status("start") else Toast.makeText(applicationContext, "Crea un nuevo servicio",Toast.LENGTH_SHORT).show()
//        }


        createNewServiceBtn.setOnClickListener{
            startService(svc)
            check=true
            Toast.makeText(applicationContext, "Crear nuevo servicio",Toast.LENGTH_SHORT).show()
        }

        stopServiceBtn.setOnClickListener{
            check=false
            Toast.makeText(applicationContext, "Daetener servicio",Toast.LENGTH_SHORT).show()
            stopService(svc)
        }

        gotBackToActivity1.setOnClickListener { // Handler code here.
            if(check){
                stopService(svc)
            }
            Toast.makeText(applicationContext, "de regreso Activity1",Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun pauseFortime(){
        Toast.makeText(applicationContext, "Bloqueo por 150000ms",Toast.LENGTH_SHORT).show()
        send_status("pause")
        Timer().schedule(150000) {
            send_status("start")
        }
    }

    private fun send_status(status: String) {
        if(status.equals("pause")){
            Toast.makeText(applicationContext, "pausar la música",Toast.LENGTH_SHORT).show()
        }
        else{

            runOnUiThread {
                val toast = Toast.makeText(applicationContext, "Iniciar música", Toast.LENGTH_SHORT)
                toast.show()
            }
//            Toast.makeText(applicationContext, "Iniciar música",Toast.LENGTH_SHORT).show()
        }
        val intent = Intent("status")
        intent.putExtra("status", status)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

}

