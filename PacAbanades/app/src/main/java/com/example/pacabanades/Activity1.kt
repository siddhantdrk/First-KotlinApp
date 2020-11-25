package com.example.pacabanades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class Activity1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_1)

        val activity2Btn=findViewById<Button>(R.id.activity2_btn)
        val activity3Btn=findViewById<Button>(R.id.activity3_btn)

        activity2Btn.setOnClickListener { // Handler code here.
            val intent = Intent(this@Activity1, Activity2::class.java);
            startActivity(intent);
        }

        activity3Btn.setOnClickListener { // Handler code here.
            val intent = Intent(this@Activity1, Activity3::class.java);
            startActivity(intent);
        }

    }
}