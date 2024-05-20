package com.example.rockpaperscissors

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import pl.droidsonroids.gif.GifImageView

class WintheGame : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_winthe_game)

        var btnToHome = findViewById<Button>(R.id.btn_to_home)

        val winnerName:TextView = findViewById(R.id.winnerName)
        //get the winners name
        winnerName.text = intent.getStringExtra("name")
        setWinnerView()

        btnToHome.setOnClickListener {
            var intent = Intent(this, Screen1::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun  setWinnerView(){
        val imageView:GifImageView = findViewById(R.id.wingameGif)
         imageView.setImageResource(R.drawable.wingame)

    }
}