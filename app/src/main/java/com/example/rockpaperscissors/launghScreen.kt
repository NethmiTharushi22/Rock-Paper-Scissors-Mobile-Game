package com.example.rockpaperscissors

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout

class launghScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laungh_screen)

        val const1: ConstraintLayout = findViewById(R.id.lanunghscreen)

        const1.setOnClickListener {
            val intent = Intent(this,Screen1::class.java)
            startActivity(intent)
        }
    }
}