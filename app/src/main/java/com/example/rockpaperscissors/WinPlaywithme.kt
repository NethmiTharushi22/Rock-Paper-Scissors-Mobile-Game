package com.example.rockpaperscissors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import pl.droidsonroids.gif.GifImageView

class WinPlaywithme : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_win_playwithme)

        val btnHome = findViewById<Button>(R.id.btn_to_home)

        val highScores = findViewById<TextView>(R.id.high_score)
        val player2score = findViewById<TextView>(R.id.your_score)
       //get the score

        val highScore = intent.getIntExtra("high_score",0)
        val p2score = intent.getIntExtra("p2_score",0)

        highScores.text = "High Score: $highScore"
        player2score.text = "Your Score: $p2score"
        setWinnerView()
        btnHome.setOnClickListener{
            finish()
        }
    }
    private fun  setWinnerView(){
        val imageView:GifImageView = findViewById(R.id.wingameGif)
        imageView.setImageResource(R.drawable.lost)

    }


}