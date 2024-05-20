package com.example.rockpaperscissors

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.example.rockpaperscissors.databinding.ActivityPlaywithmeBinding

class Playwithme : AppCompatActivity() {
    private var binding: ActivityPlaywithmeBinding?  = null
    private var animation1 : AnimationDrawable? = null
    private var animation2 : AnimationDrawable? = null
    private var setTime : CountDownTimer? = null

    private var allowPlay = true

    private lateinit var player1Select : String
    private lateinit var player2Select : String

    private var p1Score = 0
    private var p2Score = 0
    // high score
    private lateinit var sharedPreferences: SharedPreferences
    private val keyHighScore= "high_score"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlaywithmeBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        //for 2nd player buttons
        binding?.rockBtnP2?.setOnClickListener{
            onPlay("rock")
        }
        binding?.paperBtnP2?.setOnClickListener{
            onPlay("paper")
        }
        binding?.scissorBtnP2?.setOnClickListener{
            onPlay("scissor")
        }

        sharedPreferences = getSharedPreferences("HighScore", Context.MODE_PRIVATE)

    }
    private fun playAnimation(){
        binding?.conImgP1?.setImageResource(0)
        binding?.conImgP2?.setImageResource(0)
        binding?.readyP1?.text = ""
        binding?.readyP2?.text = ""
        binding?.conImgP1?.setBackgroundResource(R.drawable.animation_views)
        animation1 = binding?.conImgP1?.background as AnimationDrawable
        binding?.conImgP2?.setBackgroundResource(R.drawable.animation_views)
        animation2 = binding?.conImgP2?.background as AnimationDrawable

        setTime = object  : CountDownTimer(3000,1500){
            override fun onTick(millisUntilFinished: Long) {
                animation1?.start()
                animation2?.start()
            }

            override fun onFinish() {
                animation1?.stop()
                animation2?.stop()
                allowPlay = true
                binding?.conImgP1?.setImageResource(0)
                binding?.conImgP2?.setImageResource(0)
                setSelectedIcon()
                setScore()
                endGame()
            }
        }.start()
    }
    private fun onPlay(selection :String){
        if(allowPlay){
            player1Select = listOf("rock","paper","scissor").random()
            player2Select = selection
            allowPlay =false
            playAnimation()
        }
    }
    private fun setSelectedIcon(){
        when(player1Select){
            "rock" ->binding?.conImgP1?.setImageResource(R.drawable.rock)
            "paper" ->binding?.conImgP1?.setImageResource(R.drawable.paper)
            "scissor" ->binding?.conImgP1?.setImageResource(R.drawable.scissor)

        }
        when(player2Select){
            "rock" ->binding?.conImgP2?.setImageResource(R.drawable.rock)
            "paper" ->binding?.conImgP2?.setImageResource(R.drawable.paper)
            "scissor" ->binding?.conImgP2?.setImageResource(R.drawable.scissor)

        }
    }
    private fun getResult():String{
        return if (player1Select == player2Select)
            "tie"
        else if(player1Select =="rock" && player2Select =="scissor"||
            player1Select =="paper" && player2Select == "rock"||
            player1Select =="scissor" && player2Select == "paper")
            "P1"
        else
            "P2"
    }

    private fun setScore(){
        binding?.myscore?.text = "Your Score: $p2Score"

        if(getResult() == "tie"){
            binding?.readyP1?.text ="Tie"
            binding?.readyP2?.text ="Tie"
        }else if(getResult() == "P1"){
            binding?.readyP1?.text ="Win"
            binding?.readyP2?.text ="Lose"
            p1Score++
            when (p1Score) {
                1 -> binding?.p2Heart3?.setImageResource(R.drawable.half_empty_heart)
                2 -> binding?.p2Heart3?.setImageResource(R.drawable.empty_heart)
                3 -> binding?.p2Heart2?.setImageResource(R.drawable.half_empty_heart)
                4 -> binding?.p2Heart2?.setImageResource(R.drawable.empty_heart)
                5 -> binding?.p2Heart1?.setImageResource(R.drawable.half_empty_heart)
                6 -> binding?.p2Heart1?.setImageResource(R.drawable.empty_heart)
            }
        }
        else {
            binding?.readyP1?.text = "Lose"
            binding?.readyP2?.text = "Win"
            p2Score++
            binding?.myscore?.text = "Your Score: $p2Score"

        }
    }
   //changed the live count two 2
    private fun endGame(){
        if(p1Score == 2 ){

            val highScore = getHighScore()
            val intent = Intent(this,WinPlaywithme::class.java)
            intent.putExtra("high_score",highScore)
            intent.putExtra("p2_score", p2Score)
            startActivity(intent)

            if(p2Score > highScore){
                saveHighScore(p2Score)
            }

            finish()
        }
    }

    private fun saveHighScore(score :Int){
        val editor = sharedPreferences.edit()
        editor.putInt(keyHighScore,score)
        editor.apply()
    }
    private fun getHighScore():Int{
        return sharedPreferences.getInt(keyHighScore,0)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onDestroy(){
        super.onDestroy()
        binding = null
        setTime = null
    }
}