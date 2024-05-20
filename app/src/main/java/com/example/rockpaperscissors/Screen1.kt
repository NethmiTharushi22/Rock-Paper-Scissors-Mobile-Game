package com.example.rockpaperscissors

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Screen1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen1)

        val btnplayWithFriend = findViewById<Button>(R.id.play_button1)
        btnplayWithFriend.setOnClickListener{
            val intent = Intent(this,Playwithfriend::class.java)
                startActivity(intent)
            }

        val btnplayWithMe = findViewById<Button>(R.id.play_button2)
        btnplayWithMe.setOnClickListener{
            val intent = Intent(this,Playwithme::class.java)
            startActivity(intent)
        }
        val viewInstructions:TextView = findViewById(R.id.instructions)
        viewInstructions.setOnClickListener{
            showInstructions()
        }
    }
    private fun showInstructions(){
        val insPopup = Dialog(this)
        insPopup.setContentView(R.layout.activity_instructions_popup)
        insPopup.findViewById<Button>(R.id.okButton).setOnClickListener{
            insPopup.cancel()
        }
        insPopup.show()
    }


}