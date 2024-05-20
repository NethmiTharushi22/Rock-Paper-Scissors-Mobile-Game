package com.example.rockpaperscissors

import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import com.example.rockpaperscissors.databinding.ActivityPlaywithfriendBinding

class Playwithfriend : AppCompatActivity() {
    private var binding:ActivityPlaywithfriendBinding ?  = null
    private var animation1 :AnimationDrawable? = null
    private var animation2 :AnimationDrawable? = null
    private var setTime :CountDownTimer? = null

    private var readyPlayer1 = false
    private var readyPlayer2 = false
    private var allowPlay = true

    private lateinit var player1Select : String
    private lateinit var player2Select : String

    private var p1Score = 0
    private var p2Score = 0

    private var namePlayer1 = "Player1"
    private var namePlayer2 = "Player2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaywithfriendBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        getPlayerName()

        //for 2nd player buttons
        binding?.rockBtnP2?.setOnClickListener{
            onPlayer2Selection("rock")
        }
        binding?.paperBtnP2?.setOnClickListener{
            onPlayer2Selection("paper")
        }
        binding?.scissorBtnP2?.setOnClickListener{
            onPlayer2Selection("scissor")
        }
        // for 1sr player buttons
        binding?.rockBtnP1?.setOnClickListener{
            onPlayer1Selection("rock")
        }
        binding?.paperBtnP1?.setOnClickListener{
            onPlayer1Selection("paper")
        }
        binding?.scissorBtnP1?.setOnClickListener{
            onPlayer1Selection("scissor")
        }
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
                readyPlayer1 = false
                readyPlayer2 = false
                binding?.conImgP1?.setImageResource(0)
                binding?.conImgP2?.setImageResource(0)
                setSelectedIcon()
                setScore()
                endGame()
            }
        }.start()
    }
    private fun onPlayer1Selection(selection: String){
        if(allowPlay){
            binding?.readyP1?.text = "Ready"
            readyPlayer1 = true
            player1Select = selection

            if(readyPlayer2){
                allowPlay = false
                playAnimation()
            }

        }

    }
    private fun onPlayer2Selection(selection: String){
        if(allowPlay){
            binding?.readyP2?.text = "Ready"
            player2Select = selection
            readyPlayer2 = true
            if(readyPlayer1){
                allowPlay = false
                playAnimation()
            }

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
        if(getResult() == "tie"){
            binding?.readyP1?.text ="Tie"
            binding?.readyP2?.text ="Tie"
        }else if(getResult() == "P1"){
            binding?.readyP1?.text ="Win"
            binding?.readyP2?.text ="Lose"
            p1Score++
            when(p1Score)
            {
                1 -> binding?.p1Star1?.setImageResource(R.drawable.star_fil)
                2 -> binding?.p1Star2?.setImageResource(R.drawable.star_fil)
                3-> binding?.p1Star3?.setImageResource(R.drawable.star_fil)

            }
        }
        else {
            binding?.readyP1?.text = "Lose"
            binding?.readyP2?.text = "Win"
            p2Score++
            when (p2Score) {
                1 -> binding?.p2Star1?.setImageResource(R.drawable.star_fil)
                2 -> binding?.p2Star2?.setImageResource(R.drawable.star_fil)
                3 -> binding?.p2Star3?.setImageResource(R.drawable.star_fil)

            }
        }
    }
    private fun getPlayerName(){
        val namePopUp = Dialog(this)
        namePopUp.setContentView(R.layout.activity_player_name_popup)
        namePopUp.findViewById<Button>(R.id.okButton).setOnClickListener{
            val name1 = namePopUp.findViewById<EditText>(R.id.p1Name).text
            val name2 = namePopUp.findViewById<EditText>(R.id.p2Name).text

            if(name1.isNotEmpty() && name2.isNotEmpty()){
                namePlayer1 = name1.toString()
                namePlayer2 = name2.toString()
                binding?.playerName1?.text = namePlayer1
                binding?.playerName2?.text = namePlayer2
                namePopUp.cancel()


            }else{
                Toast.makeText(this,"Enter both players names",Toast.LENGTH_SHORT).show()
            }

        }
        namePopUp.show()
    }
    private fun endGame(){
        if(p1Score == 3 || p2Score ==3){

            var winner = if(p1Score ==3)
                namePlayer1
            else
                namePlayer2

            val intent = Intent(this,WintheGame::class.java)
            intent.putExtra("name",winner)
            startActivity(intent)
            finish()
        }
    }

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